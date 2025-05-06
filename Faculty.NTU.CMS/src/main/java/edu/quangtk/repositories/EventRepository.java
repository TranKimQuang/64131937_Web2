package edu.quangtk.repositories;

import edu.quangtk.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByFacultyId(Long facultyId);
    Event findBySlug(String slug);
}