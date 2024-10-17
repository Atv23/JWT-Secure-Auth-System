package com.jwt.helper;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//methods for generating token
//methods for validating token
//methods for checking if token is expired
//util class for JWT
@Component
public class JwtUtil {
	// requirement :
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	
	private Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Automatically generates a secure key

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser() // Get the JwtParserBuilder
				.setSigningKey(signingKey) // Set the signing key (use a Key object)
				.build() // Build the JwtParser
				.parseClaimsJws(token) // Now parse the token
				.getBody(); // Extract the claims from the token
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDetails) {
	    Map<String, Object> claims = new HashMap<>();
	    String token = doGenerateToken(claims, userDetails.getUsername());
	    System.out.println("Generated Token: " + token); // Print the token for debugging
	    return token;
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(signingKey).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
	    if (token == null || token.isEmpty()) {
	        return false; // Return false if the token is invalid
	    }
	    final String username = getUsernameFromToken(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
