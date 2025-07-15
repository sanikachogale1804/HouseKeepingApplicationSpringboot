package com.example.Demo.HouseKeppingApplication.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.UUID;
import java.nio.file.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
	    String originalFilename = file.getOriginalFilename(); 

	    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	    if (!extension.equalsIgnoreCase(".png") &&
	        !extension.equalsIgnoreCase(".jpg") &&
	        !extension.equalsIgnoreCase(".jpeg")) {
	        throw new RuntimeException("File with extension " + extension + " not allowed!");
	    }

	    String sanitizedFilename = originalFilename.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9._-]", "");

	    String fullPathWithFileName = Paths.get(path, sanitizedFilename).toString();

	    File folder = new File(path);
	    if (!folder.exists()) {
	        folder.mkdirs();
	    }

	    Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

	    return sanitizedFilename; 
	}


	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
	}

}
