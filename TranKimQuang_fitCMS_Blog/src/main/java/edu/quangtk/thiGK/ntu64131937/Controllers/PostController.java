package edu.quangtk.thiGK.ntu64131937.Controllers;

import edu.quangtk.thiGK.ntu64131937.Models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/list")
    public String listPosts(Model model) {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "First Post", "This is the first post content", 1L));
        posts.add(new Post(2L, "Second Post", "This is the second post content", 2L));

        model.addAttribute("posts", posts);
        model.addAttribute("title", "Danh sách bài viết");
        model.addAttribute("content", "postList :: content");

        return "layout/main"; 
    }

    @GetMapping("/add")
    public String addPost(Model model) {
        model.addAttribute("title", "Thêm bài viết mới");
        model.addAttribute("content", "postAdd :: content");
        return "layout/main"; 
    }
}
