package com.ForumApplication.Controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ForumApplication.Models.Role;
import com.ForumApplication.Models.User;
import com.ForumApplication.Models.UserRole;
import com.ForumApplication.Service.UserService;
import com.ForumApplication.helper.UserNotFoundException;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    private final BCryptPasswordEncoder bcryptpasswordEncorder;

    public UserController(UserService userService, BCryptPasswordEncoder bcryptpasswordEncorder){
        this.userService = userService;
        this.bcryptpasswordEncorder = bcryptpasswordEncorder;
    }
    
  //creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {


    	//user.setProfile("default.png");
        
      //encoding password with bcryptpassword
        user.setPassword(this.bcryptpasswordEncorder.encode(user.getPassword()));
    	
    	
        Set<UserRole> roles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("SME");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);


        return this.userService.createUser(user, roles);

    }
    
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
    	
    }
    
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
    	this.userService.deleteUser(userId);
    }
    
    
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserNotFoundException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }
}
