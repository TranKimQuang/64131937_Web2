package edu.quangtk.controllers;

import edu.quangtk.services.FacultyService;
import edu.quangtk.services.PostService;
import edu.quangtk.services.UserService;
import edu.quangtk.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long facultyCount = facultyService.getAllFaculties().size();
        long userCount = userService.getAllUsers(null).size();
        long postCount = postService.getAllPostsByFaculty(null).size();
        long notificationCount = notificationService.getActiveNotifications(null).size();

        model.addAttribute("title", "Dashboard");
        model.addAttribute("facultyCount", facultyCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("postCount", postCount);
        model.addAttribute("notificationCount", notificationCount);

        return "pages/dashboard";
    }
}