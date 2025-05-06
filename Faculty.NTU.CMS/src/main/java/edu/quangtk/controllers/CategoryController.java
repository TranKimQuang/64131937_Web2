package edu.quangtk.controllers;

import com.ntu.facultycms.entity.Category;
import com.ntu.facultycms.service.CategoryService;
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
    public ResponseEntity<Category> createCategory(@PathVariable Long facultyId, @RequestBody Category category) {
        category.setFacultyId(facultyId);
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Category>> getAllCategories(@PathVariable Long facultyId) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByFaculty(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long facultyId, @PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (!category.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Category does not belong to this faculty");
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long facultyId, @PathVariable Long id, @RequestBody Category category) {
        category.setFacultyId(facultyId);
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long facultyId, @PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (!category.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Category does not belong to this faculty");
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}