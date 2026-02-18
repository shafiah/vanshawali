package com.example.vanshawali3.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vanshawali3.entities.User;
import com.example.vanshawali3.exception.ResourceNotFoundException;
import com.example.vanshawali3.repositories.UserRepository;
import com.example.vanshawali3.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public List<User> getUserlist() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUserId(long userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("ResourceNotFound on the server."+userId));
	}

	@Override
	public User updateUser(long userId, User user) {
		// TODO Auto-generated method stub
		User updatedUser=userRepository.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("ResourceNotFound on the server."+userId));
		updatedUser.setFirstName(user.getFirstName());
		updatedUser.setLastName(user.getLastName());
		updatedUser.setAddress(user.getAddress());
		updatedUser.setMobile(user.getMobile());
		userRepository.save(updatedUser);
		return updatedUser;
	}

	@Override
	public User deleteUser(long userId) {
		// TODO Auto-generated method stub
		User deletedUser=userRepository.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("ResourceNotFound on the server."+userId));
		userRepository.delete(deletedUser);
		return deletedUser;
	}

	@Override
	public List<User> getUsersBySignupUserId(Long signupUserId) {
		// TODO Auto-generated method stub
		return userRepository.findBySignUpUser_SignUpUserId(signupUserId);
	}

}
