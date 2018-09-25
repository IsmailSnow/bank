package com.userfront.domain.security;

import org.springframework.security.core.GrantedAuthority;

public class Auhtority implements GrantedAuthority {

	private static final long serialVersionUID = 1471866643136189521L;

	private final String authority;

	public Auhtority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
