package com.hatoberry.api.message.dto;

import java.time.Instant;

public record MessageResponse(Long id, String content, Instant postedAt) {}
