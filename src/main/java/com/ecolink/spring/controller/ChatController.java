package com.ecolink.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecolink.spring.dto.ChatListDTO;
import com.ecolink.spring.dto.ChatMessageDTO;
import com.ecolink.spring.dto.DTOConverter;
import com.ecolink.spring.dto.GetUserFrontDTO;
import com.ecolink.spring.dto.MessageDTO;
import com.ecolink.spring.entity.Chat;
import com.ecolink.spring.entity.Message;
import com.ecolink.spring.entity.MessageType;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.entity.UserType;
import com.ecolink.spring.exception.ErrorDetails;
import com.ecolink.spring.exception.ImageNotValidExtension;
import com.ecolink.spring.exception.ImageSubmitError;
import com.ecolink.spring.response.SuccessDetails;
import com.ecolink.spring.service.ChatService;
import com.ecolink.spring.service.UserBaseService;
import com.ecolink.spring.utils.Images;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService service;
    private final UserBaseService userBaseService;
    private final DTOConverter dtoConverter;
    private final Images images;

    @Value("${spring.message.upload.dir}")
    private String uploadMessageDir;

    @MessageMapping("/chat/{chat_id}/message")
    @SendTo("/topic/chat/{chat_id}")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO message, @DestinationVariable String chat_id,
            SimpMessageHeaderAccessor headerAccessor) {

        Long senderId = Long.parseLong(headerAccessor.getSessionAttributes().get("userId").toString());

        UserBase sender = userBaseService.findById(senderId).orElse(null);
        Chat chat = service.findById(Long.parseLong(chat_id));

        if (sender == null || chat == null) {
            return null;
        }

        if (chat.getSender().getId() != senderId && chat.getReceiver().getId() != senderId) {
            return null;
        }

        if (message.getContent() == null || message.getContent().isEmpty() || message.getContent().length() > 255) {
            return null;
        }

        message.setTimestamp(LocalDateTime.now());

        if (message.getType() == null || (!message.getType().equals("TEXT") && !message.getType().equals("IMAGE"))) {
            return null;
        }

        MessageType type = message.getType().equals("TEXT") ? MessageType.TEXT : MessageType.IMAGE;

        System.out.println("Type: " + type);

        Message newMessage = new Message(chat, sender, message.getContent(), type);
        
        service.saveMessage(newMessage);

        ChatMessageDTO newMessageDTO = dtoConverter.convertMessageToChatMessageDTO(newMessage);

        return newMessageDTO;
    }

    @MessageMapping("/chat/{chat_id}/read")
    @SendTo("/topic/chat/{chat_id}")
    public ChatMessageDTO readMessage(@Payload ChatMessageDTO message, @DestinationVariable String chat_id,
            SimpMessageHeaderAccessor headerAccessor) {
        Long senderId = Long.parseLong(headerAccessor.getSessionAttributes().get("userId").toString());

        UserBase sender = userBaseService.findById(senderId).orElse(null);
        Chat chat = service.findById(Long.parseLong(chat_id));

        if (sender == null || chat == null) {
            return null;
        }

        if (chat.getSender().getId() != senderId && chat.getReceiver().getId() != senderId) {
            return null;
        }

        if (message == null || message.getId() == null) {
            return null;
        }

        Message readMessage = service.findMessageById(message.getId());
        if (readMessage == null) {
            return null;
        }

        if (readMessage.getChat().getId() != chat.getId()) {
            return null;
        }

        if (readMessage.getUser().getId() == senderId) {
            return null;
        }

        readMessage.setRead(true);

        service.saveMessage(readMessage);

        return message;
    }

    @MessageMapping("/chat/{receiver_id}/new")
    @SendTo("/topic/chat/{receiver_id}/new")
    public ChatListDTO notifyNewChat(@DestinationVariable Long receiver_id, SimpMessageHeaderAccessor headerAccessor) {
        Long senderId = Long.parseLong(headerAccessor.getSessionAttributes().get("userId").toString());

        UserBase sender = userBaseService.findById(senderId).orElse(null);
        UserBase receiver = userBaseService.findById(receiver_id).orElse(null);

        if (sender == null || receiver == null) {
            return null;
        }

        if (!receiver.getUserType().equals(UserType.COMPANY) && !receiver.getUserType().equals(UserType.STARTUP)) {
            return null;
        }

        Chat chat = service.findChatBySenderAndReceiver(sender, receiver);

        if (chat == null) {
            return null;
        }

        List<Message> messages = service.findMessagesByChat(chat);
        ChatListDTO chatListDTO = dtoConverter.convertChatToChatListDTO(chat, receiver, messages);

        return chatListDTO;
    }

    @GetMapping
    public ResponseEntity<?> getChats(@AuthenticationPrincipal UserBase user) {
        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "The user must be logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        List<Chat> chats = service.findAllByUser(user);

        List<ChatListDTO> chatsListDTO = chats.stream()
                .map(chat -> {
                    List<Message> messages = service.findMessagesByChat(chat);
                    return dtoConverter.convertChatToChatListDTO(chat, user, messages);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(chatsListDTO);

    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> getMessages(@AuthenticationPrincipal UserBase user, @PathVariable Long id) {
        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "The user must be logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        Chat chat = service.findById(id);

        if (chat == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                    "Chat not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }

        if (chat.getSender().getId() != user.getId() && chat.getReceiver().getId() != user.getId()) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "User not authorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        List<Message> messages = service.findMessagesByChat(chat);
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        List<ChatMessageDTO> messagesDTO = messages.stream()
                .map(message -> dtoConverter.convertMessageToChatMessageDTO(message))
                .collect(Collectors.toList());

        return ResponseEntity.ok(messagesDTO);
    }

    @GetMapping("/new/{id}")
    public ResponseEntity<?> getNewChat(@AuthenticationPrincipal UserBase user, @PathVariable Long id) {
        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "The user must be logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        UserBase receiver = userBaseService.findById(id).orElse(null);

        if (receiver == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                    "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }

        if (!receiver.getUserType().equals(UserType.COMPANY) && !receiver.getUserType().equals(UserType.STARTUP)) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
                    "User not authorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        Chat chat = service.findChatBySenderAndReceiver(user, receiver);

        if (chat != null) {
            List<Message> messages = service.findMessagesByChat(chat);
            ChatListDTO chatListDTO = dtoConverter.convertChatToChatListDTO(chat, user, messages);
            return ResponseEntity.ok(chatListDTO);
        }

        GetUserFrontDTO userDTO = dtoConverter.convertUserBaseToDto(receiver);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/new/{id}")
    private ResponseEntity<?> createChat(@AuthenticationPrincipal UserBase user, @PathVariable Long id,
            @RequestBody MessageDTO message) {
        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "The user must be logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        UserBase receiver = userBaseService.findById(id).orElse(null);

        if (receiver == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                    "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }

        if (!receiver.getUserType().equals(UserType.COMPANY) && !receiver.getUserType().equals(UserType.STARTUP)) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
                    "User not authorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        Chat chat = service.findChatBySenderAndReceiver(user, receiver);
        if (chat != null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
                    "Chat already exists");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }

        Chat newChat = new Chat(user, receiver);
        service.save(newChat);

        Message newMessage = new Message(newChat, user, message.getMessage(), MessageType.TEXT);

        service.saveMessage(newMessage);
        List<Message> messages = new ArrayList<>();
        messages.add(newMessage);

        ChatListDTO chatListDTO = dtoConverter.convertChatToChatListDTO(newChat, user, messages);
        return ResponseEntity.ok(chatListDTO);
    }

    @PostMapping("{id}/image")
    public ResponseEntity<?> sendImage(@AuthenticationPrincipal UserBase user, @PathVariable Long id,
            @RequestPart("image") MultipartFile image) {
        if (user == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "The user must be logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        if (id == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
                    "Chat id is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }

        if (image == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
                    "Image is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }

        Chat chat = service.findById(id);

        if (chat == null) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                    "Chat not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }

        if (chat.getSender().getId() != user.getId() && chat.getReceiver().getId() != user.getId()) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                    "User not authorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }

        String urlImage = null;

        if (!images.isExtensionImageValid(image)) {
            throw new ImageNotValidExtension("The extension is invalid");
        }

        urlImage = images.uploadFile(image, uploadMessageDir);
        if (urlImage == null || urlImage.isEmpty()) {
            throw new ImageSubmitError("Error to submit the image");
        }

        SuccessDetails successDetails = new SuccessDetails(HttpStatus.OK.value(), urlImage);

        return ResponseEntity.ok().body(successDetails);
    }
}
