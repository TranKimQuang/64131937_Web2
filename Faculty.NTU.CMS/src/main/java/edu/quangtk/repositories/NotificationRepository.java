package edu.quangtk.repositories;

import edu.quangtk.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByFacultyId(Long facultyId);

    @Query("SELECT n FROM Notification n WHERE " +
            "n.faculty.id = :facultyId AND " +
            "(:currentTime BETWEEN n.startDate AND n.endDate OR " +
            "(n.startDate IS NULL AND n.endDate IS NULL))")
     List<Notification> findActiveNotifications(
         @Param("facultyId") Long facultyId,
         @Param("currentTime") LocalDateTime currentTime);
}