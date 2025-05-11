package edu.quangtk.controllers;

import edu.quangtk.entity.Faculty;
import edu.quangtk.entity.User;
import edu.quangtk.services.FacultyService;
import edu.quangtk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuperAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/superadmin")
    public String showSuperAdminDashboard(Model model) {
        model.addAttribute("title", "Super Admin Dashboard");
        model.addAttribute("users", userService.getAllUsers(null));
        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("newUser", new User());
        model.addAttribute("newFaculty", new Faculty());
        return "pages/superadmin";
    }

    @PostMapping("/superadmin/create-user")
    public String createUser(@ModelAttribute("newUser") User user, Model model) {
        try {
            userService.createUser(user);
            model.addAttribute("success", "User created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create user: " + e.getMessage());
        }
        return "redirect:/superadmin";
    }

    @PostMapping("/superadmin/update-user")
    public String updateUser(@RequestParam("id") Long id, @ModelAttribute("user") User user, Model model) {
        try {
            userService.updateUser(id, user);
            model.addAttribute("success", "User updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update user: " + e.getMessage());
        }
        return "redirect:/superadmin";
    }

    @PostMapping("/superadmin/create-faculty")
    public String createFaculty(@ModelAttribute("newFaculty") Faculty faculty, Model model) {
        try {
            facultyService.createFaculty(faculty);
            model.addAttribute("success", "Faculty created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create faculty: " + e.getMessage());
        }
        return "redirect:/superadmin";
    }
}