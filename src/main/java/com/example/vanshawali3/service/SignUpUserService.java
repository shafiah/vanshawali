package com.example.vanshawali3.service;

import java.util.List;

import com.example.vanshawali3.entities.SignUpUser;

public interface SignUpUserService {

	public SignUpUser saveSignUpUser(SignUpUser signUpUser);
	
	public List<SignUpUser> getSignUpUserList();
	
	public SignUpUser getSignUpUserId(long signUpUserId);
}
