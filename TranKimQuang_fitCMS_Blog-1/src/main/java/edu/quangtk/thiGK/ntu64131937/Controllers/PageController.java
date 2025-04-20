package edu.quangtk.thiGK.ntu64131937.Controllers;

import edu.quangtk.thiGK.ntu64131937.Models.Page;
import edu.quangtk.thiGK.ntu64131937.Services.PageService;
import edu.quangtk.thiGK.ntu64131937.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pages")
public class PageController {
    private final PageService pageService;
    private PostService postService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @Autowired
    public void setPostService(PostController postController) {
        this.postService = postController.getPostService();
    }

    // Hiển thị trang chính (index)
    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("pages", pageService.getAllPages());
        model.addAttribute("posts", postService.getAllPosts());
        return "web/index";
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

    // Hiển thị các trang con
    @GetMapping("/children/{parentPageID}")
    public String displayChildPages(@PathVariable String parentPageID, Model model) {
        model.addAttribute("pages", pageService.getChildPages(parentPageID));
        return "web/Page/list_page";
    }

    // Hiển thị chi tiết trang
    @GetMapping("/{id}")
    public String viewPage(@PathVariable String id, Model model) {
        Page page = pageService.getPageById(id);
        if (page == null) {
            return "redirect:/pages"; 
        }
        model.addAttribute("page", page);
        return "web/Page/view_page";
    }

    // Hiển thị form chỉnh sửa trang
    @GetMapping("/edit/{id}")
    public String showEditPageForm(@PathVariable String id, Model model) {
        Page page = pageService.getPageById(id);
        if (page == null) {
            return "redirect:/pages"; 
        }
        model.addAttribute("page", page);
        return "web/Page/edit_page";
    }

    // Xử lý chỉnh sửa trang
    @PostMapping("/edit/{id}")
    public String editPage(@PathVariable String id, @ModelAttribute("page") Page updatedPage) {
        pageService.updatePage(id, updatedPage.getPageName(), updatedPage.getKeyword(), updatedPage.getContent(), updatedPage.getParentPageID());
        return "redirect:/pages";
    }

    // Xử lý xóa trang
    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable String id) {
        pageService.deletePage(id);
        return "redirect:/pages";
    }
}