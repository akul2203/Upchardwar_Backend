package com.upchardwar.app.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.upchardwar.app.entity.status.AppConstant;

@Component
public class FileUtils {

	
	public InputStream getImages(String fileName) {
		
		try {
				return new FileInputStream(AppConstant.DIRECTORY+fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}

