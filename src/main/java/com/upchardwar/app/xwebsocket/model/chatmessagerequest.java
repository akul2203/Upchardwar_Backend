package com.upchardwar.app.xwebsocket.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class chatmessagerequest {

	   private ChatMessage chatMessage;
	   private MultipartFile imageName;
	
	
}
