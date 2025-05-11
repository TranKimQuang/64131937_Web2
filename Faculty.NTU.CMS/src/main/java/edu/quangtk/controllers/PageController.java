package edu.quangtk.controllers;

import edu.quangtk.entity.Faculty;
import edu.quangtk.entity.Page;
import edu.quangtk.entity.User;
import edu.quangtk.services.FacultyService;
import edu.quangtk.services.PageService;
import edu.quangtk.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/pages")
public class PageController {
    private final PageService pageService;
    private final FacultyService facultyService;
    private final UserService userService;

    public PageController(PageService pageService, 
                        FacultyService facultyService,
                        UserService userService) {
        this.pageService = pageService;
        this.facultyService = facultyService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> createPage(
            @PathVariable Long facultyId,
            @RequestBody Page page,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Faculty faculty = facultyService.getFacultyById(facultyId);
        User creator = userService.getUserByUsername(userDetails.getUsername());
        
        page.setFaculty(faculty);
        page.setCreatedBy(creator);
        
        Page createdPage = pageService.createPage(page);
        return ResponseEntity.ok(createdPage);
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Page>> getAllPages(@PathVariable Long facultyId) {
        List<Page> pages = pageService.getAllPagesByFaculty(facultyId);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> getPageById(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        
        Page page = pageService.getPageById(id);
        if (!page.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Page does not belong to this faculty");
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> getPageBySlug(
            @PathVariable Long facultyId,
            @PathVariable String slug) {
        
        Page page = pageService.getPageBySlug(slug);
        if (!page.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Page does not belong to this faculty");
        }
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page> updatePage(
            @PathVariable Long facultyId,
            @PathVariable Long id,
            @RequestBody Page pageDetails) {
        
        Page existingPage = pageService.getPageById(id);
        if (!existingPage.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Page does not belong to this faculty");
        }

        // Update only allowed fields
        existingPage.setTitle(pageDetails.getTitle());
        existingPage.setSlug(pageDetails.getSlug());
        existingPage.setContent(pageDetails.getContent());
        
        Page updatedPage = pageService.updatePage(id, existingPage);
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deletePage(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        
        Page page = pageService.getPageById(id);
        if (!page.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Page does not belong to this faculty");
        }
        pageService.deletePage(id);
        return ResponseEntity.noContent().build();
    }
}