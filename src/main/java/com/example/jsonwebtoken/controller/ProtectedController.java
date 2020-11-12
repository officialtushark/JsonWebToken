package com.example.jsonwebtoken.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jsonwebtoken.payload.response.MessageResponseBody;

@Controller
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProtectedController {
	
	@GetMapping("/all") 
	public String allAccess() {
		return "Ello!";
		//return ResponseEntity.ok(new MessageResponse("This is a public content!"));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','MODERATOR','ADMIN')")
	public ResponseEntity<?> user() {
		return ResponseEntity.ok(new MessageResponseBody("This is a user content!"));
	}
	
	@GetMapping("/mod")
	@PreAuthorize("hasAnyRole('MODERATOR')")
	public ResponseEntity<?> mod() {
		return ResponseEntity.ok(new MessageResponseBody("This is a moderator content!"));
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> admin() {
		return ResponseEntity.ok(new MessageResponseBody("This is a Admin content!"));
	}
}
