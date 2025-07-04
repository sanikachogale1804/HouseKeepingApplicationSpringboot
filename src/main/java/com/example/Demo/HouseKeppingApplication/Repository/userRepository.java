package com.example.Demo.HouseKeppingApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Demo.HouseKeppingApplication.Entity.User;

public interface userRepository extends JpaRepository<User, Long>{

}
