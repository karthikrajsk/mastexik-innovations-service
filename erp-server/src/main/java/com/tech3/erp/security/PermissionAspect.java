package com.tech3.erp.security;

import com.tech3.erp.entity.ApplicationUser;
import com.tech3.erp.entity.UserMenuPermission;
import com.tech3.erp.repository.ApplicationUserRepository;
import com.tech3.erp.repository.UserMenuPermissionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class PermissionAspect {

    private final UserMenuPermissionRepository userMenuPermissionRepository;
    private final ApplicationUserRepository userRepository;

    public PermissionAspect(UserMenuPermissionRepository userMenuPermissionRepository,
                            ApplicationUserRepository userRepository) {
        this.userMenuPermissionRepository = userMenuPermissionRepository;
        this.userRepository = userRepository;
    }

    @Around("@annotation(hasPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, HasPermission hasPermission) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        // In this project, userId is passed in request body or headers
        // Since we don't have a standard SecurityContext, we'll try to get it from header or parameter
        String userIdStr = request.getHeader("X-User-Id");
        if (userIdStr == null) {
            // Fallback to checking common request attributes or parameters if needed
            userIdStr = request.getParameter("userId");
        }

        if (userIdStr == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User ID missing in request");
        }

        Long userId = Long.valueOf(userIdStr);
        Optional<ApplicationUser> userOpt = userRepository.findById(userId);
        
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        ApplicationUser user = userOpt.get();

        // Admin bypass
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return joinPoint.proceed();
        }

        String path = hasPermission.value();
        String action = hasPermission.action().toUpperCase();

        List<UserMenuPermission> permissions = userMenuPermissionRepository.findByUserIdAndSubMenu_ActiveTrue(userId);
        
        boolean allowed = permissions.stream().anyMatch(p -> {
            if (p.getSubMenu().getPath().equals(path)) {
                return switch (action) {
                    case "ADD" -> p.getCanAdd();
                    case "EDIT" -> p.getCanEdit();
                    case "DELETE" -> p.getCanDelete();
                    case "VIEW" -> p.getCanView();
                    case "EXPORT" -> p.getCanExport();
                    case "PRINT" -> p.getCanPrint();
                    case "APPROVE" -> p.getCanApprove();
                    case "REJECT" -> p.getCanReject();
                    case "PROCESS" -> p.getCanProcess();
                    case "SHARE" -> p.getCanShare();
                    case "DUPLICATE" -> p.getCanDuplicate();
                    case "SCHEDULE" -> p.getCanSchedule();
                    default -> false;
                };
            }
            return false;
        });

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to perform this action");
        }

        return joinPoint.proceed();
    }
}
