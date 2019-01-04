package com.lbw.platform.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameIsExitedException extends AuthenticationException {
	public UsernameIsExitedException(String msg) {
		super(msg);
	}

	public UsernameIsExitedException(String msg, Throwable t) {
		super(msg, t);
	}
}
