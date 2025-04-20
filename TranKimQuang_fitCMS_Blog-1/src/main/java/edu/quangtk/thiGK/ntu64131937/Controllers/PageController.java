package edu.quangtk.thiGK.ntu64131937.Controllers;

import edu.quangtk.thiGK.ntu64131937.Models.Page;
import edu.quangtk.thiGK.ntu64131937.Services.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pages")
public class PageController {
    private PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    // Hiển thị danh sách trang
    @GetMapping
    public String listPages(Model model) {
        model.addAttribute("pages", pageService.getAllPages());
        return "web/Page/list_page";
    }

    // Hiển thị form thêm trang
    @GetMapping("/add")
    public String showAddPageForm(Model model) {
        model.addAttribute("page", new Page(null, "", "", "", null));
        return "web/Page/add_page";
    }

    // Xử lý thêm trang
    @PostMapping("/add")
    public String addPage(@ModelAttribute("page") Page page) {
        pageService.addPage(page.getPageName(), page.getKeyword(), page.getContent(), page.getParentPageID());
        return "redirect:/pages";
    }

    // Hiển thị các trang con (nếu cần)
    @GetMapping("/children/{parentPageID}")
    public String displayChildPages(@PathVariable Long parentPageID, Model model) {
        model.addAttribute("pages", pageService.getChildPages(parentPageID));
        return "web/Page/list_page";
    }
}