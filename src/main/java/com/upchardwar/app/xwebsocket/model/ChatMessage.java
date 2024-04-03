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
public class ChatMessage {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) 
   private Long id;
   private String chatId;
   private String senderId;
   private String recipientId;
   private String senderName;
   private String recipientName;
   private String content; 
   private String Photos;
   private String timestamp;
   private MessageStatus status;
}