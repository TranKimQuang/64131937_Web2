package edu.quangtk.thiGK.ntu64131937.Controllers;

import edu.quangtk.thiGK.ntu64131937.Services.PageService;
import edu.quangtk.thiGK.ntu64131937.Services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PageService pageService;
    private final PostService postService;

    public HomeController(PageService pageService, PostService postService) {
        this.pageService = pageService;
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pages", pageService.getAllPages());
        model.addAttribute("posts", postService.getAllPosts());
        return "web/index";
    }
}