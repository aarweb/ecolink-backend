package com.ecolink.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecolink.spring.entity.Chat;
import com.ecolink.spring.entity.UserBase;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    
    @Query("SELECT c FROM Chat c WHERE (c.sender = :sender AND c.receiver = :receiver) OR (c.sender = :receiver AND c.receiver = :sender)")
    Chat findBySenderAndReceiverOrReceiverAndSender(@Param("sender") UserBase sender,
            @Param("receiver") UserBase receiver);

}
