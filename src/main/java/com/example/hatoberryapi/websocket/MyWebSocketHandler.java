package com.example.hatoberryapi.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        String payload = message.getPayload();
        System.out.println("受信: " + payload);

        session.sendMessage(new TextMessage("echo: " + payload));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("接続: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("切断: " + session.getId());
    }
}
