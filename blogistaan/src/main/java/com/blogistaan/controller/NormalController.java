package com.blogistaan.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogistaan.dao.UserRepository;
import com.blogistaan.entity.User;
import com.blogistaan.helper.Message;



@Controller
public class NormalController {
	
	@Autowired
	UserRepository user_repo;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	
	@GetMapping("/home")
	public String demo(Model model) {
		return "/home";
	}
	
	@PostMapping("/login")
	public String login() {
		return "/login";
	}
	
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("title", "Signup - Blogistaan");
		model.addAttribute("user", new User());
//		model.addAttribute("fakeSession", new Message());
		return "/signup";
	}
	
	@RequestMapping(value="/do-action", method=RequestMethod.POST)
	public String doAction(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value="agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
		
		try {
			if(bindingResult.hasErrors()) {
				System.out.println(bindingResult);
				return "signup";
			}
			if(!agreement) {
				System.out.println("The agreement checkbox error.");
				throw new Exception("The agreement checkbox error.");
			}
			System.out.println(user.getCountry());
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImage("default_image");
//			user.setPassword(passwordEncoder.encode(user.getPassword() ) );
//			System.out.println("Here it is. 1..");
			User result = user_repo.save(user);
//			System.out.println("Here it is. 2..");
			System.out.println(result);
//			System.out.println("Here it is. 3..");
			model.addAttribute("user", result);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user", user);
			Message message= new Message("Please click agreement.", "alert-danger");
			session.setAttribute("message",message);
			return "/signup";
		}
		return "/home";
	}
}
