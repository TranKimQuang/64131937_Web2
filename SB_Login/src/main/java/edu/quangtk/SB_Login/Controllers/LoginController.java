package edu.quangtk.SB_Login.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/loginn")
    public String showLoginForm() {
        return "login"; // Trả về file login.html
    }

    @PostMapping("/loginn")
    public ModelAndView login(@RequestParam("id") String id, @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();

        // Logic kiểm tra đăng nhập (ví dụ đơn giản)
        if ("admin".equals(id) && "12345".equals(password)) {
            modelAndView.setViewName("welcome"); // Chuyển hướng đến trang welcome nếu thành công
            modelAndView.addObject("message", "Đăng nhập thành công, chào mừng " + id + "!");
        } else {
            modelAndView.setViewName("login"); // Quay lại trang login nếu thất bại
            modelAndView.addObject("error", "ID hoặc mật khẩu không đúng!");
        }

        return modelAndView;
    }
}