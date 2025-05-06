package edu.quangtk.repositories;

import edu.quangtk.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByFacultyId(Long facultyId);

    @Query("SELECT n FROM Notification n WHERE n.facultyId = :facultyId AND " +
           "(n.startDate <= :currentTime OR n.startDate IS NULL) AND " +
           "(n.endDate >= :currentTime OR n.endDate IS NULL)")
    List<Notification> findActiveNotifications(Long facultyId, LocalDateTime currentTime);
}