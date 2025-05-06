package edu.quangtk.controllers;

import com.ntu.facultycms.entity.Event;
import com.ntu.facultycms.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> createEvent(@PathVariable Long facultyId, @RequestBody Event event) {
        event.setFacultyId(facultyId);
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Event>> getAllEvents(@PathVariable Long facultyId) {
        return ResponseEntity.ok(eventService.getAllEventsByFaculty(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> getEventById(@PathVariable Long facultyId, @PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (!event.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Event does not belong to this faculty");
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> getEventBySlug(@PathVariable Long facultyId, @PathVariable String slug) {
        Event event = eventService.getEventBySlug(slug);
        if (!event.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Event does not belong to this faculty");
        }
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Event> updateEvent(@PathVariable Long facultyId, @PathVariable Long id, @RequestBody Event event) {
        event.setFacultyId(facultyId);
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @Deletesrcdir/main/java/com/ntu/facultycms/controller/EventController.java
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long facultyId, @PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (!event.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Event does not belong to this faculty");
        }
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}