package edu.quangtk.DemoSpringBoot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello(ModelMap m){
		m.addAttribute("tb", "Dữ liệu thông báo xin chào.");
	return "helloView";
}
}
