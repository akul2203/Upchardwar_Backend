package com.upchardwar.app.services.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.upchardwar.app.services.IFileService;

@Service
public class FileServiceImpl implements IFileService {

	@Autowired
	private Cloudinary cloudinary;
	
	@Override
	public String uploadFileInFolder(MultipartFile myFile, String destinationPath) {
		String randomName= (UUID.randomUUID().toString() + myFile.getOriginalFilename());
		String fileName = StringUtils.cleanPath(randomName);
		Map<?, ?> uploadResponse;
		try {
			uploadResponse = cloudinary.uploader().upload(myFile.getBytes(),
					  ObjectUtils.asMap("public_id", destinationPath +"/"+fileName)); 
			return (String)uploadResponse.get("secure_url");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteFileFromCloudinary(String publicId) {
	    try {
	
	        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
	    } catch (IOException e) {
	        e.printStackTrace();
	       
	    }
	}

}
