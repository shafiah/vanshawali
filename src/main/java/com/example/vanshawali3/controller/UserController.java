package com.example.vanshawali3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.vanshawali3.entities.SignUpUser;
import com.example.vanshawali3.entities.User;
import com.example.vanshawali3.service.SignUpUserService;
import com.example.vanshawali3.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v5/vanshawali/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SignUpUserService signUpUserService;

	@GetMapping("/home")
	public String home() {
		
		return "welcome to home page";
	}
	
	
//	@PostMapping("/create")
//	public ResponseEntity<User> createUser(@RequestBody User user){
//		User createdUser=userService.saveUser(user);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(
			HttpServletRequest request,
	        @RequestParam String firstName,
	        @RequestParam String lastName,
	        @RequestParam String address,
	        @RequestParam String mobile,
	        @RequestParam(value = "photo", required = false) MultipartFile photo
	) throws IOException {
		
		Long signupUserId = (Long) request.getAttribute("signupUserId");

	    SignUpUser signupUser = signUpUserService.getSignUpUserId(signupUserId);

	    User user = new User();
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setAddress(address);
	    user.setMobile(mobile);
	    user.setSignUpUser(signupUser);

	    // Save user first
	    User savedUser = userService.saveUser(user);

	    if (photo != null && !photo.isEmpty()) {

	        // ✅ VALIDATION
	        if (!photo.getContentType().startsWith("image/")) {
	            throw new RuntimeException("Only image files allowed");
	        }

	        if (photo.getSize() > 2 * 1024 * 1024) {
	            throw new RuntimeException("Image size must be < 2MB");
	        }

	        String uploadDir = "/home/rayan/upload/users/";
	        File dir = new File(uploadDir);
	        if (!dir.exists()) dir.mkdirs();

	        String cleanName = photo.getOriginalFilename()
	                .replaceAll("\\s+", "_");

	        String fileName = "user_" + savedUser.getUserId() + "_" + cleanName;

	        Path path = Paths.get(uploadDir + fileName);
	        Files.write(path, photo.getBytes());

	        savedUser.setPhoto(fileName);
	        userService.saveUser(savedUser);
	    }

	    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
	
		
//	@GetMapping("/list")
//	public ResponseEntity<List<User>> userList(){
//		List<User> allUserList=userService.getUserlist();
//		return ResponseEntity.ok(allUserList);
//	}
//	@GetMapping("/list")
//	public ResponseEntity<List<User>> userList(HttpServletRequest request) {
//		Long signupUserId = (Long) request.getAttribute("signupUserId");
//		List<User> allUserList = userService.getUserlist();
//		allUserList.forEach(u -> {
//			if (u.getPhoto() != null) {
//				u.setPhoto("http://localhost:8085/upload/users/" + u.getPhoto());
//			}
//		});
//		return ResponseEntity.ok(allUserList);
//	}
	@GetMapping("/list")
	public ResponseEntity<List<User>> userList(HttpServletRequest request) {

	    Long signupUserId = (Long) request.getAttribute("signupUserId");

	    List<User> users = userService.getUsersBySignupUserId(signupUserId);

	    users.forEach(u -> {
	        if (u.getPhoto() != null) {
	            u.setPhoto("http://localhost:8085/upload/users/" + u.getPhoto());
	        }
	    });

	    return ResponseEntity.ok(users);
	}
//	@GetMapping("/{userId}")
//	public ResponseEntity<User> findByUserId(@PathVariable long userId){
//		User singleUserId=userService.getUserId(userId);
//		return ResponseEntity.ok(singleUserId);
//	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> findByUserId(@PathVariable long userId){

	    User user = userService.getUserId(userId);

	    if (user.getPhoto() != null) {
	        user.setPhoto("http://localhost:8085/upload/users/" + user.getPhoto());
	    }

	    return ResponseEntity.ok(user);
	}
	
//	@PutMapping("/update/{userId}")
//	public ResponseEntity<User> updateUser(@PathVariable long userId,@RequestBody User user){
//		User updatedUser=userService.updateUser(userId, user);
//		userService.saveUser(updatedUser);
//		return ResponseEntity.ok(updatedUser);
//	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<User> updateUser(
	        @PathVariable long userId,
	        @RequestParam("firstName") String firstName,
	        @RequestParam("lastName") String lastName,
	        @RequestParam("address") String address,
	        @RequestParam("mobile") String mobile,
	        @RequestParam(value = "photo", required = false) MultipartFile photo
	) throws IOException {

	    User user = userService.getUserId(userId);

	    // update text fields
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setAddress(address);
	    user.setMobile(mobile);

	    // if new photo uploaded
	    if (photo != null && !photo.isEmpty()) {

	        // validate image
	        if (!photo.getContentType().startsWith("image/")) {
	            throw new RuntimeException("Only image files allowed");
	        }

	        String uploadDir = "/home/rayan/upload/users/";
	        File dir = new File(uploadDir);
	        if (!dir.exists()) dir.mkdirs();

	        // delete old photo (important)
	        if (user.getPhoto() != null) {
	            File oldFile = new File(uploadDir + user.getPhoto());
	            if (oldFile.exists()) oldFile.delete();
	        }

	        String cleanName = photo.getOriginalFilename()
	                .replaceAll("\\s+", "_");

	        String fileName = "user_" + userId + "_" + cleanName;
	        Path path = Paths.get(uploadDir + fileName);
	        Files.write(path, photo.getBytes());

	        user.setPhoto(fileName);
	    }

	    User updatedUser = userService.saveUser(user);
	    return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable long userId){
		User deletedUserDetail=userService.deleteUser(userId);
		return new ResponseEntity<User>(deletedUserDetail,HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/upload-photo/{userId}")
	public ResponseEntity<User> uploadUserPhoto(
			@PathVariable long userId,
			@RequestParam("photo") MultipartFile photo) throws IOException {

		User user = userService.getUserId(userId);

		String uploadDir = "/home/rayan/upload/users/";
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String originalName = photo.getOriginalFilename();
		String cleanName = originalName.replaceAll("\\s+", "_");

		String fileName = "user_" + userId + "_" + cleanName;
		Path filePath = Paths.get(uploadDir + fileName);
		Files.write(filePath, photo.getBytes());

		user.setPhoto(fileName);
		userService.saveUser(user);

		return ResponseEntity.ok(user);
	}
	
}
