package com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.helper.JwtUtil;
import com.jwt.model.JwtRequest;
import com.jwt.model.JwtResponse;
import com.jwt.service.CustomUserDetailsService;

@RestController
@CrossOrigin
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		 // Check for null or empty username/password
	    if (jwtRequest.getUsername() == null || jwtRequest.getPassword() == null) {
	        return new ResponseEntity<>("Username or password cannot be null", HttpStatus.BAD_REQUEST);
	    }
		System.out.println(jwtRequest);
		try {
			//validate username & password
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw  new Exception("Bad Credentials!!");
		}
		//validation successful
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername()); 
		//generate token
		String token = this.jwtUtil.generateToken(userDetails);
		System.out.println("Token generated: " + token);
		//send token in json
		JwtResponse responseToken = new JwtResponse(token);
		return new ResponseEntity<>(responseToken, HttpStatus.CREATED);
	}
}
