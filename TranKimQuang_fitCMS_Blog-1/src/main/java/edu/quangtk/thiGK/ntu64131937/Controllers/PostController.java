package edu.quangtk.thiGK.ntu64131937.Controllers;

import edu.quangtk.thiGK.ntu64131937.Models.Post;
import edu.quangtk.thiGK.ntu64131937.Services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

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

    // Hiển thị bài viết theo danh mục
    @GetMapping("/category/{categoryID}")
    public String displayPostsByCategory(@PathVariable String categoryID, Model model) {
        model.addAttribute("posts", postService.getPostsByCategory(categoryID));
        return "web/Post/list_post";
    }

    // Hiển thị chi tiết bài viết
    @GetMapping("/{id}")
    public String viewPost(@PathVariable String id, Model model) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/posts"; 
        }
        model.addAttribute("post", post);
        return "web/Post/view_post";
    }

    // Hiển thị form chỉnh sửa bài viết
    @GetMapping("/edit/{id}")
    public String showEditPostForm(@PathVariable String id, Model model) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/posts"; 
        }
        model.addAttribute("post", post);
        return "web/Post/edit_post";
    }

    // Xử lý chỉnh sửa bài viết
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable String id, @ModelAttribute("post") Post updatedPost) {
        postService.updatePost(id, updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getCategoryID());
        return "redirect:/posts";
    }

    // Xử lý xóa bài viết
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    // Getter để PageController có thể truy cập danh sách bài viết
    public PostService getPostService() {
        return postService;
    }
}