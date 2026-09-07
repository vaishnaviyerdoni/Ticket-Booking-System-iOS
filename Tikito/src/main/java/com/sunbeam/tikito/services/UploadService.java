
package com.sunbeam.tikito.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.tikito.dto.ImageUploadResponse;

public interface UploadService {

	   ImageUploadResponse uploadPoster(MultipartFile file) throws IOException;

	    ImageUploadResponse uploadProfile(MultipartFile file) throws IOException;
	}
