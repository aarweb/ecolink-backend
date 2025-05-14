package com.ecolink.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecolink.spring.entity.Chat;
import com.ecolink.spring.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChat(Chat chat);

    List<Message> findTop10ByChatAndIsReadFalseOrderByTimestampDesc(Chat chat);
}