package com.example.jsonwebtoken.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupPojo {
	@NotBlank
	@Size(min = 3,max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private Set<String> role;
}
