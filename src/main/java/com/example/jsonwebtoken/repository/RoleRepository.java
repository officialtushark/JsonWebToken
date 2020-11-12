package com.example.jsonwebtoken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jsonwebtoken.models.RoleEnum;
import com.example.jsonwebtoken.models.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	
	Optional<RoleEntity> findByRole(RoleEnum name);
}
