package edu.quangtk.controllers;

import edu.quangtk.entity.Event;
import edu.quangtk.entity.Page;
import edu.quangtk.entity.Post;
import edu.quangtk.services.EventService;
import edu.quangtk.services.PageService;
import edu.quangtk.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FacultyAdminController {

    @Autowired
    private PostService postService;

    @Autowired
    private PageService pageService;

    @Autowired
    private EventService eventService;

    @GetMapping("/facultyadmin")
    public String showFacultyAdminDashboard(Model model) {
        model.addAttribute("title", "Faculty Admin Dashboard");
        model.addAttribute("posts", postService.getAllPostsByFaculty(null));
        model.addAttribute("pages", pageService.getAllPagesByFaculty(null));
        model.addAttribute("events", eventService.getAllEventsByFaculty(null));
        model.addAttribute("pendingPosts", postService.getPendingPosts(null));
        model.addAttribute("newPost", new Post());
        model.addAttribute("newPage", new Page());
        model.addAttribute("newEvent", new Event());
        return "pages/facultyadmin";
    }

    @PostMapping("/facultyadmin/create-post")
    public String createPost(@ModelAttribute("newPost") Post post, Model model) {
        try {
            postService.createPost(post);
            model.addAttribute("success", "Post created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create post: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/update-post")
    public String updatePost(@RequestParam("id") Long id, @ModelAttribute("post") Post post, Model model) {
        try {
            postService.updatePost(id, post);
            model.addAttribute("success", "Post updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update post: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/delete-post")
    public String deletePost(@RequestParam("id") Long id, Model model) {
        try {
            postService.deletePost(id);
            model.addAttribute("success", "Post deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete post: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/create-page")
    public String createPage(@ModelAttribute("newPage") Page page, Model model) {
        try {
            pageService.createPage(page);
            model.addAttribute("success", "Page created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create page: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/update-page")
    public String updatePage(@RequestParam("id") Long id, @ModelAttribute("page") Page page, Model model) {
        try {
            pageService.updatePage(id, page);
            model.addAttribute("success", "Page updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update page: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/delete-page")
    public String deletePage(@RequestParam("id") Long id, Model model) {
        try {
            pageService.deletePage(id);
            model.addAttribute("success", "Page deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete page: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/create-event")
    public String createEvent(@ModelAttribute("newEvent") Event event, Model model) {
        try {
            eventService.createEvent(event);
            model.addAttribute("success", "Event created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create event: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/update-event")
    public String updateEvent(@RequestParam("id") Long id, @ModelAttribute("event") Event event, Model model) {
        try {
            eventService.updateEvent(id, event);
            model.addAttribute("success", "Event updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update event: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/delete-event")
    public String deleteEvent(@RequestParam("id") Long id, Model model) {
        try {
            eventService.deleteEvent(id);
            model.addAttribute("success", "Event deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete event: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }

    @PostMapping("/facultyadmin/approve-post")
    public String approvePost(@RequestParam("id") Long id, Model model) {
        try {
            postService.approvePost(id);
            model.addAttribute("success", "Post approved successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to approve post: " + e.getMessage());
        }
        return "redirect:/facultyadmin";
    }
}