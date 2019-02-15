package com.liufan.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liufan.blog.pojo.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsernameAndPassword(String username,String password);
	
	
}
