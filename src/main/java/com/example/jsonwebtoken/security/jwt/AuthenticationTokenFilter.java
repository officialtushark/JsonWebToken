package com.example.jsonwebtoken.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jsonwebtoken.security.services.UserDetailsImplementation;
import com.example.jsonwebtoken.security.services.UserDetailsServiceImplementation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter{
	
	@Autowired private JwtUtility jwtUtils;
	@Autowired private UserDetailsServiceImplementation userDetailsServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt=parseJwt(request);
			if(jwt!=null && jwtUtils.validateToken(jwt)) {
				String username=jwtUtils.getUsername(jwt);
				UserDetailsImplementation user=userDetailsServiceImpl.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(username, null,user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch(Exception e) {
			log.error("Could not perform authentication!");
		}
		filterChain.doFilter(request, response);
	}

	private static String parseJwt(HttpServletRequest request) {
		String token=request.getHeader("Authorization");
		if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.replaceAll("Bearer ", "");
		}
		return null;
	}
}
