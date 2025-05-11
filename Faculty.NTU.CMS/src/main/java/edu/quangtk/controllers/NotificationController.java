package edu.quangtk.controllers;

import edu.quangtk.entity.Faculty;
import edu.quangtk.entity.Notification;
import edu.quangtk.entity.User;
import edu.quangtk.services.FacultyService;
import edu.quangtk.services.NotificationService;
import edu.quangtk.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final FacultyService facultyService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService,
                               FacultyService facultyService,
                               UserService userService) {
        this.notificationService = notificationService;
        this.facultyService = facultyService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Notification> createNotification(
            @PathVariable Long facultyId,
            @RequestBody Notification notification,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Faculty faculty = facultyService.getFacultyById(facultyId);
        User creator = userService.getUserByUsername(userDetails.getUsername());
        
        notification.setFaculty(faculty);
        notification.setCreatedBy(creator);
        
        Notification createdNotification = notificationService.createNotification(notification);
        return ResponseEntity.ok(createdNotification);
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Notification>> getActiveNotifications(@PathVariable Long facultyId) {
        List<Notification> notifications = notificationService.getActiveNotifications(facultyId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Notification> getNotificationById(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        
        Notification notification = notificationService.getNotificationById(id);
        if (!notification.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Notification does not belong to this faculty");
        }
        return ResponseEntity.ok(notification);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Long facultyId,
            @PathVariable Long id,
            @RequestBody Notification notificationDetails) {
        
        Notification existingNotification = notificationService.getNotificationById(id);
        if (!existingNotification.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Notification does not belong to this faculty");
        }

        // Update only allowed fields
        existingNotification.setTitle(notificationDetails.getTitle());
        existingNotification.setContent(notificationDetails.getContent());
        existingNotification.setStartDate(notificationDetails.getStartDate());
        existingNotification.setEndDate(notificationDetails.getEndDate());

        Notification updatedNotification = notificationService.updateNotification(id, existingNotification);
        return ResponseEntity.ok(updatedNotification);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        
        Notification notification = notificationService.getNotificationById(id);
        if (!notification.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Notification does not belong to this faculty");
        }
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}