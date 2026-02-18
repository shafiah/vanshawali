package com.example.vanshawali3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vanshawali3.entities.SignUpUser;
@Repository
public interface SignUpUserRepository extends JpaRepository<SignUpUser, Long> {

	Optional<SignUpUser> findByUsername(String username);
}
