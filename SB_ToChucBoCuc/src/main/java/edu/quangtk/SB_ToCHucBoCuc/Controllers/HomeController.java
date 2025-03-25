package edu.quangtk.SB_ToCHucBoCuc.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Trả về tên template "index.html" trong thư mục templates/
    }
}
