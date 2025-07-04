package com.example.Demo.HouseKeppingApplication.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tasks")
public class Task {
	
	  @Id 
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

//	    @ManyToOne
//	    private User user;
//
//	    @ManyToOne
//	    private Area area;

	  @Enumerated(EnumType.STRING)
	  private TaskStatus status; 
	   
	  private String supervisorComment;

	  private LocalDateTime createdAt = LocalDateTime.now();
	   
	  public enum TaskStatus {
		    PENDING,
		    APPROVED,
		    REJECTED
		}


}
