package ksmart.ks48git.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String MainPage(Model model) {
		model.addAttribute("title", "메인화면");
		return "main";
	}
}
