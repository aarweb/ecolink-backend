package com.ecolink.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserBase user;

    private String content;

    private LocalDateTime timestamp;

    @Column(name = "is_read")
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    public Message(Chat chat, UserBase user, String content, MessageType type) {
        this.chat = chat;
        this.user = user;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
        this.type = type;
    }
}
