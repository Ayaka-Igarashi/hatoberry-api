package com.hatoberry.api.message.controller;

import com.hatoberry.api.message.domain.Message;

import com.hatoberry.api.message.dto.MessageRequest;
import com.hatoberry.api.message.dto.MessageResponse;
import com.hatoberry.api.message.domain.Message;
import com.hatoberry.api.message.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageControllerTest {
    @Mock
    private MessageService messageService;
    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostMessage() {
        MessageRequest request = new MessageRequest("hello");
        doNothing().when(messageService).postMessage("hello");
        messageController.postMessage(request);
        verify(messageService, times(1)).postMessage("hello");
    }

    @Test
    void testGetMessages() {
        Message m1 = mock(Message.class);
        Message m2 = mock(Message.class);
        when(m1.getId()).thenReturn(1L);
        when(m1.getContent()).thenReturn("a");
        when(m1.getPostedAt()).thenReturn(Instant.now());
        when(m2.getId()).thenReturn(2L);
        when(m2.getContent()).thenReturn("b");
        when(m2.getPostedAt()).thenReturn(Instant.now());
        when(messageService.getMessages()).thenReturn(List.of(m1, m2));

        List<MessageResponse> result = messageController.getMessages();
        assertEquals(2, result.size());
        assertEquals("a", result.get(0).content());
        assertEquals("b", result.get(1).content());
    }
}
