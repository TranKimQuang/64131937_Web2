package edu.quangtk.services;

import edu.quangtk.entity.Category;
import edu.quangtk.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategoriesByFaculty(Long facultyId) {
        return categoryRepository.findByFacultyId(facultyId);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        category.setSlug(updatedCategory.getSlug());
        category.setDescription(updatedCategory.getDescription());
        category.setFacultyId(updatedCategory.getFacultyId());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
