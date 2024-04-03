package com.upchardwar.app.controller;

import java.io.IOException;
import java.io.InputStream;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.utils.FileUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class GetImageController {

	@Autowired
	private FileUtils fileUtils;
	
	@RequestMapping(value = "/getImageApi/{imgName}",method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	public void getImage(@PathVariable("imgName") String imgName,HttpServletResponse response) throws IOException {
		System.out.println(imgName);
		InputStream data = fileUtils.getImages(imgName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(data, response.getOutputStream());

	}
	
	
}
