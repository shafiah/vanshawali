package com.example.vanshawali3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vanshawali3.entities.SignUpUser;
import com.example.vanshawali3.service.SignUpUserService;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v5/vanshawali/signupuser")
public class SignUpUserController {

	@Autowired
	private SignUpUserService signUpUserService;
	
	@PostMapping("/create")
	public ResponseEntity<SignUpUser> createSignUpUser(@RequestBody SignUpUser signUpUser){
		
		SignUpUser createdSignUpUser=signUpUserService.saveSignUpUser(signUpUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSignUpUser);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<SignUpUser>> signUpUserList(){
		List<SignUpUser> signUpUserList=signUpUserService.getSignUpUserList();
		return ResponseEntity.ok(signUpUserList);
	}
	@GetMapping("/{singUpUserId}")
	public ResponseEntity<SignUpUser> getSignUpUserId(@PathVariable long singUpUserId){
		SignUpUser signUpUserId=signUpUserService.getSignUpUserId(singUpUserId);
		return ResponseEntity.ok(signUpUserId);
	}
}
