package com.example.Demo.HouseKeppingApplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "areas")
public class Area {
	
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(nullable = false)
	 private Long id;

	 @Column(nullable = false)
	 private String name;
	 
	 @Column(nullable = false)
	 private String location;

}
