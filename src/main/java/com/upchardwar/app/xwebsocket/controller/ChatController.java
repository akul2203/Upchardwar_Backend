package com.upchardwar.app.xwebsocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.services.IFileService;
import com.upchardwar.app.xwebsocket.model.ChatMessage;
import com.upchardwar.app.xwebsocket.model.ChatNotification;
import com.upchardwar.app.xwebsocket.service.ChatMessageService;
import com.upchardwar.app.xwebsocket.service.ChatRoomService;

@RestController
@CrossOrigin("*")
@RequestMapping("/upchardwar/chat")
public class ChatController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private ChatMessageService chatMessageService;
	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private IFileService fileService;

	@MessageMapping("/chat")
	public void processMessage(@Payload ChatMessage chatMessage) {
		var chatId = chatRoomService.getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
		chatMessage.setChatId(chatId.get());
		System.out.println(chatMessage);
		ChatMessage saved = chatMessageService.save(chatMessage);
		messagingTemplate.convertAndSend(
				"/user/" + chatMessage.getRecipientId() + "_" + chatMessage.getSenderId() + "/queue/messages",
				new ChatNotification(saved.getId(), saved.getSenderId(), saved.getSenderName(), saved.getContent(), saved.getPhotos()));
	}

	@PostMapping("/api/chat/upload-photo")
	public ResponseEntity<String> uploadPhoto(@RequestParam("chatMessage") String chatMessage,@RequestParam("photo")MultipartFile photo) {
        	  ObjectMapper  mapper = new ObjectMapper();
        	  
		try {
		
			 ChatMessage chatMessag = mapper.readValue(chatMessage, ChatMessage.class);
				var chatId = chatRoomService.getChatId(chatMessag.getSenderId(), chatMessag.getRecipientId(), true);
				chatMessag.setChatId(chatId.get());
			 System.err.println(chatMessag);
			 System.err.println(photo.getOriginalFilename());
			 String documentImageName= fileService.uploadFileInFolder(photo, AppConstant.LAB_DOC_DIR);
				System.err.println(documentImageName);
			chatMessag.setPhotos(documentImageName);
			chatMessageService.save(chatMessag);
			return	ResponseEntity.status(200).body(documentImageName);
//			return ResponseEntity.ok("Photo uploaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error uploading photo: " + e.getMessage());
		}
	}

	@GetMapping("/messages/{senderId}/{recipientId}/count")
	public ResponseEntity<Long> countNewMessages(@PathVariable String senderId, @PathVariable String recipientId) {

		return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId));
	}

	@GetMapping("/messages/{senderId}/{recipientId}")
	public ResponseEntity<?> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId) {
		return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
	}

	@GetMapping("/messages/{id}")
	public ResponseEntity<?> findMessage(@PathVariable String id) {
		return ResponseEntity.ok(chatMessageService.findById(id));
	}
}