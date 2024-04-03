package com.upchardwar.app.xwebsocket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatNotification {
    @Id 
	private Long id;
    private String senderId;
    private String senderName;
    private String content;
    private String Photos;
    
}