package com.example.Demo.HouseKeppingApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.Demo.HouseKeppingApplication.Entity.User;

@Repository
@RepositoryRestResource(path = "floorData")
@CrossOrigin(origins = {
	    "http://localhost:8080",
	    "http://127.0.0.1:8080",
	    "http://192.168.1.92:8080",
	    "http://45.115.186.228:8080",
	    "http://localhost:3000"
	})
public interface userRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
