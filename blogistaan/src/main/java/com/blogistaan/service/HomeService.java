package com.blogistaan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogistaan.dao.UserRepository;
import com.blogistaan.entity.Blog;
import com.blogistaan.entity.User;

@Service
public class HomeService {
	
	@Autowired
	UserRepository user_repo;
	
	public List<Blog> getBlogDetails(String userEmail) {
		User user = user_repo.getUserByUserName(userEmail);
		List<Blog> blogs = user.getBlogs();
		return blogs;
	}
	
	public int getFirstBlog(String userEmail) {
		User user = user_repo.getUserByUserName(userEmail);
		if(user.getBlogs().isEmpty()) {
			System.out.println("inside if block user.getBlogs(): " + user.getBlogs());
			return -1;
		}
		Blog blog = user.getBlogs().get(0);
		System.out.println("First row of blog: " + blog);
		
		return blog.getBId();
	}
	
	public User getUserDetails(String userEmail) {
		return user_repo.getUserByUserName(userEmail);
	}
	
	public User findById(int id) {
		Optional<User> optional = user_repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}
}
