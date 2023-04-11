package com.ForumApplication.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ForumApplication.Models.User;
import com.ForumApplication.Repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=this.userRepository.findByUsername(username);
		
		if(user==null) {
			System.out.println("user not found");
		throw new  UsernameNotFoundException("no user found this credentials");
		}
		
		
		//List<SimpleGrantedAuthority> roles=null;
		return user;
	}

}
