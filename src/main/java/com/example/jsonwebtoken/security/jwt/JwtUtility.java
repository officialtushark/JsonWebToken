package com.example.jsonwebtoken.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.jsonwebtoken.security.services.UserDetailsImplementation;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtility {
	
	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public boolean validateToken(String jwt) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
			return true;
		}catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}catch (Exception e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
	
	public String getUsername(String jwt) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
	}

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImplementation user=(UserDetailsImplementation)authentication.getPrincipal();
		return Jwts.builder().setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.claim("authorities",authentication.getAuthorities())
				.setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512,jwtSecret)
				.compact();
	}
}
