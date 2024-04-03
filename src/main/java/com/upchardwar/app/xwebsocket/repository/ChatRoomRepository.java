package com.upchardwar.app.xwebsocket.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.upchardwar.app.xwebsocket.model.ChatRoom;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
