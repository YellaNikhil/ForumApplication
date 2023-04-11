package com.ForumApplication.ServiceImpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ForumApplication.Models.User;
import com.ForumApplication.Models.UserRole;
import com.ForumApplication.Repository.RoleRepository;
import com.ForumApplication.Repository.UserRepository;
import com.ForumApplication.Service.UserService;


@Service
public class UserServiceImpl implements UserService {
	 
	@Autowired
	private UserRepository userRepository;

	 @Autowired
	 private RoleRepository roleRepository;

	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		 	User local = this.userRepository.findByUsername(user.getUsername());
	        if (local != null) {
	            System.out.println("User is already there !!");
	            throw new Exception("User already present!");
	        } else {
	            //user create
	            for (UserRole ur : userRoles) {
	                roleRepository.save(ur.getRole());
	            }

	            user.getUserRoles().addAll(userRoles);
	            local = this.userRepository.save(user);

	        }

	        return local;
	}

	@Override
	public User getUser(String username) {
	
		return this.userRepository.findByUsername(username);
	}

	@Override
	public void deleteUser(Long userId) {
//		Long l= new Long(userId);
//		int i=l.intValue();
		this.userRepository.deleteById(userId);
		
	}

}
