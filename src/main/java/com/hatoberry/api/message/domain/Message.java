package com.hatoberry.api.message.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Instant postedAt;

    protected Message() {}

    public Message(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("message is empty");
        }
        this.content = content;
        this.postedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Instant getPostedAt() {
        return postedAt;
    }
}
