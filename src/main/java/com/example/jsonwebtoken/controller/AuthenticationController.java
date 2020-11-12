package com.example.jsonwebtoken.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jsonwebtoken.models.RoleEnum;
import com.example.jsonwebtoken.models.RoleEntity;
import com.example.jsonwebtoken.models.UserEntity;
import com.example.jsonwebtoken.payload.request.LoginPojo;
import com.example.jsonwebtoken.payload.request.SignupPojo;
import com.example.jsonwebtoken.payload.response.JwtResponseBody;
import com.example.jsonwebtoken.payload.response.MessageResponseBody;
import com.example.jsonwebtoken.repository.RoleRepository;
import com.example.jsonwebtoken.repository.UserRepository;
import com.example.jsonwebtoken.security.jwt.JwtUtility;
import com.example.jsonwebtoken.security.services.UserDetailsImplementation;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {
	
	@Autowired AuthenticationManager authenticationManager;
	@Autowired JwtUtility jwtUtility;
	@Autowired UserRepository userRepository;
	@Autowired RoleRepository roleRepository;
	@Autowired PasswordEncoder encoder;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginPojo loginRequest){
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwtUtility.generateJwtToken(authentication);
		UserDetailsImplementation userDetails=(UserDetailsImplementation)authentication.getPrincipal();
		List<String> role=userDetails.getAuthorities().stream().map(item-> item.getAuthority()).collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponseBody(token, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), role));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupPojo signupRequest){
		if(userRepository.existsByUsername(signupRequest.getUsername()) || userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponseBody("Error: The user with following username/email already exists!"));
		}
		UserEntity user=new UserEntity();
		user.setEmail(signupRequest.getEmail());
		user.setPassword(encoder.encode(signupRequest.getPassword()));
		user.setUsername(signupRequest.getUsername());
		Set<String> role=signupRequest.getRole();
		Set<RoleEntity> roles=new HashSet<>();
		if(role==null) {
			RoleEntity userRole=roleRepository.findByRole(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Role User is not found!"));
			roles.add(userRole);
		}else {
			role.forEach(r -> {
				switch(r) {
					case "admin":
						RoleEntity adminRole=roleRepository.findByRole(RoleEnum.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role Admin is not found!"));
						roles.add(adminRole);
						break;
					case "mod":
						RoleEntity modRole=roleRepository.findByRole(RoleEnum.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Role Moderator is not found!"));
						roles.add(modRole);
						break;
					case "user":
						RoleEntity userRole=roleRepository.findByRole(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Role User is not found!"));
						roles.add(userRole);
						break;
					default:
						ResponseEntity.unprocessableEntity().body(new MessageResponseBody("Error: The specified role is not found!"));
				}
			});
			user.setRoles(roles);
			userRepository.save(user);
		}
		return ResponseEntity.ok(new MessageResponseBody("User is successfully registered!"));
	}
}
