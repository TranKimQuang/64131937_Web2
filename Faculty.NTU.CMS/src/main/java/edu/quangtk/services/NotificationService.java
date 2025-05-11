package edu.quangtk.services;

import edu.quangtk.entity.Notification;
import edu.quangtk.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getActiveNotifications(Long facultyId) {
        return notificationRepository.findActiveNotifications(facultyId, LocalDateTime.now());
    }

    public List<Notification> getAllNotificationsByFaculty(Long facultyId) {
        return notificationRepository.findByFacultyId(facultyId);
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    public Notification updateNotification(Long id, Notification updatedNotification) {
        Notification notification = getNotificationById(id);
        notification.setTitle(updatedNotification.getTitle());
        notification.setContent(updatedNotification.getContent());
        notification.setStartDate(updatedNotification.getStartDate());
        notification.setEndDate(updatedNotification.getEndDate());
        notification.setFaculty(updatedNotification.getFaculty());
        notification.setCreatedBy(updatedNotification.getCreatedBy());
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
}