package io.theam.crmservice.api.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import io.theam.crmservice.api.property.FileStorageProperties;

public interface FileStorageService {
	
	String storeFile(MultipartFile file);
	
	void createStorageDir(FileStorageProperties fileStorageProperties);
	
	Resource loadFileAsResource(String fileName);
}
