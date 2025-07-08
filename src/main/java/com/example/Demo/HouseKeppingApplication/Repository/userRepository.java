package com.example.Demo.HouseKeppingApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Demo.HouseKeppingApplication.Entity.User;

@Repository
public interface userRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
