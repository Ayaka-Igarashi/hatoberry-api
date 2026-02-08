package com.hatoberry.api.message.adapter.websocket;


import com.hatoberry.api.message.domain.Message;
import com.hatoberry.api.message.dto.MessageResponse;
import com.hatoberry.api.common.websocket.WebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MessageWebSocketAdapterTest {
    private WebSocketHandler webSocketHandler;
    private ObjectMapper objectMapper;
    private MessageWebSocketAdapter adapter;

    @BeforeEach
    void setUp() {
        webSocketHandler = mock(WebSocketHandler.class);
        objectMapper = mock(ObjectMapper.class);
        adapter = new MessageWebSocketAdapter(webSocketHandler, objectMapper);
    }

    @Test
    void testNotifyMessagePostedSuccess() throws Exception {
        Message message = mock(Message.class);
        when(message.getId()).thenReturn(1L);
        when(message.getContent()).thenReturn("test");
        when(message.getPostedAt()).thenReturn(java.time.Instant.now());
        String json = "{\"id\":1,\"content\":\"test\",\"postedAt\":\"2026-02-07T00:00:00Z\"}";
        when(objectMapper.writeValueAsString(any(MessageResponse.class))).thenReturn(json);

        adapter.notifyMessagePosted(message);
        verify(webSocketHandler, times(1)).broadcast(json);
    }

    @Test
    void testNotifyMessagePostedSerializationError() throws Exception {
        Message message = mock(Message.class);
        when(objectMapper.writeValueAsString(any(MessageResponse.class))).thenThrow(new RuntimeException("fail"));
        // Should not throw
        adapter.notifyMessagePosted(message);
        verify(webSocketHandler, never()).broadcast(anyString());
    }
}
