package com.sheryians.major.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sheryians.major.entities.CustomUserDetails;
import com.sheryians.major.entities.User;
import com.sheryians.major.repositories.UserRepository;
@Service
public class CustomUserDetailService implements UserDetailsService{
	
    @Autowired
    UserRepository userRepository;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
         Optional<User>user = userRepository.findByEmail(username);
         if(user.isEmpty())
         return (UserDetails) new UsernameNotFoundException("User not found");
         System.out.println("------loadusername-----");
         return user.map(CustomUserDetails::new).get();
	}

	
}
