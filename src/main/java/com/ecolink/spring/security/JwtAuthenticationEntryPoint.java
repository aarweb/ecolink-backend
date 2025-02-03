package com.ecolink.spring.security;

import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint {

    	private final ObjectMapper mapper;
	
	// @Override
	// public void commence(HttpServletRequest request, HttpServletResponse response,
	// 		AuthenticationException authException) throws IOException, ServletException {
		
	// 	response.setContentType("application/json");
	// 	response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
	// 	//ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, authException.getMessage());
	// 	//Tranforma la apiError a un String (en formato JSON)
	// 	String strApiError = mapper.writeValueAsString(apiError);
		
	// 	PrintWriter writer = response.getWriter();
	// 	writer.println(strApiError);
	// }

}
