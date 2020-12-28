package com.blogistaan.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogistaan.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
	@Query("Select b from Blog b where b.blogUrl= :blogUrl")
	public Blog getBlogByBlogUrl(@Param("blogUrl") String blogUrl);
	
//	@Query("Select b from Blog b limit by 1")
//	public Blog getFirstBlogDetails();
}
