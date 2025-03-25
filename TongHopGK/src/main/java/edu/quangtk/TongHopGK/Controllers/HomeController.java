package edu.quangtk.TongHopGK.Controllers;

import edu.quangtk.TongHopGK.Models.SinhVien;
import edu.quangtk.TongHopGK.Services.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private SinhVienService sinhVienService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/studentList")
    public String list(Model model) {
        model.addAttribute("dssv", sinhVienService.getAllSinhVien());
        return "list";
    }

    @GetMapping("/addNew")
    public String addNew(Model model) {
        model.addAttribute("sinhVien", new SinhVien(null, "", 0.0f)); // mssv là null
        return "addNew";
    }

    @PostMapping("/addNew")
    public String addNewSubmit(@ModelAttribute SinhVien sinhVien) {
        sinhVienService.addSinhVien(sinhVien);
        return "redirect:/studentList";
    }
}