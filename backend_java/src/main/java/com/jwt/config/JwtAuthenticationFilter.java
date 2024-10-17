package com.jwt.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.helper.JwtUtil;
import com.jwt.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// get Jwt header
		String reqTokenHeader = request.getHeader("Authorization");

		String username = null, jwtToken = null;

		// checking token is well formatted or not
		if (reqTokenHeader != null && reqTokenHeader.startsWith("Bearer ")) {
			jwtToken = reqTokenHeader.substring(7);
			try {

				username = this.jwtUtil.getUsernameFromToken(jwtToken);

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (username != null) {
			    UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			    
			    // validate
			    if (SecurityContextHolder.getContext().getAuthentication() == null) {
			        // set the authentication
			        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
			            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			    } else {
			        System.out.println("Token Validation failed");
			    }
			} else {
			    System.out.println("JWT token is invalid or expired");
			}
		}
		filterChain.doFilter(request, response);
	}

}
