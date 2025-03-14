package com.bankingapp.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankingapp.model.User;
import com.bankingapp.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	// Save a user (for example, during registration)
    public User saveUser(User user) {
    	  if (userRepository.findByUsername(user.getUsername()).isPresent()) {
    	        throw new IllegalArgumentException("Username already exists!");
    	    }
    	    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
    	        throw new IllegalArgumentException("Email already registered!");
    	    }
    	    return userRepository.save(user);
    }

	// Load user by username for authentication
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())  // You should hash passwords in production!
				.roles(user.getRole())
				.build();
	}

	// Find a user by ID
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	// Find a user by username
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}


}

