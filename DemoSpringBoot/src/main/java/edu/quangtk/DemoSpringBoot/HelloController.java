package edu.quangtk.DemoSpringBoot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HelloController {
	
	@RequestMapping("/helloView")
	public String hello(){
	return "helloView";
}
}
