package edu.quangtk.services;

import edu.quangtk.entity.Event;
import edu.quangtk.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEventsByFaculty(Long facultyId) {
        return eventRepository.findByFacultyId(facultyId);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    public Event getEventBySlug(String slug) {
        return eventRepository.findBySlug(slug);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event event = getEventById(id);
        event.setTitle(updatedEvent.getTitle());
        event.setSlug(updatedEvent.getSlug());
        event.setDescription(updatedEvent.getDescription());
        event.setEventDate(updatedEvent.getEventDate());
        event.setLocation(updatedEvent.getLocation());
        event.setFaculty(updatedEvent.getFaculty());
        event.setCreatedBy(updatedEvent.getCreatedBy());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }
}