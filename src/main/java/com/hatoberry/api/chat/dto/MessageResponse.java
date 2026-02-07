package com.hatoberry.api.chat.dto;

import java.time.Instant;

public record MessageResponse(Long id, String content, Instant postedAt) {}
