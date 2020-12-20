package com.blogistaan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormalController {
	@GetMapping("/home")
	@ResponseBody
	public String demo() {
		return "Success";
	}
}
