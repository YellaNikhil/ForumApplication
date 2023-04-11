package com.ForumApplication.JWTModels;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	
	private String authority;
	
	
	//constructor
	public Authority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	
	
	
}
