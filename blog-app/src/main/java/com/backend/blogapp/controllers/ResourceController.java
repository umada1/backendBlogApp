package com.backend.blogapp.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blogapp.classes.Request;
import com.backend.blogapp.classes.Resources;
import com.backend.blogapp.classes.Response;
import com.backend.blogapp.classes.Users;
import com.backend.blogapp.jwtHandling.Utilities;
import com.backend.blogapp.repositories.ResourceRepository;
import com.backend.blogapp.tokenAuth.DetailsOfUsers;
import com.backend.blogapp.tokenAuth.SelectedUser;

@CrossOrigin
@RestController
@RequestMapping("/api") // adds api in front of all addresses 

public class ResourceController {
	
	private final ResourceRepository repository;

	ResourceController(ResourceRepository repository) {
	    this.repository = repository;
	}
	
	@PostMapping("/addResource")
	Resources addResource(@RequestBody Resources newResource) {
	    return repository.save(newResource);
	}
	
	@GetMapping("/resources")
	  List<Resources> all() {
	    return repository.findAll();
	}
	
	@DeleteMapping("/resources/{id}")
	  void deleteResources(@PathVariable Long id) {
	    repository.deleteById(id);
	}
	
}
