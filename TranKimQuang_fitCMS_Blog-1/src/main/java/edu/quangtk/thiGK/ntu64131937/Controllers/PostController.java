package edu.quangtk.thiGK.ntu64131937.Controllers;

import edu.quangtk.thiGK.ntu64131937.Models.Post;
import edu.quangtk.thiGK.ntu64131937.Services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Hiển thị danh sách bài viết
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "web/Post/list_post";
    }

    // Hiển thị form thêm bài viết
    @GetMapping("/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new Post(null, "", "", ""));
        return "web/Post/add_post";
    }

    // Xử lý thêm bài viết
    @PostMapping("/add")
    public String addPost(@ModelAttribute("post") Post post) {
        postService.addPost(post.getTitle(), post.getContent(), post.getCategoryID());
        return "redirect:/posts";
    }

    // Hiển thị bài viết theo danh mục (nếu cần)
    @GetMapping("/category/{categoryID}")
    public String displayPostsByCategory(@PathVariable String categoryID, Model model) {
        model.addAttribute("posts", postService.getPostsByCategory(categoryID));
        return "web/Post/list_post";
    }
}