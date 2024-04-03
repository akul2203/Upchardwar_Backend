package com.upchardwar.app.xwebsocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.upchardwar.app.xwebsocket.model.ChatMessage;
import com.upchardwar.app.xwebsocket.model.MessageStatus;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

	long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);

	@Query("SELECT c FROM ChatMessage c WHERE c.chatId = :chatId ORDER BY c.timestamp DESC")
	List<ChatMessage> findByChatId(String chatId);

	@Query("SELECT c FROM ChatMessage c WHERE( c.senderId =:senderId AND c.recipientId =:recipientId  )  OR ( c.recipientId =: senderId AND c.senderId =:recipientId    )ORDER BY c.id DESC")
	List<ChatMessage> findBySenderIdAndRecipientId(String senderId, String recipientId);

}