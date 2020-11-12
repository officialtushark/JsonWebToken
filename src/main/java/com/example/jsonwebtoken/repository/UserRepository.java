package com.example.jsonwebtoken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jsonwebtoken.models.UserEntity;
import com.example.jsonwebtoken.security.services.UserDetailsImplementation;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	Optional<UserEntity> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String password);

}
