// AuthService.java
package com.tech3.erp.service;

import com.tech3.erp.entity.ApplicationUser;
import com.tech3.erp.repository.ApplicationUserRepository;
import com.tech3.erp.repository.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.time.LocalDateTime;
import java.util.UUID;

//AuthService.java - Update with device-based session management
@Service
public class AuthService {

 private final ApplicationUserRepository applicationUserRepository;
 private final CompanyRepository companyRepository;
 private final PasswordEncoder passwordEncoder;
 private final MenuService menuService;
 
 // Track active sessions: userId -> Map of deviceId to sessionId
 private final ConcurrentMap<Long, Map<String, String>> userDeviceSessions = new ConcurrentHashMap<>();
 // Track session creation time: sessionId -> creation time
 private final ConcurrentMap<String, LocalDateTime> sessionTimes = new ConcurrentHashMap<>();
 // Track session to user mapping: sessionId -> userId
 private final ConcurrentMap<String, Long> sessionUserMap = new ConcurrentHashMap<>();
 // Track session to device mapping: sessionId -> deviceId
 private final ConcurrentMap<String, String> sessionDeviceMap = new ConcurrentHashMap<>();
 // Track session to company mapping: sessionId -> companyId
 private final ConcurrentMap<String, Long> sessionCompanyMap = new ConcurrentHashMap<>();
 
 // Session timeout in minutes
 private static final int SESSION_TIMEOUT_MINUTES = 15;
 // Prevent concurrent login from DIFFERENT devices
 private static final boolean PREVENT_CONCURRENT_LOGIN = true;
 // Allow multiple sessions from same device
 private static final boolean ALLOW_MULTIPLE_SESSIONS_PER_DEVICE = true;

 public AuthService(ApplicationUserRepository applicationUserRepository, 
                   CompanyRepository companyRepository,
                   PasswordEncoder passwordEncoder,
                   MenuService menuService) {
     this.applicationUserRepository = applicationUserRepository;
     this.companyRepository = companyRepository;
     this.passwordEncoder = passwordEncoder;
     this.menuService = menuService;
     
     startSessionCleanup();
 }

 public Map<String, Object> authenticate(String userName, String password, Long companyId, String deviceId) {
     // Find user by username (case insensitive)
     Optional<ApplicationUser> userOpt = applicationUserRepository.findByUserNameIgnoreCase(userName);
     
     if (userOpt.isEmpty()) {
         throw new IllegalArgumentException("Invalid username or password");
     }

     ApplicationUser user = userOpt.get();

     // Check if user is active
     if (!user.getActiveFlag()) {
         throw new IllegalArgumentException("User account is deactivated");
     }

     // Verify password
     if (!passwordEncoder.matches(password, user.getPassword())) {
         throw new IllegalArgumentException("Invalid username or password");
     }

     // ✅ FIX: Check for existing sessions from OTHER devices only
     if (PREVENT_CONCURRENT_LOGIN) {
         Map<String, String> deviceSessions = userDeviceSessions.get(user.getId());
         if (deviceSessions != null && !deviceSessions.isEmpty()) {
             // Check if user has active sessions from OTHER devices
             boolean hasSessionsFromOtherDevices = deviceSessions.entrySet().stream()
                 .anyMatch(entry -> !entry.getKey().equals(deviceId));
             
             if (hasSessionsFromOtherDevices) {
                 throw new IllegalArgumentException("User already logged in from another device");
             }
         }
     }

     // Check if user has access to the selected company
     if (companyId != null && !user.getCompanies().contains(companyId)) {
         throw new IllegalArgumentException("User does not have access to the selected company");
     }

     // Create new session
     String sessionId = UUID.randomUUID().toString();
     
     // Seed default reports and number format configurations if companyId is provided (idempotent call)
     if (companyId != null) {
         companyRepository.seedDefaultReports(companyId);
         companyRepository.seedNumberFormatConfigurations(companyId);
     }

     // Add session to user's device sessions
     userDeviceSessions.computeIfAbsent(user.getId(), k -> new ConcurrentHashMap<>())
                      .put(deviceId, sessionId);
     sessionTimes.put(sessionId, LocalDateTime.now());
     sessionUserMap.put(sessionId, user.getId());
     sessionDeviceMap.put(sessionId, deviceId);

     Map<String, Object> response = new HashMap<>();
     response.put("success", true);
     response.put("sessionId", sessionId);
     response.put("user", Map.of(
         "id", user.getId(),
         "userName", user.getUserName(),
         "role", user.getRole(),
         "companies", user.getCompanies()
     ));
     response.put("menuData", menuService.getUserMenuData(user));

     return response;
 }

 public Map<String, Object> validateUser(String userName, String password, String deviceId) {
     Optional<ApplicationUser> userOpt = applicationUserRepository.findByUserNameIgnoreCase(userName);
     
     if (userOpt.isEmpty()) {
         throw new IllegalArgumentException("Invalid username or password");
     }

     ApplicationUser user = userOpt.get();

     if (!passwordEncoder.matches(password, user.getPassword())) {
         throw new IllegalArgumentException("Invalid username or password");
     }

     // ✅ FIX: Check for existing sessions from OTHER devices only during validation too
     if (PREVENT_CONCURRENT_LOGIN) {
         Map<String, String> deviceSessions = userDeviceSessions.get(user.getId());
         if (deviceSessions != null && !deviceSessions.isEmpty()) {
             boolean hasSessionsFromOtherDevices = deviceSessions.entrySet().stream()
                 .anyMatch(entry -> !entry.getKey().equals(deviceId));
             
             if (hasSessionsFromOtherDevices) {
                 throw new IllegalArgumentException("User already logged in from another device");
             }
         }
     }

     Map<String, Object> response = new HashMap<>();
     response.put("userName", user.getUserName());
     response.put("companies", user.getCompanies());
     
     return response;
 }

 // Validate session and update last activity
 public boolean validateSession(Long userId, String sessionId) {
     Map<String, String> deviceSessions = userDeviceSessions.get(userId);
     if (deviceSessions == null || !deviceSessions.containsValue(sessionId)) {
         return false;
     }
     
     // Check if session is expired
     LocalDateTime sessionTime = sessionTimes.get(sessionId);
     if (sessionTime == null || sessionTime.plusMinutes(SESSION_TIMEOUT_MINUTES).isBefore(LocalDateTime.now())) {
         // Auto-remove expired session
         logout(userId, sessionId);
         return false;
     }
     
     // Update session time
     sessionTimes.put(sessionId, LocalDateTime.now());
     return true;
 }

 // Logout user from specific session
 public void logout(Long userId, String sessionId) {
     String deviceId = sessionDeviceMap.get(sessionId);
     if (deviceId != null) {
         Map<String, String> deviceSessions = userDeviceSessions.get(userId);
         if (deviceSessions != null) {
             deviceSessions.remove(deviceId);
             if (deviceSessions.isEmpty()) {
                 userDeviceSessions.remove(userId);
             }
         }
     }
     
     sessionTimes.remove(sessionId);
     sessionUserMap.remove(sessionId);
     sessionDeviceMap.remove(sessionId);
 }

 // Logout user from ALL devices
 public void logoutFromAllDevices(Long userId) {
     Map<String, String> deviceSessions = userDeviceSessions.get(userId);
     if (deviceSessions != null) {
         for (String sessionId : deviceSessions.values()) {
             sessionTimes.remove(sessionId);
             sessionUserMap.remove(sessionId);
             sessionDeviceMap.remove(sessionId);
         }
         userDeviceSessions.remove(userId);
     }
 }

 // Force logout from all devices (can be called by admin or user)
 public boolean forceLogoutAllSessions(String userName) {
     Optional<ApplicationUser> userOpt = applicationUserRepository.findByUserNameIgnoreCase(userName);
     if (userOpt.isPresent()) {
         ApplicationUser user = userOpt.get();
         logoutFromAllDevices(user.getId());
         return true;
     }
     return false;
 }

 // Cleanup expired sessions
 private void startSessionCleanup() {
     Thread cleanupThread = new Thread(() -> {
         while (true) {
             try {
                 Thread.sleep(60000); // Check every minute
                 LocalDateTime now = LocalDateTime.now();
                 
                 sessionTimes.entrySet().removeIf(entry -> {
                     if (entry.getValue().plusMinutes(SESSION_TIMEOUT_MINUTES).isBefore(now)) {
                         String sessionId = entry.getKey();
                         Long userId = sessionUserMap.get(sessionId);
                         String deviceId = sessionDeviceMap.get(sessionId);
                         
                         // Remove from user device sessions
                         if (userId != null && deviceId != null) {
                             Map<String, String> deviceSessions = userDeviceSessions.get(userId);
                             if (deviceSessions != null) {
                                 deviceSessions.remove(deviceId);
                                 if (deviceSessions.isEmpty()) {
                                     userDeviceSessions.remove(userId);
                                 }
                             }
                         }
                         
                         // Remove from session maps
                         sessionUserMap.remove(sessionId);
                         sessionDeviceMap.remove(sessionId);
                         return true;
                     }
                     return false;
                 });
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
                 break;
             }
         }
     });
     cleanupThread.setDaemon(true);
     cleanupThread.start();
 }

 // Get remaining session time in minutes
 public long getRemainingSessionTime(String sessionId) {
     LocalDateTime sessionTime = sessionTimes.get(sessionId);
     if (sessionTime == null) return 0;
     
     LocalDateTime expiryTime = sessionTime.plusMinutes(SESSION_TIMEOUT_MINUTES);
     return java.time.Duration.between(LocalDateTime.now(), expiryTime).toMinutes();
 }

 // Get active device count for user (for admin purposes)
 public int getActiveDeviceCount(Long userId) {
     Map<String, String> deviceSessions = userDeviceSessions.get(userId);
     return deviceSessions != null ? deviceSessions.size() : 0;
 }
}