package com.upchardwar.app.xwebsocket.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upchardwar.app.xwebsocket.model.ChatRoom;
import com.upchardwar.app.xwebsocket.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

    @Autowired private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(
            String senderId, String recipientId, boolean createIfNotExist) {

         return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                 .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                     var chatId =
                            String.format("%s_%s", senderId, recipientId);

                     var chatId2 =
                             String.format("%s_%s", recipientId, senderId);

                     var chatId3 =
                             String.format("%s_%s", senderId, recipientId);

                   
                    ChatRoom senderRecipient = ChatRoom
                            .builder()
                            .chatId(chatId3)
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    ChatRoom recipientSender = ChatRoom
                            .builder()
                            .chatId(chatId2)
                            .senderId(recipientId)
                            .recipientId(senderId)
                            .build();
                    
                    System.out.println(senderRecipient);
                    System.out.println(recipientSender);
                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}