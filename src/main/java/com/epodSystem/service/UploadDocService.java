package com.epodSystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadDocService {

	/**
	 * STORE FILE IN DESTINATION FOLDER
	 * 
	 * @param file
	 * @return String
	 */
	String storeFile(MultipartFile file);

}
