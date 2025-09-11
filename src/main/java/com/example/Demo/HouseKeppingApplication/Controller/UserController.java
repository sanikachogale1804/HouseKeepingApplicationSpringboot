package com.example.Demo.HouseKeppingApplication.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.Demo.HouseKeppingApplication.Entity.User;
import com.example.Demo.HouseKeppingApplication.Repository.userRepository;
import com.example.Demo.HouseKeppingApplication.Service.UserService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private userRepository userRepository; 

    @CrossOrigin(origins = {
    	    "http://localhost:8080",
    	    "http://127.0.0.1:8080",
    	    "http://192.168.1.92:8080",
    	    "http://45.115.186.228:8080",
    	    "http://localhost:3000"
    	})
    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> getAllUsers() {
        List<User> users = service.getAllUsers();

        List<EntityModel<User>> userModels = users.stream()
            .map(user -> EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        return CollectionModel.of(userModels,
            linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @CrossOrigin(origins = {
    	    "http://localhost:8080",
    	    "http://127.0.0.1:8080",
    	    "http://192.168.1.92:8080",
    	    "http://45.115.186.228:8080",
    	    "http://localhost:3000"
    	})
    @GetMapping("/users/{id}")
    public EntityModel<User> getUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        return EntityModel.of(user,
            linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
            linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

    @CrossOrigin(origins = {
    	    "http://localhost:8080",
    	    "http://127.0.0.1:8080",
    	    "http://192.168.1.92:8080",
    	    "http://45.115.186.228:8080"
    	})
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @CrossOrigin(origins = {
    	    "http://localhost:8080",
    	    "http://127.0.0.1:8080",
    	    "http://192.168.1.92:8080",
    	    "http://45.115.186.228:8080"
    	})
    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String userPassword) {
        User user = new User();
        user.setUsername(username);
        user.setUserPassword(userPassword);
        System.out.println(user);
        return service.verify(user);
    }

//    // ✅ PUT for updating username and role (password skipped)
//    @PutMapping("/users/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        Optional<User> optionalUser = userRepository.findById(id);
//
//        if (optionalUser.isPresent()) {
//            User existingUser = optionalUser.get();
//            existingUser.setUsername(updatedUser.getUsername());
//            existingUser.setRole(updatedUser.getRole());
//
//            // ✅ Optional: skip password update for now
//            // You can uncomment this later when you add encoder
//            /*
//            if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isEmpty()) {
//                existingUser.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
//            }
//            */
//
//            userRepository.save(existingUser);
//            return ResponseEntity.ok(existingUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    
    @CrossOrigin(origins = {
    	    "http://localhost:8080",
    	    "http://127.0.0.1:8080",
    	    "http://192.168.1.92:8080",
    	    "http://45.115.186.228:8080",
    	    "http://localhost:3000"
    	})
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User updated = service.updateUser(id, updatedUser); // ✅ Delegate to service
        return ResponseEntity.ok(updated);
    }
    
    @CrossOrigin(origins = {
    	    "http://localhost:8080",
    	    "http://127.0.0.1:8080",
    	    "http://192.168.1.92:8080",
    	    "http://45.115.186.228:8080",
    	    "http://localhost:3000"
    	})
    @PostMapping("/users/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        String currentPassword = passwords.get("currentPassword");
        String newPassword = passwords.get("newPassword");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(currentPassword, user.getUserPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect current password");
        }

        user.setUserPassword(encoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }

    @CrossOrigin(origins = {
    	    "http://localhost:8080",
    	    "http://127.0.0.1:8080",
    	    "http://192.168.1.92:8080",
    	    "http://45.115.186.228:8080",
    	    "http://localhost:3000"
    	})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build(); 
    }

}
