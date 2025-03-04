package com.ecolink.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Chat chat;


    @ManyToOne
    private UserBase user;

    private String content;

    private LocalDateTime timestamp;


    public Message(Chat chat, UserBase user, String content) {
        this.chat = chat;
        this.user = user;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

}
