package com.ForumApplication.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ForumApplication.Config.JwtUtil;
import com.ForumApplication.JWTModels.JwtRequest;
import com.ForumApplication.JWTModels.JwtResponse;
import com.ForumApplication.Models.User;
import com.ForumApplication.ServiceImpl.UserDetailsServiceImpl;
import com.ForumApplication.helper.UserNotFoundException;


@RestController
@CrossOrigin("*")
public class AuthenticateController {

	private final AuthenticationManager authenticationManger;
	private final UserDetailsServiceImpl userDetailsService;
	private final JwtUtil jwtUtils;

	public AuthenticateController(AuthenticationManager authenticationManger, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtils){
		this.authenticationManger = authenticationManger;
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
	}
	
	//generate token
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		
		try {
			
			authenticates(jwtRequest.getUsername(), jwtRequest.getPassword());
				
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}
		
		//authinticate
	UserDetails	userDetails=this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token=this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
	
	
	private void authenticates(String username,String password) throws Exception {
		
		try {
			authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User disable"+e.getMessage());
		}catch(BadCredentialsException e) {
			throw new Exception("invalid credential"+e.getMessage());
		}
		
	}
	
	//return the current details of user
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		
		return ((User)this.userDetailsService.loadUserByUsername(principal.getName()));
		
	} 
	
	
}
