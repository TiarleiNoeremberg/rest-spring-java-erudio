package br.com.tiarlei.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.tiarlei.data.dto.v1.security.AccontCredentialDTO;
import br.com.tiarlei.data.dto.v1.security.TokenDTO;
import br.com.tiarlei.repositories.UserRepository;
import br.com.tiarlei.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccontCredentialDTO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
			var user = repository.findByUserName(username);
			var tokenResponse = new TokenDTO();
			if (user != null) {
				tokenResponse = tokenProvider.createdAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username: " + username + " not found!");
			}
			
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
