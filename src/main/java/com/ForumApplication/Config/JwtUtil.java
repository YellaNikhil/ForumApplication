package com.ForumApplication.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ForumApplication.Repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/* Contains methods for generating tokens , validating tokens and checking whether token is expired or not */
@Service
public class JwtUtil {
	 private String SECRET_KEY = "Forumapplication";
	 @Autowired
	 private UserRepository urepo;
	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }
	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser().
	        		setSigningKey(SECRET_KEY).
	        		parseClaimsJws(token).
	        		getBody();
	    }
	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }
	    public String generateToken(UserDetails userDetails) {
	        Map<String,Object> claims = new HashMap<>();
	       System.out.println(urepo.findByUsername(userDetails.getUsername()).getAuthorities().toString());
	        claims.put("roles",urepo.findByUsername(userDetails.getUsername()).getAuthorities());//added to set roles/claims in jwt token generation
	        return createToken(claims, userDetails.getUsername());
	    }
	    private String createToken(Map<String,Object> claims, String subject) {
	        return Jwts.builder().
	        		setClaims(claims).    /* Added to set roles/claims in jwt token generation */
	        		setSubject(subject).
	        		setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	    }

	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
}
