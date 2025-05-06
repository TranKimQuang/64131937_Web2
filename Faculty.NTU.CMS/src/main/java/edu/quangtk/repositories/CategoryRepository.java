package edu.quangtk.repositories;

import edu.quangtk.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByFacultyId(Long facultyId);
    Category findBySlug(String slug);
}