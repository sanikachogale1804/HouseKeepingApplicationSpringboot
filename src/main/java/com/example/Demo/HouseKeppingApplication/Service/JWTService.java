package com.example.Demo.HouseKeppingApplication.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private String secretKey = "";
	
	 public JWTService() {
	        try {
	            // Generate a secret key for HmacSHA256 algorithm
	            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
	            SecretKey sk = keyGen.generateKey();
	            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("Error generating the key", e);
	        }
	    }

	public String generateToken(String username) {
		
		 Map<String, Object> claims = new HashMap<>();
		 
		 return Jwts.builder()
				 .claims()
				 .add(claims)
				 .subject(username)
				 .issuedAt(new Date(System.currentTimeMillis()))
				 .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30))
				 .and()
				 .signWith(getKey())
				 .compact();
	}

	// Generate the SecretKey from the encoded secret key
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

	// Extract username from the token
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    
 // Extract any claim from the token
    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	
 // Validate token with user details
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    
 // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
	

}
