package edu.quangtk.SB_BMI.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BMIController {

    @GetMapping("/bmi")
    public String showBMIForm() {
        return "bmi"; // Trả về bmi.html
    }

    @PostMapping("/calculateBMI")
    public ModelAndView calculateBMI(
            @RequestParam("height") double height,
            @RequestParam("weight") double weight) {
        
        ModelAndView modelAndView = new ModelAndView();
        double bmi = weight / (height * height);
        String category;
        if (bmi < 18.5) {
            category = "Gầy (Thiếu cân)";
        } else if (bmi >= 18.5 && bmi < 25) {
            category = "Bình thường";
        } else if (bmi >= 25 && bmi < 30) {
            category = "Thừa cân";
        } else {
            category = "Béo phì";
        }

        modelAndView.setViewName("bmiResult");
        modelAndView.addObject("bmi", String.format("%.2f", bmi));
        modelAndView.addObject("category", category);
        modelAndView.addObject("height", height);
        modelAndView.addObject("weight", weight);

        return modelAndView;
    }
}