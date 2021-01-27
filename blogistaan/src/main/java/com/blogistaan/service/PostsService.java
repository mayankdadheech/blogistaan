package com.blogistaan.service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogistaan.dao.BlogRepository;
import com.blogistaan.dao.PostsRepository;
import com.blogistaan.entity.Blog;
import com.blogistaan.entity.Posts;

@Service
public class PostsService {
	
	@Autowired
	PostsRepository post_repo;
	
	@Autowired
	BlogRepository blog_repo;
	
	@Autowired
	BlogService blogService;
	
	public void getPostByBlogId(int bId) {
//		List<Posts> postByBlogId = post_repo.getPostByBlogId(bId);
		
	}
	
	public void addPostToBlog(Posts post, int bId, Date date1) {
//		System.out.println(post);
		Blog blog = blogService.findById(bId);
		System.out.println("testing1");
//		if(post.getDate()==null) {
		if(date1==null) {
//			System.out.println("testing-1");
			long millis=System.currentTimeMillis();  

//			System.out.println("testing0");
			java.sql.Date date=new java.sql.Date(millis);
			System.out.println(date);
			post.setDate(date);
//			System.out.println("testing1");
		}
		else {
			post.setDate(date1);
		}
		post.setBlog(blog);
//		System.out.println("testing2");
		blog.getPosts().add(post);
		blog_repo.save(blog);
	}
	
	public Posts fidById(int postId) {
		Optional<Posts> post = post_repo.findById(postId);
		if(post.isPresent()) {
			return post.get();
		}
		else {
			return null;
		}
	}

	public List<Posts> getPostContent(Blog blog) {
		List<Posts> posts = blog.getPosts();
		int i=0;
		String  all = null;
//		List<String> cont = new ArrayList<>();
		while(posts.size()>i) {
			System.out.println("i= " + i);
			List<Pattern> list = new ArrayList<>();
			 list.add(Pattern.compile("<p>"));
			 list.add(Pattern.compile("</p>"));
//			 list.add(Pattern.compile("<h.>"));
//			 list.add(Pattern.compile("</h.>"));
			 list.add(Pattern.compile("<..>"));
			 list.add(Pattern.compile("</..>"));
			 list.add(Pattern.compile("&nbsp;"));
			 StringBuffer ssb = new StringBuffer();
			 System.out.println("Content is : " + posts.get(i).getContent());
			 ssb.append(posts.get(i).getContent());
//			 posts.remove(i);
//			 System.out.println("psos.get-i: ");
//			 System.out.println("post.get-i: "+ posts.get(i));
			 for(Pattern p: list) {
				 Matcher mt = p.matcher(ssb.toString());
				 ssb.setLength(0);
				 while(mt.find()) {
					 mt.appendReplacement(ssb, " ");
				 }
				 mt.appendTail(ssb);
			 }
			 System.out.println("ssb.toString method: " + ssb.toString());
			 String betweenn;
			 all=ssb.toString();
			 while(all.contains("<")) {
				 betweenn = between(all, "<", ">");
				 System.out.println("betweenn"+betweenn + " aaaaaa");
				 all = all.replace(betweenn, " ");
			 }
			 System.out.println("all: ");
			 System.out.println(all);
			 posts.get(i).setContent(all.substring(0, 10)+"...");
			 ++i;
		}
		return posts;
	}
	
	public String between(String str, String first, String last) {
		int f = str.indexOf(first);
		System.out.println("f: "+ f);
		if(f==-1) {
			return "";
		}
		int l = str.indexOf(last);
		System.out.println("l: "+ l);
		if(l==-1) {
			return "";
		}
//		int adjustedf= f + first.length();
		if(f >= l) {
			return "";
		}
		
		return str.substring(f, ++l);
	}
	
}
