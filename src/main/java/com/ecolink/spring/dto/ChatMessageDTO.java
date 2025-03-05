package com.ecolink.spring.dto;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    private String content;
    private String sender;
    private LocalDateTime timestamp;
}
