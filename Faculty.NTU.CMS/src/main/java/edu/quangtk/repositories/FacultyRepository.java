package edu.quangtk.repositories;

import edu.quangtk.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findBySlug(String slug);
}