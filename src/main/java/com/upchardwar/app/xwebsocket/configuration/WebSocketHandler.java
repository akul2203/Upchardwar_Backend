package com.upchardwar.app.xwebsocket.configuration;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // Set to store active WebSocket sessions
    private final Set<WebSocketSession> activeSessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // New connection established
        activeSessions.add(session);
        sendConnectedClientsCount();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages if needed
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Connection closed
        activeSessions.remove(session);
        sendConnectedClientsCount();
    }

    private void sendConnectedClientsCount() throws Exception {
        // Broadcast the current number of connected clients to all sessions
        for (WebSocketSession session : activeSessions) {
            session.sendMessage(new TextMessage("Current number of connections: " + activeSessions.size()));
        }
    }
}