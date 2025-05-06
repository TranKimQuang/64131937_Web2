package edu.quangtk.controllers;

import com.ntu.facultycms.entity.Notification;
import com.ntu.facultycms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Notification> createNotification(@PathVariable Long facultyId, @RequestBody Notification notification) {
        notification.setFacultyId(facultyId);
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Notification>> getActiveNotifications(@PathVariable Long facultyId) {
        return ResponseEntity.ok(notificationService.getActiveNotifications(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long facultyId, @PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if (!notification.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Notification does not belong to this faculty");
        }
        return ResponseEntity.ok(notification);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long facultyId, @PathVariable Long id, @RequestBody Notification notification) {
        notification.setFacultyId(facultyId);
        return ResponseEntity.ok(notificationService.updateNotification(id, notification));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long facultyId, @PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if (!notification.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Notification does not belong to this faculty");
        }
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}