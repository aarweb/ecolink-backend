package com.ecolink.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.ecolink.spring.entity.Chat;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.security.jwt.JwtProvider;
import com.ecolink.spring.service.ChatService;
import com.ecolink.spring.service.UserBaseService;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private ChatService chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            boolean isNewChat = false;
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
            Map<String, List<String>> headers = accessor.toNativeHeaderMap();

            if (headers != null && headers.containsKey("Authorization")) {

                List<String> authHeaders = headers.get("Authorization");
                if (authHeaders != null) {
                    for (String authHeader : authHeaders) {
                        if (authHeader.startsWith("Bearer ")) {
                            String token = authHeader.substring(7);

                            Long id_user = tokenProvider.getUserIdFromJWT(token);

                            UserBase user = userBaseService.findById(id_user).orElse(null);

                            if (user != null) {
                                if (accessor.getCommand() == StompCommand.SUBSCRIBE
                                        || accessor.getCommand() == StompCommand.SEND) {
                                    String destination = accessor.getDestination();

                                    String chatIdStr = null;

                                    if (destination != null
                                            && (destination.startsWith("/topic/chat/")
                                                    || destination.startsWith("/app/chat/"))) {
                                        String[] parts = destination.split("/");
                                        if (parts.length > 3) {
                                            chatIdStr = parts[3];
                                        }

                                        if (parts.length >= 4) {
                                            if (parts[4].equals("new")) {
                                                isNewChat = true;
                                            }
                                        }
                                    }

                                    if (chatIdStr == null) {
                                        throw new RuntimeException("Chat no encontrado");
                                    }

                                    Long chatId = Long.parseLong(chatIdStr);


                                    Chat chat = null;

                                    if (isNewChat) {
                                        System.out.println("Nuevo chat");
                                        UserBase receiver = userBaseService.findById(chatId).orElse(null);
                                        if (receiver == null) {
                                            throw new RuntimeException("Usuario no encontrado");
                                        }
                                        chat = chatService.findChatBySenderAndReceiver(user, receiver);
                                    } else {
                                        chat = chatService.findById(chatId);
                                    }

                                    if (chat == null) {
                                        throw new RuntimeException("Chat no encontrado");
                                    }

                                    if (chat.getSender().getId() == user.getId()
                                            || chat.getReceiver().getId() == user.getId()) {
                                        accessor.getSessionAttributes().put("userId", id_user);
                                    } else {
                                        System.out.println("Chat Sender y Receiver" + chat.getSender().getId() + " - "
                                                + chat.getReceiver().getId() + " - " + user.getId());
                                        throw new RuntimeException("Usuario no autorizado");
                                    }
                                }

                                accessor.getSessionAttributes().put("userId", id_user);
                            } else {
                                throw new RuntimeException("Usuario no encontrado");
                            }
                        }
                    }
                }
            }

            return message;
        } catch (Exception e) {
            System.err.println("Error in WebSocketInterceptor: " + e.getMessage());
            return null;
        }
    }

}