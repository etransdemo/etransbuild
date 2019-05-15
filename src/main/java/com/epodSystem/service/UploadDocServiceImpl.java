package com.epodSystem.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadDocServiceImpl implements UploadDocService{
	
	 @Value("${file.upload-dir}")
	 public String uploadDirectory;
	 
	 private final Logger log = Logger.getLogger(UploadDocServiceImpl.class);
	 
	/**
	 * STORE FILE IN DESTINATION FOLDER
	 * 
	 * @param file
	 * @return String
	 */
	@Override
	public String storeFile(MultipartFile file) {
		 final Path fileStorageLocation;
		 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		 log.info("fileName : "+fileName);
		
		 fileStorageLocation = Paths.get(uploadDirectory).toAbsolutePath().normalize();
		 String docPath = uploadDirectory+"/"+fileName;
		 log.info("directory :: "+uploadDirectory);
		 try {
	            Files.createDirectories(fileStorageLocation);
	            
	            Path targetLocation =fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	        } catch (Exception ex) {
	        	log.info(ex);
	        }
		 
		return docPath;
	}

}
