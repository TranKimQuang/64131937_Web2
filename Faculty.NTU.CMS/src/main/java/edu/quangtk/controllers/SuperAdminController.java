package edu.quangtk.controllers;

import edu.quangtk.entity.Faculty;
import edu.quangtk.entity.User;
import edu.quangtk.services.FacultyService;
import edu.quangtk.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserService userService;

    // Kết hợp các handler cho các route chính để tránh trùng lặp
    @GetMapping({"", "/faculties", "/users"})
    public String showSuperAdminPage(Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI()); // Để active menu ở sidebar

        if ("/superadmin/faculties".equals(request.getRequestURI())) {
            model.addAttribute("title", "Quản lý Khoa");
            model.addAttribute("newFaculty", new Faculty());
            model.addAttribute("faculties", facultyService.getAllFaculties());
            model.addAttribute("content", "fragments/faculty-management :: facultyManagement"); // Sử dụng cú pháp fragment mới
        } else if ("/superadmin/users".equals(request.getRequestURI())) {
            model.addAttribute("title", "Quản lý Người dùng");
            model.addAttribute("newUser", new User());
            model.addAttribute("users", userService.getAllUsers(null));
            model.addAttribute("faculties", facultyService.getAllFaculties()); // Truyền danh sách khoa để dùng trong form
            model.addAttribute("content", "fragments/user-management :: userManagement"); // Sử dụng cú pháp fragment mới
        } else {
            // Trang dashboard
            model.addAttribute("title", "Super Admin Dashboard");
            long userCount = userService.getUserCount();
            long facultyCount = facultyService.getFacultyCount();
            model.addAttribute("userCount", userCount);
            model.addAttribute("facultyCount", facultyCount);
            model.addAttribute("content", "fragments/dashboard-admin :: dashboard-admin"); // Sử dụng cú pháp fragment mới
        }

        return "layout/layout"; // Luôn trả về trang layout chính
    }

    @PostMapping("/create-faculty")
    public String createFaculty(@ModelAttribute("newFaculty") Faculty faculty, RedirectAttributes redirectAttributes) {
        try {
            facultyService.createFaculty(faculty);
            redirectAttributes.addFlashAttribute("success", "Khoa đã được tạo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tạo khoa: " + e.getMessage());
        }
        return "redirect:/superadmin/faculties";
    }

    @PostMapping("/update-faculty")
    public String updateFaculty(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("slug") String slug,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes) {
        try {
            Faculty faculty = facultyService.getFacultyById(id);
            faculty.setName(name);
            faculty.setSlug(slug);
            faculty.setDescription(description);
            facultyService.updateFaculty(id, faculty);
            redirectAttributes.addFlashAttribute("success", "Khoa đã được cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật khoa: " + e.getMessage());
        }
        return "redirect:/superadmin/faculties";
    }

    @PostMapping("/delete-faculty")
    public String deleteFaculty(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            facultyService.deleteFaculty(id);
            redirectAttributes.addFlashAttribute("success", "Khoa đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa khoa: " + e.getMessage());
        }
        return "redirect:/superadmin/faculties";
    }

    @PostMapping("/create-user")
    public String createUser(@ModelAttribute("newUser") User user,
                             @RequestParam(value = "faculty.id", required = false) Long facultyId,
                             RedirectAttributes redirectAttributes) {
        try {
            if (facultyId != null) {
                Faculty faculty = facultyService.getFacultyById(facultyId);
                user.setFaculty(faculty);
            }
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("success", "Người dùng đã được tạo thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tạo người dùng: " + e.getMessage());
        }
        return "redirect:/superadmin/users";
    }

    @PostMapping("/update-user")
    public String updateUser(
            @RequestParam("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam(value = "faculty.id", required = false) Long facultyId,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(id);
            user.setUsername(username);
            if (!password.isEmpty()) {
                user.setPassword(password);
            }
            user.setFullName(fullName);
            user.setEmail(email);
            user.setRole(role);
            if (facultyId != null) {
                Faculty faculty = facultyService.getFacultyById(facultyId);
                user.setFaculty(faculty);
            } else {
                user.setFaculty(null);
            }
            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("success", "Người dùng đã được cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật người dùng: " + e.getMessage());
        }
        return "redirect:/superadmin/users";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Người dùng đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa người dùng: " + e.getMessage());
        }
        return "redirect:/superadmin/users";
    }
}

