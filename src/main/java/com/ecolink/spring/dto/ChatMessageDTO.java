package com.ecolink.spring.dto;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    private Long id;
    private String content;
    private String sender;
    private LocalDateTime timestamp;
    private boolean read;
    private String type;
}
