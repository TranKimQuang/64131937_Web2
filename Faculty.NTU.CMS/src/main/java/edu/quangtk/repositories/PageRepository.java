package edu.quangtk.repositories;

import edu.quangtk.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Long> {
    List<Page> findByFacultyId(Long facultyId);
    Page findBySlug(String slug);
}