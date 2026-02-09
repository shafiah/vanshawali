package com.example.vanshawali3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vanshawali3.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	 List<User> findBySignUpUser_SignUpUserId(Long signUpUserId);
}
