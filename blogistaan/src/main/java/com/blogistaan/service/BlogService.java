package com.blogistaan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogistaan.dao.BlogRepository;
import com.blogistaan.dao.UserRepository;
import com.blogistaan.entity.Blog;
import com.blogistaan.entity.User;

@Service
public class BlogService {
	
	@Autowired
	BlogRepository blog_repo;
	
	@Autowired
	UserRepository user_repo;
	
	public String checkUrl(String blogUrl) {
//		Blog blog = blog_repo.getBlogByBlogUrl(blogUrl);
		Blog blog = getBlogByBlogUrl(blogUrl);
		System.out.println("Blog: "+ blog);
//		Response response = new Response();
//		System.out.println("Response: "+response);
		if(blog==null) {
//			response.setStatus("This url is available");
//			response.setData(blog);
			return "This url is available";
		}
		else{
//			response.setStatus("This url is unavailable");
//			response.setData(blog);
			return "This url is unavailable";
		}
	}
	
	public String createBlog(String userEmail, Blog blog) {
		User user = user_repo.getUserByUserName(userEmail);
		if(blog.getCategory() == null) {
			blog.setCategory("Other");
		}
		blog.setUser(user);
		user.getBlogs().add(blog);
		user_repo.save(user);
		return "";
	}
	
	public Blog findById(int bId) {

		System.out.println("checkPoint2");
		Optional<Blog> blog = blog_repo.findById(bId);
		if(blog.isPresent()) {
			return blog.get();
		}
		else {
			return null;
		}
//		System.out.println("to Ccheck null or not: "+blog);
//		return blog;
	}
	
	public Blog getBlogByBlogUrl(String blogUrl) {
		Blog blog = blog_repo.getBlogByBlogUrl(blogUrl);
		return blog;
	}
}
