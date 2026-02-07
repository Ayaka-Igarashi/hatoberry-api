package com.hatoberry.api.chat.event;

import com.hatoberry.api.chat.dto.MessageResponse;

public record MessagePostedEvent(MessageResponse response) {}
