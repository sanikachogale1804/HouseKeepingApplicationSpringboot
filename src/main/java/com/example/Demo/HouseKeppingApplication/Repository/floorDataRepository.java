package com.example.Demo.HouseKeppingApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.Demo.HouseKeppingApplication.Entity.FloorData;

@RepositoryRestResource(path = "floorData")
public interface floorDataRepository extends JpaRepository<FloorData, Long>{
	
	

}
