package br.com.tiarlei.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvaliedJwtAuthenticationException extends AuthenticationException {
	private static final long serialVersionUID = 1L;
	
	public InvaliedJwtAuthenticationException(String ex) {
		super(ex);
	}
}
