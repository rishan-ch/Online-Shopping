package com.bway.BroadwayProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.BroadwayProject.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String email);
	User findByEmailAndPassword(String email, String Password);

}
