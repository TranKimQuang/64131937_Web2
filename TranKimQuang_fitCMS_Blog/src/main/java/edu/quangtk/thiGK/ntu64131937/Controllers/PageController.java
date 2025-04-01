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
        // Hard-code dữ liệu CV của sinh viên
        List<Page> pages = new ArrayList<>();

        // CV 1: Nguyễn Văn A
        pages.add(new Page(
            "Nguyễn Văn A",
            "nguyen-van-a, sinh-vien, cntt",
            "<h3>CV - Nguyễn Văn A</h3>" +
            "<p><strong>MSSV:</strong> 64131937</p>" +
            "<p><strong>Ngành học:</strong> Công nghệ Thông tin</p>" +
            "<p><strong>Email:</strong> nguyen.van.a@edu.vn</p>" +
            "<p><strong>Số điện thoại:</strong> 0123 456 789</p>" +
            "<p><strong>Kỹ năng:</strong> Java, Spring Boot, HTML, CSS</p>" +
            "<p><strong>Kinh nghiệm:</strong> Thực tập tại FPT Software (6 tháng)</p>" +
            "<p><strong>Học vấn:</strong> Đại học Nguyễn Tất Thành (2019-2023)</p>",
            null
        ));

        // CV 2: Trần Thị B
        pages.add(new Page(
            "Trần Thị B",
            "tran-thi-b, sinh-vien, marketing",
            "<h3>CV - Trần Thị B</h3>" +
            "<p><strong>MSSV:</strong> 64131938</p>" +
            "<p><strong>Ngành học:</strong> Marketing</p>" +
            "<p><strong>Email:</strong> tran.thi.b@edu.vn</p>" +
            "<p><strong>Số điện thoại:</strong> 0987 654 321</p>" +
            "<p><strong>Kỹ năng:</strong> Digital Marketing, SEO, Content Creation</p>" +
            "<p><strong>Kinh nghiệm:</strong> Làm việc tại công ty quảng cáo XYZ (1 năm)</p>" +
            "<p><strong>Học vấn:</strong> Đại học Nguyễn Tất Thành (2020-2024)</p>",
            1L
        ));

        model.addAttribute("pages", pages);
        model.addAttribute("title", "Page List");
        model.addAttribute("content", "pages/page-list :: content");
        return "main";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("title", "Add New Page");
        model.addAttribute("content", "pages/page-add :: content");
        return "main";
    }
}