package com.hatoberry.api.message.repository;

import com.hatoberry.api.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
