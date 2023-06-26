package com.sheryians.major.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheryians.major.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
   Optional<User> findByEmail(String email);
   
}
