package com.example.vanshawali3.service;

import java.util.List;

import com.example.vanshawali3.entities.User;

public interface UserService {

	public User saveUser(User user);
	
	public List<User> getUserlist();
	
	public User getUserId(long userId);
	
	public User updateUser(long userId,User user);
	
	public User deleteUser(long userId);
	
	// 🔥 ADD THIS
    List<User> getUsersBySignupUserId(Long signupUserId);
}
