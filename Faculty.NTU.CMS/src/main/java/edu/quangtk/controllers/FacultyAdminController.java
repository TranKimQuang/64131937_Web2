package edu.quangtk.controllers;

import edu.quangtk.entity.*;
import edu.quangtk.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/facultyadmin")
public class FacultyAdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PageService pageService;

    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String showFacultyAdminDashboard(Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        Long facultyId = currentUser.getFaculty().getId();

        model.addAttribute("title", "Faculty Admin Dashboard");
        model.addAttribute("categories", categoryService.getAllCategoriesByFaculty(facultyId));
        model.addAttribute("pages", pageService.getAllPagesByFaculty(facultyId));
        model.addAttribute("posts", postService.getAllPostsByFaculty(facultyId));
        model.addAttribute("notifications", notificationService.getAllNotificationsByFaculty(facultyId));
        model.addAttribute("events", eventService.getAllEventsByFaculty(facultyId));
        model.addAttribute("menus", menuService.getMenusByFaculty(facultyId));
        model.addAttribute("newCategory", new Category());
        model.addAttribute("newPage", new Page());
        model.addAttribute("newPost", new Post());
        model.addAttribute("newNotification", new Notification());
        model.addAttribute("newEvent", new Event());
        model.addAttribute("newMenu", new Menu());
        model.addAttribute("content", "fragments/dashboard-admin :: dashboard-admin");
        model.addAttribute("request", request); // Truyền request vào model
        return "layout/layout";
    }

    @GetMapping("/categories")
    public String showCategoryManagement(Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        Long facultyId = currentUser.getFaculty().getId();

        model.addAttribute("title", "Manage Categories");
        model.addAttribute("categories", categoryService.getAllCategoriesByFaculty(facultyId));
        model.addAttribute("newCategory", new Category());
        model.addAttribute("content", "fragments/category-management :: categoryManagement");
        model.addAttribute("request", request); // Truyền request vào model
        return "layout/layout";
    }

    @PostMapping("/create-category")
    public String createCategory(@ModelAttribute("newCategory") Category category, Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        category.setFaculty(currentUser.getFaculty());
        try {
            categoryService.createCategory(category);
            model.addAttribute("success", "Category created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create category: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/categories";
    }

    @PostMapping("/update-category")
    public String updateCategory(@RequestParam("id") Long id, @ModelAttribute("category") Category category, Model model, HttpServletRequest request) {
        try {
            categoryService.updateCategory(id, category);
            model.addAttribute("success", "Category updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update category: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/categories";
    }

    @PostMapping("/delete-category")
    public String deleteCategory(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
        try {
            categoryService.deleteCategory(id);
            model.addAttribute("success", "Category deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete category: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/categories";
    }

    @GetMapping("/pages")
    public String showPageManagement(Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        Long facultyId = currentUser.getFaculty().getId();

        model.addAttribute("title", "Manage Pages");
        model.addAttribute("pages", pageService.getAllPagesByFaculty(facultyId));
        model.addAttribute("newPage", new Page());
        model.addAttribute("content", "fragments/page-management :: pageManagement");
        model.addAttribute("request", request); // Truyền request vào model
        return "layout/layout";
    }

    @PostMapping("/create-page")
    public String createPage(@ModelAttribute("newPage") Page page, Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        page.setFaculty(currentUser.getFaculty());
        try {
            pageService.createPage(page);
            model.addAttribute("success", "Page created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create page: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/pages";
    }

    @PostMapping("/update-page")
    public String updatePage(@RequestParam("id") Long id, @ModelAttribute("page") Page page, Model model, HttpServletRequest request) {
        try {
            pageService.updatePage(id, page);
            model.addAttribute("success", "Page updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update page: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/pages";
    }

    @PostMapping("/delete-page")
    public String deletePage(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
        try {
            pageService.deletePage(id);
            model.addAttribute("success", "Page deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete page: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/pages";
    }

    @GetMapping("/posts")
    public String showPostManagement(Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        Long facultyId = currentUser.getFaculty().getId();

        model.addAttribute("title", "Manage Posts");
        model.addAttribute("posts", postService.getAllPostsByFaculty(facultyId));
        model.addAttribute("newPost", new Post());
        model.addAttribute("content", "fragments/post-management :: postManagement");
        model.addAttribute("request", request); // Truyền request vào model
        return "layout/layout";
    }

    @PostMapping("/create-post")
    public String createPost(@ModelAttribute("newPost") Post post, Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        post.setFaculty(currentUser.getFaculty());
        post.setCreatedBy(currentUser);
        try {
            postService.createPost(post);
            model.addAttribute("success", "Post created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create post: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/posts";
    }

    @PostMapping("/update-post")
    public String updatePost(@RequestParam("id") Long id, @ModelAttribute("post") Post post, Model model, HttpServletRequest request) {
        try {
            postService.updatePost(id, post);
            model.addAttribute("success", "Post updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update post: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/posts";
    }

    @PostMapping("/delete-post")
    public String deletePost(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
        try {
            postService.deletePost(id);
            model.addAttribute("success", "Post deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete post: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/posts";
    }

    @GetMapping("/notifications")
    public String showNotificationManagement(Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        Long facultyId = currentUser.getFaculty().getId();

        model.addAttribute("title", "Manage Notifications");
        model.addAttribute("notifications", notificationService.getAllNotificationsByFaculty(facultyId));
        model.addAttribute("newNotification", new Notification());
        model.addAttribute("content", "fragments/notification-management :: notificationManagement");
        model.addAttribute("request", request); // Truyền request vào model
        return "layout/layout";
    }

    @PostMapping("/create-notification")
    public String createNotification(@ModelAttribute("newNotification") Notification notification, Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        notification.setFaculty(currentUser.getFaculty());
        try {
            notificationService.createNotification(notification);
            model.addAttribute("success", "Notification created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create notification: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/notifications";
    }

    @PostMapping("/update-notification")
    public String updateNotification(@RequestParam("id") Long id, @ModelAttribute("notification") Notification notification, Model model, HttpServletRequest request) {
        try {
            notificationService.updateNotification(id, notification);
            model.addAttribute("success", "Notification updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update notification: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/notifications";
    }

    @PostMapping("/delete-notification")
    public String deleteNotification(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
        try {
            notificationService.deleteNotification(id);
            model.addAttribute("success", "Notification deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete notification: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/notifications";
    }

    @GetMapping("/events")
    public String showEventManagement(Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        Long facultyId = currentUser.getFaculty().getId();

        model.addAttribute("title", "Manage Events");
        model.addAttribute("events", eventService.getAllEventsByFaculty(facultyId));
        model.addAttribute("newEvent", new Event());
        model.addAttribute("content", "fragments/event-management :: eventManagement");
        model.addAttribute("request", request); // Truyền request vào model
        return "layout/layout";
    }

    @PostMapping("/create-event")
    public String createEvent(@ModelAttribute("newEvent") Event event, Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        event.setFaculty(currentUser.getFaculty());
        try {
            eventService.createEvent(event);
            model.addAttribute("success", "Event created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create event: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/events";
    }

    @PostMapping("/update-event")
    public String updateEvent(@RequestParam("id") Long id, @ModelAttribute("event") Event event, Model model, HttpServletRequest request) {
        try {
            eventService.updateEvent(id, event);
            model.addAttribute("success", "Event updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update event: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/events";
    }

    @PostMapping("/delete-event")
    public String deleteEvent(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
        try {
            eventService.deleteEvent(id);
            model.addAttribute("success", "Event deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete event: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/events";
    }

    @GetMapping("/menus")
    public String showMenuManagement(Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        Long facultyId = currentUser.getFaculty().getId();

        model.addAttribute("title", "Manage Menus");
        model.addAttribute("menus", menuService.getMenusByFaculty(facultyId));
        model.addAttribute("newMenu", new Menu());
        model.addAttribute("content", "fragments/menu-management :: menuManagement");
        model.addAttribute("request", request); // Truyền request vào model
        return "layout/layout";
    }

    @PostMapping("/create-menu")
    public String createMenu(@ModelAttribute("newMenu") Menu menu, Model model, HttpServletRequest request) {
        User currentUser = userService.getUserByUsername("facultyadmin");
        if (currentUser == null) {
            throw new IllegalStateException("User 'facultyadmin' not found in the database. Please create a test user.");
        }
        menu.setFaculty(currentUser.getFaculty());
        try {
            menuService.createMenu(menu);
            model.addAttribute("success", "Menu created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create menu: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/menus";
    }

    @PostMapping("/update-menu")
    public String updateMenu(@RequestParam("id") Long id, @ModelAttribute("menu") Menu menu, Model model, HttpServletRequest request) {
        try {
            menuService.updateMenu(id, menu);
            model.addAttribute("success", "Menu updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update menu: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/menus";
    }

    @PostMapping("/delete-menu")
    public String deleteMenu(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
        try {
            menuService.deleteMenu(id);
            model.addAttribute("success", "Menu deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete menu: " + e.getMessage());
        }
        model.addAttribute("request", request); // Truyền request vào model
        return "redirect:/facultyadmin/menus";
    }
}