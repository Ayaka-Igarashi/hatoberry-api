package com.hatoberry.api.chat.event;

import com.hatoberry.api.chat.domain.message.Message;

public record MessagePostedEvent(Message message) {}
