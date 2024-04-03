package com.upchardwar.app.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
	public String uploadFileInFolder(MultipartFile myFile, String destinationPath);
}
