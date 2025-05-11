package edu.quangtk.controllers;

import edu.quangtk.entity.Faculty;
import edu.quangtk.services.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    // Sử dụng constructor injection thay vì field injection
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        List<Faculty> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        return ResponseEntity.ok(faculty);
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    public ResponseEntity<Faculty> updateFaculty(
//            @PathVariable Long id, 
//            @RequestBody Faculty faculty) {
//        Faculty updatedFaculty = facultyService.updateFaculty(id, faculty);
//        return ResponseEntity.ok(updatedFaculty);
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }
}