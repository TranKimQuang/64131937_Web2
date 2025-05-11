package edu.quangtk.controllers;

import edu.quangtk.entity.Event;
import edu.quangtk.entity.Faculty;
import edu.quangtk.services.EventService;
import edu.quangtk.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/events")
public class EventController {
    private final EventService eventService;
    private final FacultyService facultyService;

    @Autowired
    public EventController(EventService eventService, FacultyService facultyService) {
        this.eventService = eventService;
        this.facultyService = facultyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> createEvent(
            @PathVariable Long facultyId, 
            @RequestBody Event event) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        event.setFaculty(faculty);
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Event>> getAllEvents(@PathVariable Long facultyId) {
        return ResponseEntity.ok(eventService.getAllEventsByFaculty(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> getEventById(
            @PathVariable Long facultyId, 
            @PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (!event.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Event does not belong to this faculty");
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> getEventBySlug(
            @PathVariable Long facultyId, 
            @PathVariable String slug) {
        Event event = eventService.getEventBySlug(slug);
        if (!event.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Event does not belong to this faculty");
        }
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long facultyId, 
            @PathVariable Long id, 
            @RequestBody Event event) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        event.setFaculty(faculty);
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long facultyId, 
            @PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (!event.getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Event does not belong to this faculty");
        }
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}