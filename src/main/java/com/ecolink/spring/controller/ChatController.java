package com.ecolink.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import com.ecolink.spring.dto.ChatListDTO;
import com.ecolink.spring.dto.ChatMessage;
import com.ecolink.spring.dto.DTOConverter;
import com.ecolink.spring.dto.MessageDTO;
import com.ecolink.spring.entity.Chat;
import com.ecolink.spring.entity.Message;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.exception.ErrorDetails;
import com.ecolink.spring.response.SuccessDetails;
import com.ecolink.spring.service.ChatService;
import com.ecolink.spring.service.UserBaseService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService service;
    private final UserBaseService userBaseService;
    private final SimpMessagingTemplate messagingTemplate;
    private final DTOConverter dtoConverter;

    @MessageMapping("/chat/{chat_id}/message")
    @SendTo("/topic/chat/{chat_id}")
    public ChatMessage sendMessage(@Payload ChatMessage message, @DestinationVariable String chat_id,
            SimpMessageHeaderAccessor headerAccessor) {
        // Obtener id
        Long senderId = Long.parseLong(headerAccessor.getSessionAttributes().get("userId").toString());

        UserBase sender = userBaseService.findById(senderId).orElse(null);
        Chat chat = service.findById(Long.parseLong(chat_id));

        if (sender == null || chat == null) {
            return null;
        }

        if (chat.getSender().getId() != senderId && chat.getReceiver().getId() != senderId) {
            return null;
        }

        message.setTimestamp(String.valueOf(System.currentTimeMillis()));
        

        return message;
    }

    @GetMapping
    public ResponseEntity<?> getChats(@AuthenticationPrincipal UserBase user) {
        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "The user must be logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        List<Chat> chats = service.findAllByUser(user);

        List<ChatListDTO> chatsListDTO = chats.stream().map(chat -> dtoConverter.convertChatToChatListDTO(chat, user))
                .collect(Collectors.toList());

        return ResponseEntity.ok(chatsListDTO);

    }
}
