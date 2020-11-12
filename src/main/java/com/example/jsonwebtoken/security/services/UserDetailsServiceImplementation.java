package com.example.jsonwebtoken.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jsonwebtoken.models.UserEntity;
import com.example.jsonwebtoken.repository.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{

	
	@Autowired UserRepository userRepository;
	
	@Override
	public UserDetailsImplementation loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user=userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
		return UserDetailsImplementation.build(user);
	}

}
