package com.blogistaan.controller;


import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blogistaan.entity.Blog;
import com.blogistaan.entity.CheckName;
import com.blogistaan.entity.Posts;
import com.blogistaan.helper.Message;
import com.blogistaan.service.BlogService;
import com.blogistaan.service.HomeService;
import com.blogistaan.service.PostsService;
import com.blogistaan.util.Response;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	HomeService homeService;
	
	@Autowired
	PostsService postsService;
	
	@GetMapping("/index")
	public String index(Model model, Principal principal) {
//		String userEmail = principal.getName();
//		List<Blog> blogDetails = homeService.getBlogDetails(userEmail);
//		model.addAttribute("blogDetails", blogDetails);
//		model.addAttribute("title", "User-Dashboard - Blogistaan");
//		return "/user/user-dashboard";
		
//		String userEmail = principal.getName();
//		int userId = homeService.getUserDetails(userEmail).getId();
////		Blog blog1 = blogService.findById(bId);
//		List<Blog> blogDetails = homeService.getBlogDetails(userEmail);
//		model.addAttribute("title", "User-Dashboard - Blogistaan");
//		if(blogDetails.isEmpty()) {
//			return "/user/user-dashboard";
//		}
//		else if(userId == blogDetails.get(0).getUser().getId()) {
//			model.addAttribute("blogDetails", blogDetails);
//			model.addAttribute("blog1", blogDetails.get(0));
//			return "/user/user-dashboard";
//		}
//		else {
//			model.addAttribute("error-message", "Error in displaying the data");
//			return "/user/user-dashboard";
//		}
		
		
		String userEmail = principal.getName();
		List<Blog> blogDetails = homeService.getBlogDetails(userEmail);
		if(!blogDetails.isEmpty()) {
			return "redirect:/user/index/"+ blogDetails.get(0).getBId();
		}
		return "/user/user-dashboard";
		
		

//		String userEmail = principal.getName();
//		int bId = homeService.getFirstBlog(userEmail);
//		if(bId!=-1) {
//			return "redirect:/user/index/"+ bId;
//		}
//		return "/user/user-dashboard";
	}
	
	@GetMapping("/index/{bId}")
	public String indexWithBlogId(Model model, Principal principal, @PathVariable("bId") int bId) {
		String userEmail = principal.getName();
		int userId = homeService.getUserDetails(userEmail).getId();
		System.out.println("checkPoint1");
		Blog blogOnPage = blogService.findById(bId);
		System.out.println("checkPoint3");
		System.out.println("Check if returned null true or false : ");
		System.out.println("Check if returned null true or false : " + blogOnPage==null + "...");
		List<Blog> blogDetails = homeService.getBlogDetails(userEmail);
		model.addAttribute("title", "User-Dashboard - Blogistaan");
		if(blogDetails.isEmpty() || (blogOnPage == null) ) {
			model.addAttribute("message", new Message("Error in displaying the data", "alert-danger"));
			return "/user/user-dashboard";
		}
		else if(userId == blogOnPage.getUser().getId()) {
//			postsService.getPostByBlogId(blogOnPage.getBId());
			System.out.println("tester");
			List<Posts> posts = blogOnPage.getPosts();
			System.out.println("Post test check: "+ posts);
			model.addAttribute("posts", posts);
			model.addAttribute("blogDetails", blogDetails);
			model.addAttribute("blog1", blogOnPage);
			return "/user/user-dashboard";
		}
		else {
//		blogDetails.isEmpty() || 
			model.addAttribute("message", new Message("Error in displaying the data", "alert-danger"));
			return "/user/user-dashboard";
		}
	}
	
	@RequestMapping("/addBlog")
	public String addBlog(Model model) {
		model.addAttribute("title", "Create new blog - Blogistaan");
		return "/user/add-new-blog";
	}
	
	@PostMapping("/checkName")
	@ResponseBody
	public Response checkName(Principal principal, Model model, HttpSession session, HttpServletRequest request) {
//		ModelAndView modelAndView = new ModelAndView();
//		String name = request.getParameter("name");
		
		 CheckName n = new CheckName("Mayank"); 
		 Response response = new Response("Done", n); 
//		 CommonUtils.ConvertObjectToJSON(modelAndView).toString();
//		modelAndView.addObject("response","Mayank");
		System.out.println("Working");
		return response;
	}
	
	@PostMapping("/checkBlogUrl")
	@ResponseBody
	public String checkBlogUrl(@RequestParam("blogUrl") String blogUrl) {
		String response = blogService.checkUrl(blogUrl);
		System.out.println(response);
		return response;
	}
	
//	@PostMapping("/checkBlogUrl")
//	@ResponseBody
//	public Response checkBlogUrl(@RequestParam("blogUrl") String blogUrl, Model model) {
//		Response response = blogService.checkUrl(blogUrl);
//		System.out.println(response);
////		String blogUrls = request.getParameter("blogUrl");
////		Blog blog = blog_repo.getBlogByBlogUrl(blogUrls);
////		System.out.println("Blog: "+ blog);
////		Response response = new Response();
////		System.out.println("Response: "+response);
////		if(blog==null) {
////			response.setStatus("This url is available");
////			response.setData(blog);
////			return response;
////		}
////		else {
////			response.setStatus("This url is unavailable");
////			response.setData(null);
////			return response;
////		}
//		
////		return response;
//	}
	
	@PostMapping("/createBlog")
	public String createBlog(@ModelAttribute("blog") Blog blog, Principal principal) {
		String userEmail = principal.getName();
		blogService.createBlog(userEmail, blog);
		return "redirect:/user/index";
	}

}

