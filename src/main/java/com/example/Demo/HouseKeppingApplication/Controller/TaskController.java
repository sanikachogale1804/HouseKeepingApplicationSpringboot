package com.example.Demo.HouseKeppingApplication.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Demo.HouseKeppingApplication.Entity.FloorData;
import com.example.Demo.HouseKeppingApplication.Entity.User;
import com.example.Demo.HouseKeppingApplication.Repository.floorDataRepository;
import com.example.Demo.HouseKeppingApplication.Repository.userRepository;
import com.example.Demo.HouseKeppingApplication.Service.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class TaskController {
	
	@Value("${tasks.image.path}")
	private String imagePath;
	
	@Autowired
	private floorDataRepository floorDataRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private userRepository userRepo;

	
	
	@PostMapping("floorData/{floorDataId}/image")
	public ResponseEntity<String> uploadTaskImage(
	        @RequestParam("taskImage") MultipartFile image,
 
	        @PathVariable Long floorDataId) throws IOException {

	    return floorDataRepository.findById(floorDataId).map(floorData -> {
	        try {
	            String imageName = fileService.uploadFile(image, imagePath);
	            System.out.println("✅ ImageName: " + imageName);

	          

	            floorData.setTaskImage(imageName);
	          
	            floorDataRepository.save(floorData);

	            return new ResponseEntity<>("✅ Image uploaded successfully", HttpStatus.ACCEPTED);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<>("❌ Image upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }).orElseGet(() -> {
	        return new ResponseEntity<>("❌ FloorData with ID " + floorDataId + " not found", HttpStatus.NOT_FOUND);
	    });
	}


	 @CrossOrigin(origins = {
	    	    "http://localhost:8080",
	    	    "http://127.0.0.1:8080",
	    	    "http://192.168.1.92:8080",
	    	    "http://45.115.186.228:8080",
	    	    "http://localhost:3000"
	    	})
	@GetMapping(value = "floorData/{floorDataId}/image")
	public void serveTaskImage(@PathVariable Long floorDataId, HttpServletResponse response) throws IOException {
	    floorDataRepository.findById(floorDataId).ifPresentOrElse(floorData -> {
	        String imageName = floorData.getTaskImage();
	        if (imageName == null || imageName.isEmpty()) {
	            response.setStatus(HttpStatus.NOT_FOUND.value());
	            return;
	        }
	        try (InputStream resource = fileService.getResource(imagePath, imageName)) {
	            String extension = imageName.substring(imageName.lastIndexOf(".") + 1).toLowerCase();
	            if (extension.equals("png")) {
	                response.setContentType(MediaType.IMAGE_PNG_VALUE);
	            } else {
	                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	            }
	            StreamUtils.copy(resource, response.getOutputStream());
	        } catch (IOException e) {
	            e.printStackTrace();
	            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        }
	    }, () -> {
	        response.setStatus(HttpStatus.NOT_FOUND.value());
	    });
	}


	 @CrossOrigin(origins = {
	    	    "http://localhost:8080",
	    	    "http://127.0.0.1:8080",
	    	    "http://192.168.1.92:8080",
	    	    "http://45.115.186.228:8080",
	    	    "http://localhost:3000"
	    	})
	 @GetMapping("/floorData/images")
	 public ResponseEntity<List<FloorData>> getImages(
	         @RequestParam(required = false) String floorName,
	         @RequestParam(required = false) String subFloorName,
	         @RequestParam(required = false) String imageType) {

	     List<FloorData> results;

	     if (floorName != null && subFloorName != null && imageType != null) {
	         results = floorDataRepository.findByFloorNameAndSubFloorNameAndImageType(floorName, subFloorName, imageType);
	     } else if (floorName != null && subFloorName != null) {
	         results = floorDataRepository.findByFloorNameAndSubFloorName(floorName, subFloorName);
	     } else if (subFloorName != null && imageType != null) {
	         results = floorDataRepository.findBySubFloorNameAndImageType(subFloorName, imageType);
	     } else if (subFloorName != null) {
	         results = floorDataRepository.findBySubFloorName(subFloorName);
	     } else {
	         // 🔥 NEW: return all images if no filter is passed
	         results = floorDataRepository.findAll();
	     }

	     return new ResponseEntity<>(results, HttpStatus.OK);
	 }
	 
	 @CrossOrigin(origins = {
			    "http://localhost:8080",
			    "http://127.0.0.1:8080",
			    "http://192.168.1.92:8080",
			    "http://45.115.186.228:8080",
			    "http://localhost:3000"
			})
			@PutMapping("/floorData/{floorDataId}/approve")
			public ResponseEntity<String> approveImage(@PathVariable Long floorDataId) {
			    return floorDataRepository.findById(floorDataId).map(floorData -> {
			        floorData.setApproved(true);   // mark approved
			        floorDataRepository.save(floorData);
			        return ResponseEntity.ok("✅ Image approved successfully");
			    }).orElseGet(() -> 
			        new ResponseEntity<>("❌ FloorData with ID " + floorDataId + " not found", HttpStatus.NOT_FOUND)
			    );
			}



private void serveImage(HttpServletResponse response, String imageName) {
    try (InputStream resource = fileService.getResource(imagePath, imageName)) {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    } catch (IOException e) {
        e.printStackTrace();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}


}
