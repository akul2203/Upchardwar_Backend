package com.upchardwar.app.xwebsocket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upchardwar.app.xwebsocket.exception.ResourceNotFoundException;
import com.upchardwar.app.xwebsocket.model.ChatMessage;
import com.upchardwar.app.xwebsocket.model.MessageStatus;
import com.upchardwar.app.xwebsocket.repository.ChatMessageRepository;

@Service
public class ChatMessageService {
	@Autowired
	private ChatMessageRepository repository;

	@Autowired
	private ChatRoomService chatRoomService;

	public ChatMessage save(ChatMessage chatMessage) {
		chatMessage.setStatus(MessageStatus.RECEIVED);
		repository.save(chatMessage);
		return chatMessage;
	}

	public long countNewMessages(String senderId, String recipientId) {
		return repository.countBySenderIdAndRecipientIdAndStatus(senderId, recipientId, MessageStatus.RECEIVED);
	}

	String chatid2 ="";
	public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
		Optional<String> chatIdOptional = chatRoomService.getChatId(senderId, recipientId, false);

		
		String chatId = chatIdOptional.orElseThrow(() -> new RuntimeException("ChatId not found"));
 
		System.out.println(chatId+"-----------------------");
		
		List<ChatMessage> messages = repository.findBySenderIdAndRecipientId(senderId, recipientId);


		if (!messages.isEmpty()) {
			updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
		}

//		System.err.println();
		return messages;
	}

	public ChatMessage findById(String id) {
		return repository.findById(id).map(chatMessage -> {
			chatMessage.setStatus(MessageStatus.DELIVERED);
			return repository.save(chatMessage);
		}).orElseThrow(() -> new ResourceNotFoundException("Can't find message (" + id + ")"));
	}

	public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
		List<ChatMessage> messagesToUpdate = repository.findBySenderIdAndRecipientId(senderId, recipientId);

		messagesToUpdate.forEach(message -> {
			message.setStatus(status);
			repository.save(message);
		});
	}
}
