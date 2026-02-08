package com.hatoberry.api.message.service;

import com.hatoberry.api.message.domain.Message;

import com.hatoberry.api.message.port.MessagePublishPort;
import com.hatoberry.api.message.repository.MessageRepository;
import com.hatoberry.api.message.domain.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessagePublishPort messagePublishPort;
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostMessage() {
        Message message = new Message("test");
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        messageService.postMessage("test");
        verify(messageRepository).save(any(Message.class));
        verify(messagePublishPort).notifyMessagePosted(message);
    }

    @Test
    void testGetMessages() {
        Message m1 = new Message("a");
        Message m2 = new Message("b");
        when(messageRepository.findAll()).thenReturn(List.of(m1, m2));

        List<Message> result = messageService.getMessages();
        assertEquals(2, result.size());
        assertEquals("a", result.get(0).getContent());
        assertEquals("b", result.get(1).getContent());
    }
}
