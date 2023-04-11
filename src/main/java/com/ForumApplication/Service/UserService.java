package com.ForumApplication.Service;

import java.util.Set;

import com.ForumApplication.Models.User;
import com.ForumApplication.Models.UserRole;



public interface UserService {
 public User createUser(User user, Set<UserRole> userRoles) throws Exception;
	 
	 public User getUser(String username);
	 
	 public void deleteUser(Long userId);
}
