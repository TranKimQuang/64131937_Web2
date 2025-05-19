package edu.quangtk.controllers;

import edu.quangtk.entity.Event;
import edu.quangtk.entity.Faculty;
import edu.quangtk.entity.Notification;
import edu.quangtk.services.EventService;
import edu.quangtk.services.FacultyService;
import edu.quangtk.services.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private EventService eventService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public String showDashboard(Model model, HttpServletRequest request) {
        model.addAttribute("title", "NTU Dashboard");
        List<Faculty> faculties = facultyService.getAllFaculties();
        model.addAttribute("faculties", faculties);
        List<Event> allEvents = eventService.getAllEventsByFaculty(null);
        model.addAttribute("allEvents", allEvents);
        List<Notification> allNotifications = notificationService.getAllNotificationsByFaculty(null);
        model.addAttribute("allNotifications", allNotifications);
        model.addAttribute("content", "dashboard");
        model.addAttribute("requestURI", request.getRequestURI());
        return "layout/layout";
    }

    @GetMapping("/faculty/{slug}")
    public String showFacultyDashboard(@PathVariable String slug, Model model, HttpServletRequest request) {
        Faculty faculty = facultyService.getFacultyBySlug(slug);
        if (faculty == null) {
            // Xử lý trường hợp không tìm thấy khoa
            return "error/404";
        }
        model.addAttribute("title", "Faculty Dashboard - " + faculty.getName());
        model.addAttribute("faculty", faculty);
        List<Event> facultyEvents = eventService.getAllEventsByFaculty(faculty.getId());
        model.addAttribute("facultyEvents", facultyEvents);
        List<Notification> facultyNotifications = notificationService.getAllNotificationsByFaculty(faculty.getId()); // Truyền facultyId
        model.addAttribute("facultyNotifications", facultyNotifications);
        model.addAttribute("content", "fragments/faculty-management :: facultyManagement");
        model.addAttribute("requestURI", request.getRequestURI());
        return "layout/layout";
    }
}
