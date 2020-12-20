package com.blogistaan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class NormalController {
	@GetMapping("/home")
	public String demo(Model model) {
		return "/home";
	}
}
