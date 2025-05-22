package com.ecolink.spring.service;

import com.ecolink.spring.entity.UserBase;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenRouterService {

        private final WebClient client;

        public OpenRouterService(@Value("${openrouter.api.key}") String apiKey) {
                this.client = WebClient.builder()
                                .baseUrl("https://openrouter.ai/api/v1")
                                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                                .build();
        }

        public String obtenerRespuesta(List<String> mensajesChat, UserBase user) {

                if (mensajesChat == null || mensajesChat.isEmpty()) {
                        return "Hola, ¿qué tal?";
                }

                Map<String, String> system = Map.of(
                                "role", "system",
                                "content",
                                """
                                                Responde en español formal como %s.
                                                Un único mensaje (<200 caracteres). Sin emojis ni menciones a que eres IA.
                                                Omite las preguntas de las que no tengas información y no las nombres.
                                                Si el usuario pregunta por tu estado (“hola, ¿qué tal?”, “¿cómo te encuentras?”),
                                                responde brevemente con tu estado y devuélvele la pregunta antes de ofrecer ayuda.
                                                """
                                                .formatted(user.getName()));

                List<Map<String, String>> mensajes = new ArrayList<>();
                mensajes.add(system);
                mensajesChat.stream()
                                .limit(10) // o los que necesites
                                .forEach(m -> {
                                        String role = m.startsWith("You:") ? "assistant" : "user";
                                        String texto = m.substring(m.indexOf(':') + 1).trim();
                                        mensajes.add(Map.of("role", role, "content", texto));
                                });

                Map<String, Object> body = Map.of(
                                "model", "openrouter/auto",
                                "messages", mensajes,
                                "provider", Map.of("max_price", Map.of("prompt", 0, "completion", 0)),
                                "max_tokens", 100,
                                "temperature", 0.7);

                return client.post()
                                .uri("/chat/completions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(body)
                                .retrieve()
                                .bodyToMono(JsonNode.class)
                                .timeout(Duration.ofSeconds(30))
                                .map(json -> json.at("/choices/0/message/content")
                                                .asText("Sin respuesta"))
                                .block();
        }

}