package com.tech3.erp.controller;

import com.tech3.erp.dto.NotificationDTO;
import com.tech3.erp.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Real-time notification APIs")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get paginated notifications for a company")
    public ResponseEntity<Page<NotificationDTO>> getNotifications(
            @RequestParam Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(notificationService.getByCompany(companyId, page, size));
    }

    @GetMapping("/unread-count")
    @Operation(summary = "Get unread notification count for a company")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@RequestParam Long companyId) {
        long count = notificationService.getUnreadCount(companyId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<Map<String, Boolean>> markAsRead(
            @PathVariable Long id,
            @RequestParam Long companyId) {
        boolean updated = notificationService.markAsRead(id, companyId);
        return ResponseEntity.ok(Map.of("success", updated));
    }

    @PutMapping("/read-all")
    @Operation(summary = "Mark all notifications as read for a company")
    public ResponseEntity<Map<String, Integer>> markAllAsRead(@RequestParam Long companyId) {
        int updated = notificationService.markAllAsRead(companyId);
        return ResponseEntity.ok(Map.of("updated", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification by ID for a company")
    public ResponseEntity<Map<String, Boolean>> deleteNotification(
            @PathVariable Long id,
            @RequestParam Long companyId) {
        boolean deleted = notificationService.deleteById(id, companyId);
        return ResponseEntity.ok(Map.of("success", deleted));
    }
}
