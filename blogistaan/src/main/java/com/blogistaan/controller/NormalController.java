package com.blogistaan.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blogistaan.dao.UserRepository;
import com.blogistaan.entity.Blog;
import com.blogistaan.entity.Posts;
import com.blogistaan.entity.User;
import com.blogistaan.helper.Message;
import com.blogistaan.service.BlogService;
import com.blogistaan.service.HomeService;
import com.blogistaan.service.PostsService;



@Controller
public class NormalController {
	
	@Autowired
	UserRepository user_repo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	PostsService postsService;
	
	@Autowired
	HomeService homeService;
	
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
			user.setPassword(passwordEncoder.encode(user.getPassword() ) );
//			System.out.println("Here it is. 1..");
			User result = user_repo.save(user);
			System.out.println("Here it is. 2..");
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
	
	@RequestMapping(value = "/signin")
	public String signin(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		model.addAttribute("u_id", user_repo.getUserByUserName(auth.getName()).getId());
//		System.out.println(auth.getName() + "helloooooo");
	    if (!(auth instanceof AnonymousAuthenticationToken)) {
//	        User loggedInUser = user_repo.getUserByUserName(auth.getName());
	        model.addAttribute("u_id", user_repo.getUserByUserName(auth.getName()).getId());
	        /* The user is logged in :) */
	        return "redirect:/user/index";
	    }
	    
		model.addAttribute("title", "Signin - Blogistaan");
		return "/login";
	}
	
	@GetMapping("/{blogUrl}")
	public String getBlogByUrl(@PathVariable("blogUrl") String blogUrl, Model model) {
		Blog blog = blogService.getBlogByBlogUrl(blogUrl);
		if(blog != null && blog.getPosts() != null) {
//			System.out.println("getposts: "+ blog.getPosts());
			model.addAttribute("postContentPlainText", postsService.getPostContent(blog));
			System.out.println("getposts: "+ blog.getPosts());
//			model.addAttribute("postsList", blog.getPosts());
			model.addAttribute("user", blog.getUser());
			model.addAttribute("blog", blog);
			return "/open-blogs";
		}
		return "";
	}
	
	@GetMapping("/{id}/{bId}/{postId}")
	public String blogMainContent(@PathVariable("id") int id, @PathVariable("bId") int bId, @PathVariable("postId") int postId, Model model) {
		User user= homeService.findById(id);
		Blog blog = blogService.findById(bId);
		Posts post = postsService.fidById(postId);
		if(user != null || blog != null || post != null) {
			model.addAttribute("user", user);
			model.addAttribute("blog", blog);
			model.addAttribute("post", post);
			return "/blog-main-content";
		}
		return "";
	}
}
