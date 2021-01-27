package com.blogistaan.controller;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.multipart.MultipartFile;

import com.blogistaan.entity.Blog;
import com.blogistaan.entity.CheckName;
import com.blogistaan.entity.Posts;
import com.blogistaan.entity.User;
import com.blogistaan.helper.Message;
import com.blogistaan.service.BlogService;
import com.blogistaan.service.HomeService;
import com.blogistaan.service.PostsService;
import com.blogistaan.util.CommonUtils;
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
		
		String userEmail = principal.getName();
		List<Blog> blogDetails = homeService.getBlogDetails(userEmail);
		if(!blogDetails.isEmpty()) {
			return "redirect:/user/index/"+ blogDetails.get(0).getBId();
		}
		return "/user/user-dashboard";
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
		if(!blogDetails.isEmpty() && (blogOnPage != null) && userId == blogOnPage.getUser().getId()) {
			System.out.println("tester");
			List<Posts> posts = blogOnPage.getPosts();
			System.out.println("Post test check: "+ posts);
			model.addAttribute("posts", posts);
			model.addAttribute("blogDetails", blogDetails);
			model.addAttribute("blog1", blogOnPage);
			return "/user/user-dashboard";
		}
		else {
			model.addAttribute("message", new Message("Error in displaying the data", "alert-danger"));
			model.addAttribute("blogDetails", blogDetails);
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
		 CheckName n = new CheckName("Mayank"); 
		 Response response = new Response("Done", n); 
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
	
	@RequestMapping("/{bId}/addPost")
	public String addNewPost(Principal principal, Model model, @PathVariable("bId") int bId) {
		String userEmail = principal.getName();
		int userId = homeService.getUserDetails(userEmail).getId();
		System.out.println("checkPoint1");
		Blog blogOnPage = blogService.findById(bId);
		System.out.println("checkPoint3");
		System.out.println("Check if returned null true or false : ");
		System.out.println("Check if returned null true or false : " + blogOnPage==null + "...");
		List<Blog> blogDetails = homeService.getBlogDetails(userEmail);
		model.addAttribute("title", "Create new post - Blogistaan");
		
		  if(!blogDetails.isEmpty() && (blogOnPage != null) && userId == blogOnPage.getUser().getId()) {
			System.out.println("tester");
			model.addAttribute("blogDetails", blogDetails);
			model.addAttribute("blog1", blogOnPage);
			model.addAttribute("singlePost", new Posts());
			return "/user/add-new-post";
		}
		else {
			System.out.println("this is done: ");
			model.addAttribute("message", new Message("Error in displaying the data", "alert-danger"));
			model.addAttribute("blogDetails", blogDetails);
			return "/user/add-new-post";
		}
	}
	
	@PostMapping("/postProcess")
	public String postProcess(@ModelAttribute("post") Posts post, @RequestParam("bId") int bId, @RequestParam("date1") String date1, Model model) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date parse = null;
		Date dateSql = null;
		try {
			parse = df.parse(date1);
			dateSql = new java.sql.Date(parse.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postsService.addPostToBlog(post, bId, dateSql);
		return "redirect:/user/index/"+ bId;
	}
	
//	@PostMapping("/uploader")
//	@ResponseBody
//	public String imageUploader(Model model) {
//		System.out.println("uploader controller");
//		return "abc";
//	}
	
	@PostMapping("/image")
	@ResponseBody
	public String saveImage(@RequestParam("file") MultipartFile file) {
	    String url = null;
	    System.out.println("File name: " + file.getOriginalFilename());
		try {
			url = copyFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return url;
	}
	
	
	private String copyFile(MultipartFile file) throws Exception {		
		String url = null;
		String fileName = file.getOriginalFilename();
		
		try (InputStream is = file.getInputStream()) {
			Path path = CommonUtils.getImagePath(fileName);

			Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
			
			url = CommonUtils.getImageUrl(fileName);
			System.out.println("Final url is: "+ url);
		} catch (IOException ie) {
			ie.printStackTrace();
			throw new Exception("Failed to upload!");
		}
		
		return url;
	}
	
	@PostMapping("/{bId}/edit/{postId}")
	public String editPost(@PathVariable("postId") int postId, @PathVariable("bId") int bId ,Model model) {
		System.out.println(postId);
		Blog blogOnPage = blogService.findById(bId);
		List<Blog> blogDetails = blogOnPage.getUser().getBlogs();
		model.addAttribute("singlePost", postsService.fidById(postId));
		model.addAttribute("blogDetails", blogDetails);
		model.addAttribute("blog1", blogOnPage);
		return "/user/add-new-post";
	}
	
	@GetMapping("/userProfile")
	public String userProfile(Model model, Principal principal) {
		model.addAttribute("blogDetails", homeService.getUserDetails(principal.getName()).getBlogs());
		return "/user/user-profile";
	}
	
	@PostMapping("/userProfileImage")
	public String userProfileImage(@RequestParam("file") MultipartFile file, Principal principal) {
		String fileName = file.getOriginalFilename();
		try (InputStream is = file.getInputStream()) {
			Path path = CommonUtils.getUserProfileImagePath(fileName, homeService.getUserDetails(principal.getName()));

			Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
			
//			url = CommonUtils.getImageUrl(fileName);
//			System.out.println("Final url is: "+ url);
		} catch (IOException ie) {
			ie.printStackTrace();
//			throw new Exception("Failed to upload!");
		}
		return "";
	}

}

