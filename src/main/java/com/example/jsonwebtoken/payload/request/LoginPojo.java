package com.example.jsonwebtoken.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginPojo {
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
