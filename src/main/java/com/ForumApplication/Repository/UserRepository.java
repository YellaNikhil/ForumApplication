package com.ForumApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ForumApplication.Models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String usename);
	
	public void deleteById(long id);
}
