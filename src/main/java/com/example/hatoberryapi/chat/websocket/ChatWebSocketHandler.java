package com.example.hatoberryapi.chat.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 接続中の全セッションを管理
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        String payload = message.getPayload();
        System.out.println("受信: " + payload);

        session.sendMessage(new TextMessage("echo: " + payload));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("接続: " + session.getId() + " (接続数: " + sessions.size() + ")");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("切断: " + session.getId() + " (接続数: " + sessions.size() + ")");
    }

    /**
     * 接続中の全クライアントにメッセージをブロードキャスト
     */
    public void broadcast(String message) {
        TextMessage textMessage = new TextMessage(message);
        sessions.forEach(session -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(textMessage);
                } catch (IOException e) {
                    System.err.println("メッセージ送信エラー: " + e.getMessage());
                }
            }
        });
    }
}
