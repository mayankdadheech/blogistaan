package com.blogistaan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.blogistaan.dao.UserRepository;
import com.blogistaan.entity.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository user_repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userByUserName = user_repo.getUserByUserName(username);
		CustomUserDetails customUserDetails = new CustomUserDetails(userByUserName);
		return customUserDetails;
	}

}
