package com.blogistaan.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogistaan.entity.Posts;

public interface PostsRepository extends JpaRepository<Posts, Integer>{
//	@Query("Select p from Posts p where p.bId= :bId")
//	public Posts getUserByBlogId(@Param("bId") int bId);
}
