package com.example.Demo.HouseKeppingApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Demo.HouseKeppingApplication.Entity.Task;

public interface taskRepository extends JpaRepository<Task, Long>{

}
