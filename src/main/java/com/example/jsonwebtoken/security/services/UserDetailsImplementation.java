package com.example.jsonwebtoken.security.services;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.jsonwebtoken.models.UserEntity;

public class UserDetailsImplementation implements UserDetails{
	
	private static final long serialVersionUID = 6376010801834901344L;
	
	private Long id;
	private String username;
	private String password;
	private String email;
	private Boolean isAccountNonExpired;
	private Boolean isAccountNonLocked;
	private Boolean isCredentialsNonExpired;
	private Boolean isEnabled;
	private Set<SimpleGrantedAuthority> authorities;

	
	
	public UserDetailsImplementation(String username, String password, Boolean isAccountNonExpired,Boolean isAccountNonLocked, Boolean isCredentialsNonExpired, Boolean isEnabled,
			Set<SimpleGrantedAuthority> authorities,String email,Long id) {
		this.username = username;
		this.password = password;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
		this.authorities = authorities;
		this.email=email;
		this.id=id;
	}

	public static UserDetailsImplementation build(UserEntity user) {
		Set<SimpleGrantedAuthority> authority=user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toSet());
		return new UserDetailsImplementation(user.getUsername(), user.getPassword(), true, true, true, true, authority,user.getEmail(),user.getId());
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
