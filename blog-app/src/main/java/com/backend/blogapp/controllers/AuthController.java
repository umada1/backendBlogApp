package com.backend.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blogapp.classes.Request;
import com.backend.blogapp.classes.Response;
import com.backend.blogapp.classes.Users;
import com.backend.blogapp.jwtHandling.Utilities;
import com.backend.blogapp.repositories.UserRepository;
import com.backend.blogapp.tokenAuth.DetailsOfUsers;


@CrossOrigin
@RestController
@RequestMapping("/api") // adds api in front of all addresses  

public class AuthController {
	
	private final UserRepository repository;

	AuthController(UserRepository repository) {
	    this.repository = repository;
	}
	
	@Autowired 
	private Utilities token;
	
	@Autowired
	private DetailsOfUsers user;
	
	@Autowired 
	private AuthenticationManager authMan;
	
	
	@PostMapping("/auth")
	
	ResponseEntity<?> createAuthenticationToken(@RequestBody Request req) throws Exception{
		
		Users i = repository.findByUsername(req.getUsername());
		if (BCrypt.checkpw(req.getPassword(), i.getPassword())) {
			try {
				authMan.authenticate(
						new UsernamePasswordAuthenticationToken(req.getUsername(), i.getPassword()));
			}catch(BadCredentialsException exception){
				throw new Exception("incorrect password",exception);
			}
			
			UserDetails details = user.loadUserByUsername(req.getUsername());
			
			String tokencred = token.newJwt(details);
			return ResponseEntity.ok(new Response(tokencred));
			
		}
		return (ResponseEntity<?>) ResponseEntity.notFound();
		
	}

}
