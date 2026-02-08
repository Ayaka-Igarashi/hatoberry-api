package com.hatoberry.api.message.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    @Test
    void testValidMessage() {
        Message m = new Message("hello");
        assertEquals("hello", m.getContent());
        assertNotNull(m.getPostedAt());
    }

    @Test
    void testBlankContentThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Message(""));
        assertThrows(IllegalArgumentException.class, () -> new Message(null));
    }
}
