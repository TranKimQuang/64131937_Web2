package edu.quangtk.TongHopGK.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/studentList")
    public String list() {
        return "list";
    }
    
    @GetMapping("/addNew")
    public String addNew() {
        return "addNew";
    }
}

