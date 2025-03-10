package edu.quangtk.SB_TruyenDuLieuSangView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class TruyenDuLieuController {
	
	@RequestMapping("/truyen")
	public String hello(ModelMap m){
		m.addAttribute("hovaten", "Trần Kim Quang");
		m.addAttribute("lop", "64CNTT_CLC");
		m.addAttribute("SDT", "0421314533");
		m.addAttribute("cccd", "352315141213");
		m.addAttribute("khoahoc", "2022-2026");
	return "TruyenDuLieuView";
}
}