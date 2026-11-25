// AuthController.java - Add new endpoints
package com.tech3.erp.controller;

import com.tech3.erp.service.AuthService;
import com.tech3.erp.service.MenuService;
import com.tech3.erp.entity.ApplicationUser;
import com.tech3.erp.repository.ApplicationUserRepository;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "APIs for user authentication")
public class AuthController {

    private final AuthService authService;
    private final MenuService menuService;
    private final ApplicationUserRepository applicationUserRepository;

    public AuthController(AuthService authService,
                          MenuService menuService,
                          ApplicationUserRepository applicationUserRepository) {
        this.authService = authService;
        this.menuService = menuService;
        this.applicationUserRepository = applicationUserRepository;
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticate user with username, password and company")
    public ResponseEntity<Object> login(@RequestBody Map<String, Object> loginRequest, 
                                       HttpServletRequest request) {
        try {
            String userName = (String) loginRequest.get("userName");
            String password = (String) loginRequest.get("password");
            Long companyId = null;
            
            if (loginRequest.get("companyId") != null) {
                companyId = Long.valueOf(loginRequest.get("companyId").toString());
            }

            // ✅ Generate device ID from request
            String deviceId = generateDeviceId(request);

            Map<String, Object> authResponse = authService.authenticate(userName, password, companyId, deviceId);
            return ResponseUtil.success("Login successful", authResponse);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return ResponseUtil.error("Authentication failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate User", description = "Validate user credentials and get available companies")
    public ResponseEntity<Object> validateUser(@RequestBody Map<String, String> credentials, 
                                              HttpServletRequest request) {
        try {
            String userName = credentials.get("userName");
            String password = credentials.get("password");

            // ✅ Generate device ID from request
            String deviceId = generateDeviceId(request);

            Map<String, Object> validationResponse = authService.validateUser(userName, password, deviceId);
            return ResponseUtil.success("Validation successful", validationResponse);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return ResponseUtil.error("Validation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Generate device ID from request (simplified version)
    private String generateDeviceId(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        
        // Create a hash of user agent + IP address to identify device
        String deviceInfo = userAgent + "|" + ipAddress;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(deviceInfo.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            // Fallback to simple hash
            return Integer.toHexString(deviceInfo.hashCode());
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

 // In AuthController.java - Fix the validate-session endpoint
    @PostMapping("/validate-session")
    @Operation(summary = "Validate Session", description = "Validate user session and get remaining time")
    public ResponseEntity<Object> validateSession(@RequestBody Map<String, Object> sessionRequest) {
        try {
            Long userId = Long.valueOf(sessionRequest.get("userId").toString());
            String sessionId = (String) sessionRequest.get("sessionId");

            boolean isValid = authService.validateSession(userId, sessionId);
            if (isValid) {
                long remainingTime = authService.getRemainingSessionTime(sessionId);
                // ✅ Return the data directly in the response
                return ResponseUtil.success("Session valid", Map.of(
                    "valid", true,
                    "remainingTime", remainingTime
                ));
            } else {
                // ✅ Return valid: false when session is invalid
                return ResponseUtil.success("Session invalid", Map.of(
                    "valid", false,
                    "remainingTime", 0
                ));
            }
        } catch (Exception e) {
            // ✅ Return valid: false on error
            return ResponseUtil.success("Session validation failed", Map.of(
                "valid", false,
                "remainingTime", 0
            ));
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout user from current session")
    public ResponseEntity<Object> logout(@RequestBody Map<String, Object> logoutRequest) {
        try {
            Long userId = Long.valueOf(logoutRequest.get("userId").toString());
            String sessionId = (String) logoutRequest.get("sessionId");

            authService.logout(userId, sessionId);
            return ResponseUtil.success("Logout successful", null);
        } catch (Exception e) {
            return ResponseUtil.error("Logout failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ NEW ENDPOINT: Logout from all devices
    @PostMapping("/logout-all")
    @Operation(summary = "Logout All Sessions", description = "Logout user from all devices and sessions")
    public ResponseEntity<Object> logoutFromAllDevices(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            
            authService.logoutFromAllDevices(userId);
            return ResponseUtil.success("Logged out from all devices", null);
        } catch (Exception e) {
            return ResponseUtil.error("Logout failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ NEW ENDPOINT: Force logout user from all devices (admin function)
    @PostMapping("/force-logout")
    @Operation(summary = "Force Logout User", description = "Force logout user from all devices (admin function)")
    public ResponseEntity<Object> forceLogoutUser(@RequestBody Map<String, String> request) {
        try {
            String userName = request.get("userName");
            
            boolean success = authService.forceLogoutAllSessions(userName);
            if (success) {
                return ResponseUtil.success("User logged out from all devices", null);
            } else {
                return ResponseUtil.error("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtil.error("Force logout failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user-menu/{userId}")
    @Operation(summary = "Get User Menu", description = "Get fresh user menu data after settings changes")
    public ResponseEntity<Object> getUserMenu(@PathVariable Long userId) {
        try {
            ApplicationUser user = applicationUserRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Object menuData = menuService.getUserMenuData(user);
            return ResponseUtil.success("Menu data retrieved", menuData);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to retrieve menu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}