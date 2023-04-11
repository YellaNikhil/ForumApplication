package com.ForumApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ForumApplication.Models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
