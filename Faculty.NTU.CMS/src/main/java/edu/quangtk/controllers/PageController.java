package edu.quangtk.controllers;

import com.ntu.facultycms.entity.Page;
import com.ntu.facultycms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/pages")
public class PageController {
    @Autowired
    private PageService pageService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> createPage(@PathVariable Long facultyId, @RequestBody Page page) {
        page.setFacultyId(facultyId);
        return ResponseEntity.ok(pageService.createPage(page));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Page>> getAllPages(@PathVariable Long facultyId) {
        return ResponseEntity.ok(pageService.getAllPagesByFaculty(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> getPageById(@PathVariable Long facultyId, @PathVariable Long id) {
        Page page = pageService.getPageById(id);
        if (!page.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Page does not belong to this faculty");
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> getPageBySlug(@PathVariable Long facultyId, @PathVariable String slug) {
        Page page = pageService.getPageBySlug(slug);
        if (!page.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Page does not belong to this faculty");
        }
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> updatePage(@PathVariable Long facultyId, @PathVariable Long id, @RequestBody Page page) {
        page.setFacultyId(facultyId);
        return ResponseEntity.ok(pageService.updatePage(id, page));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deletePage(@PathVariable Long facultyId, @PathVariable Long id) {
        Page page = pageService.getPageById(id);
        if (!page.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Page does not belong to this faculty");
        }
        pageService.deletePage(id);
        return ResponseEntity.noContent().build();
    }
}