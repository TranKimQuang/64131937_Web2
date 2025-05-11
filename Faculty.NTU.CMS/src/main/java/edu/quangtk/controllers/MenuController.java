package edu.quangtk.controllers;

import edu.quangtk.entity.Faculty;
import edu.quangtk.entity.Menu;
import edu.quangtk.services.FacultyService;
import edu.quangtk.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/menus")
public class MenuController {
    private final MenuService menuService;
    private final FacultyService facultyService;

    public MenuController(MenuService menuService, FacultyService facultyService) {
        this.menuService = menuService;
        this.facultyService = facultyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Menu> createMenu(
            @PathVariable Long facultyId, 
            @RequestBody Menu menu) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        menu.setFaculty(faculty);
        return ResponseEntity.ok(menuService.createMenu(menu));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Menu>> getMenusByFaculty(@PathVariable Long facultyId) {
        List<Menu> menus = menuService.getMenusByFaculty(facultyId);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Menu> getMenuById(
            @PathVariable Long facultyId, 
            @PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        if (!menu.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Menu does not belong to this faculty");
        }
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Menu> updateMenu(
            @PathVariable Long facultyId, 
            @PathVariable Long id, 
            @RequestBody Menu menu) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        menu.setFaculty(faculty);
        return ResponseEntity.ok(menuService.updateMenu(id, menu));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long facultyId, 
            @PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        if (!menu.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Menu does not belong to this faculty");
        }
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}