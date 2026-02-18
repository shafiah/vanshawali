package com.example.vanshawali3.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vanshawali3.entities.SignUpUser;
import com.example.vanshawali3.exception.ResourceNotFoundException;
import com.example.vanshawali3.repositories.SignUpUserRepository;
import com.example.vanshawali3.service.SignUpUserService;
@Service
public class SignUpUserServiceImpl implements SignUpUserService{
	
	@Autowired
	private SignUpUserRepository signUpUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public SignUpUser saveSignUpUser(SignUpUser signUpUser) {
		// TODO Auto-generated method stub
		// 🔐 BCrypt password encode
	    signUpUser.setPassword(
	            passwordEncoder.encode(signUpUser.getPassword())
	    );
		return signUpUserRepository.save(signUpUser);
	}

	@Override
	public List<SignUpUser> getSignUpUserList() {
		// TODO Auto-generated method stub
		return signUpUserRepository.findAll();
	}

	@Override
	public SignUpUser getSignUpUserId(long signUpUserId) {
		// TODO Auto-generated method stub
		return signUpUserRepository.findById(signUpUserId)
				.orElseThrow(()-> new ResourceNotFoundException("ResourceNotFound on the server."+signUpUserId));
	}

}
