package edu.quangtk.controllers;

import edu.quangtk.entity.Category;
import edu.quangtk.entity.Faculty;
import edu.quangtk.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Category> createCategory(
            @PathVariable Faculty facultyId,
            @RequestBody Category category) {
        category.setFaculty(facultyId);
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Category>> getAllCategories(@PathVariable Long facultyId) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByFaculty(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<?> getCategoryById(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (!category.getFaculty().equals(facultyId)) {
            return ResponseEntity.status(403).body("Category does not belong to this faculty");
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<?> updateCategory(
            @PathVariable Faculty facultyId,
            @PathVariable Long id,
            @RequestBody Category category) {
        Category existing = categoryService.getCategoryById(id);
        if (!existing.getFaculty().equals(facultyId)) {
            return ResponseEntity.status(403).body("Category does not belong to this faculty");
        }
        category.setFaculty(facultyId);
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (!category.getFaculty().equals(facultyId)) {
            return ResponseEntity.status(403).body("Category does not belong to this faculty");
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
