package com.example.Demo.HouseKeppingApplication.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FloorData {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @Column(nullable = false)
	 private String floorName;
	 
	 @Column(nullable = false)
	 private String subFloorName;
	 
	 @Column(nullable = false)
	 private String imageType;
	 
	 @Lob
	 private String taskImage;
	 
//	 @Column(name = "upload_date")
//	 private LocalDate uploadDate;

}
