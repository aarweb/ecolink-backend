package com.ecolink.spring.service;

import java.util.List;

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

    public void saveMessage(Message message){
        messageRepository.save(message);
    }

    public void createComment(Message message) {
        messageRepository.save(message);
    }

    public List<Chat> findAllByUser(UserBase user) {
        return repository.findAllBySenderOrReceiver(user, user);
    }

    public Chat findById(Long chatId) {
        return repository.findById(chatId).orElse(null);
    }

    public List<Message> findMessagesByChat(Chat chat) {
        return messageRepository.findAllByChat(chat);
    }

    public Message findMessageById(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public List<Message> findTop10UnreadMessages(Chat chat) {
        return messageRepository.findTop10ByChatAndIsReadFalseOrderByTimestampDesc(chat);
    }
}
