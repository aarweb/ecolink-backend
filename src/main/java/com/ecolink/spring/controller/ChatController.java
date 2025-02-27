package com.ecolink.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.spring.entity.Chat;
import com.ecolink.spring.entity.Message;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.exception.ErrorDetails;
import com.ecolink.spring.response.SuccessDetails;
import com.ecolink.spring.service.ChatService;
import com.ecolink.spring.service.UserBaseService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService service;
    private final UserBaseService userBaseService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/{id}/message")
    public ResponseEntity<?> sendMessage(@AuthenticationPrincipal UserBase user, @PathVariable Long id,
            @RequestParam String content) {

        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "The user must be logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        UserBase receiver = userBaseService.findById(id).orElse(null);

        if (receiver == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                    "The receiver doesn't not exists");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }

        Chat chat = service.findChatBySenderAndReceiver(user, receiver);
        if (chat == null) {
            chat = new Chat(user, receiver);
            service.save(chat);
        }

        if (content == null || content.length() <= 0) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "The content is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }

        Message message = new Message(chat, user, content);
        service.createComment(message);

        messagingTemplate.convertAndSend("/topic/chat/" + id, message);
        
        
        SuccessDetails successDetails = new SuccessDetails(HttpStatus.OK.value(), "Message sent successfully");

        return ResponseEntity.ok(successDetails);
    }
}
