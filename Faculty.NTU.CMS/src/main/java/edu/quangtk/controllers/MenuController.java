package edu.quangtk.controllers;

import com.ntu.facultycms.entity.Menu;
import com.ntu.facultycms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Menu> createMenu(@PathVariable Long facultyId, @RequestBody Menu menu) {
        menu.setFacultyId(facultyId);
        return ResponseEntity.ok(menuService.createMenu(menu));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Menu>> getMenusByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(menuService.getMenusByFaculty(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long facultyId, @PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        if (!menu.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Menu does not belong to this faculty");
        }
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long facultyId, @PathVariable Long id, @RequestBody Menu menu) {
        menu.setFacultyId(facultyId);
        return ResponseEntity.ok(menuService.updateMenu(id, menu));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long facultyId, @PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        if (!menu.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Menu does not belong to this faculty");
        }
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}