package com.blogistaan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("title", "User-Dashboard - Blogistaan");
		return "/user/user-dashboard";
	}
}
