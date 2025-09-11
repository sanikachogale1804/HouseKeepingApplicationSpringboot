package com.example.Demo.HouseKeppingApplication.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.Demo.HouseKeppingApplication.Entity.FloorData;

@RepositoryRestResource(path = "floorData")
@CrossOrigin(origins = {
	    "http://localhost:8080",
	    "http://127.0.0.1:8080",
	    "http://192.168.1.92:8080",
	    "http://45.115.186.228:8080",
	    "http://localhost:3000"
	})
public interface floorDataRepository extends JpaRepository<FloorData, Long>{
	
	FloorData findTopByFloorNameAndSubFloorNameAndImageTypeOrderByIdDesc(
		    String floorName, String subFloorName, String imageType);
	List<FloorData> findBySubFloorName(String subFloorName);

	List<FloorData> findBySubFloorNameAndImageType(String subFloorName, String imageType);

	List<FloorData> findByFloorNameAndSubFloorName(String floorName, String subFloorName);

	List<FloorData> findByFloorNameAndSubFloorNameAndImageType(String floorName, String subFloorName, String imageType);

}
