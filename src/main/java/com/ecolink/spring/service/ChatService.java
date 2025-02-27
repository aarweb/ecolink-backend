package com.ecolink.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecolink.spring.entity.Chat;
import com.ecolink.spring.entity.Message;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.repository.ChatRepository;
import com.ecolink.spring.repository.MessageRepository;

@Service
public class ChatService {
    @Autowired
    private ChatRepository repository;

    @Autowired
    private MessageRepository messageRepository;

    public Chat findChatBySenderAndReceiver(UserBase user, UserBase receiver) {
        return repository.findBySenderAndReceiverOrReceiverAndSender(user, receiver);
    }

    public void save(Chat chat) {
        repository.save(chat);
    }

    public void createComment(Message message) {
        messageRepository.save(message);
    }

}
