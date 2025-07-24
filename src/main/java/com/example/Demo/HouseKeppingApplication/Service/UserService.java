package com.example.Demo.HouseKeppingApplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.example.Demo.HouseKeppingApplication.Entity.User;
import com.example.Demo.HouseKeppingApplication.Repository.userRepository;

@Service
public class UserService {
	
	@Autowired
	private userRepository repo;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private userRepository userRepository;

	
	public List<User> getAllUsers() {
	    return userRepository.findAll();
	}

	public User getUserById(Long id) {
	    return userRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("User not found"));
	}

	
	@Autowired
	AuthenticationManager authManager;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public User register(User user)
	{
		user.setUserPassword(encoder.encode(user.getUserPassword()));
		return repo.save(user);
	}

	public String verify(User user) {
		Authentication authentication=
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getUserPassword()));
		
		if(authentication.isAuthenticated()) 
			return jwtService.generateToken(user.getUsername());
			
		return "Fail";
			
	}
	
	public User updateUser(Long id, User updatedUser) {
	    User existingUser = userRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    existingUser.setUsername(updatedUser.getUsername());
	    existingUser.setRole(updatedUser.getRole());

	    if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isEmpty()) {
	        existingUser.setUserPassword(encoder.encode(updatedUser.getUserPassword())); // ✅ Encode here
	    }

	    return userRepository.save(existingUser);
	}



}
