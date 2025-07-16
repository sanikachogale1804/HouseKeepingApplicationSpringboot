package com.example.Demo.HouseKeppingApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Demo.HouseKeppingApplication.Entity.User;
import com.example.Demo.HouseKeppingApplication.Service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return service.register(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		System.out.println(user);
		return service.verify(user);
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> loginNotAllowed() {
	    return ResponseEntity
	        .status(HttpStatus.METHOD_NOT_ALLOWED)
	        .body("❌ GET not allowed on /login. Use POST instead.");
	}
}
