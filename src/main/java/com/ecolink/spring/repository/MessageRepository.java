package com.ecolink.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecolink.spring.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}