package com.hatoberry.api.message.port;

import com.hatoberry.api.message.domain.Message;

public interface MessagePublishPort {
    void notifyMessagePosted(Message message);
}
