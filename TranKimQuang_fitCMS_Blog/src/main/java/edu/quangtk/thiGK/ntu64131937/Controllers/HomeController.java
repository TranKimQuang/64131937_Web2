package edu.quangtk.thiGK.ntu64131937.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("content", "dashboard :: content");
        return "layout/main";     
    }

    @GetMapping("dashboard")
    public String dashboard(Model model) {
        return home(model);
    }
}
