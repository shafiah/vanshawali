package com.example.vanshawali3.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="signup_user")
public class SignUpUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long signUpUserId;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "signUpUser",
	           cascade = CascadeType.ALL,
	           orphanRemoval = true
	          )
	private List<User> users;
	
}
