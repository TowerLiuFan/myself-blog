package com.liufan.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liufan.blog.dao.UserRepository;
import com.liufan.blog.pojo.User;
import com.liufan.blog.util.MD5Utils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRes;

	@Override
	public User checkUser(String username, String password) {
		User user = userRes.findByUsernameAndPassword(username, MD5Utils.code(password));
		return user;
	}

}
