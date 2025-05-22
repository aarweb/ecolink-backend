package com.ecolink.spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.service.OpenRouterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openrouter")
public class OpenRouterController {

        private final OpenRouterService openRouterService;

        @GetMapping("/obtener_respuesta")
        public ResponseEntity<String> obtenerRespuesta(
                        @AuthenticationPrincipal UserBase user,
                        @RequestParam List<String> pregunta) {
                if (user == null) {
                        return ResponseEntity.badRequest().body("Usuario no autenticado");
                }
                String respuesta = openRouterService.obtenerRespuesta(pregunta, user);
                return ResponseEntity.ok(respuesta);
        }
}
