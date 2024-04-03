package com.upchardwar.app.xwebsocket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Document
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;
}