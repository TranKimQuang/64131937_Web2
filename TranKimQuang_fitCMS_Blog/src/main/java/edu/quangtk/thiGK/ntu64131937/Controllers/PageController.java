package edu.quangtk.thiGK.ntu64131937.Controllers;

import edu.quangtk.thiGK.ntu64131937.Models.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pages")
public class PageController {

    @GetMapping("/list")
    public String listPages(Model model) {
        List<Page> pages = new ArrayList<>();
        pages.add(new Page("Nguyễn Văn A", "nguyen-van-a, sinh-vien, cntt", "...", null));
        pages.add(new Page("Trần Thị B", "tran-thi-b, sinh-vien, marketing", "...", 1L));
        model.addAttribute("pages", pages);
        model.addAttribute("title", "Page List");
        model.addAttribute("content", "pageList :: content");
        return "layout/main"; 
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("title", "Add New Page");
        model.addAttribute("content", "pageAdd :: content");
        return "layout/main"; 
    }
}