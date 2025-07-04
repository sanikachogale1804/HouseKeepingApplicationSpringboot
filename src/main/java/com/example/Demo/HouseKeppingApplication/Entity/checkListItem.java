package com.example.Demo.HouseKeppingApplication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class checkListItem {
	
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

//	 @ManyToOne
//	 private Task task;

	 private String itemName;
	 private String status; // "PASS", "FAIL"
	 private String remarks;

}
