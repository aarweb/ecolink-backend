package com.ecolink.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ecolink.spring.dto.DTOConverter;
import com.ecolink.spring.dto.GetUserDTO;
import com.ecolink.spring.dto.GetUserFrontDTO;
import com.ecolink.spring.dto.NewPasswordDTO;
import com.ecolink.spring.entity.Client;
import com.ecolink.spring.entity.Company;
import com.ecolink.spring.entity.Ods;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.entity.Status;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.entity.UserType;
import com.ecolink.spring.exception.ErrorDetails;
import com.ecolink.spring.exception.ImageNotValidExtension;
import com.ecolink.spring.exception.ImageSubmitError;
import com.ecolink.spring.response.SuccessDetails;
import com.ecolink.spring.security.jwt.JwtProvider;
import com.ecolink.spring.security.jwt.model.JwtUserResponse;
import com.ecolink.spring.security.jwt.model.LoginRequest;
import com.ecolink.spring.service.EmailVerificationService;
import com.ecolink.spring.service.NewPasswordCodeService;
import com.ecolink.spring.service.OdsService;
import com.ecolink.spring.service.StartupService;
import com.ecolink.spring.service.UserBaseService;
import com.ecolink.spring.utils.Images;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;
    private final DTOConverter converter;
    private final UserBaseService service;
    private final StartupService startupService;
    private final EmailVerificationService emailVerificationService;
    private final NewPasswordCodeService newPasswordCodeService;

    private final OdsService odsService;
    private final Images images;

    @Value("${spring.users.upload.dir}")
    private String uploadUserDir;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            System.out.println(loginRequest.getPassword() + " | " + loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserBase user = (UserBase) authentication.getPrincipal();
            if (!user.isVerified()) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                        "The user is not verified");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
            }

            if (user instanceof Startup startup) {
                if (startup.getStatus().equals(Status.REJECTED)) {
                    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                            "The startup is rejected");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
                }

                if (startup.getStatus().equals(Status.PENDING)) {
                    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                            "The startup is pending");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
                }

            } else if (user instanceof Company company) {
                if (company.getStatus().equals(Status.REJECTED)) {
                    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                            "The company is rejected");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
                }

                if (company.getStatus().equals(Status.PENDING)) {
                    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(),
                            "The company is pending");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
                }
            }

            String jwtToken = tokenProvider.generateToken(authentication);

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(30 * 24 * 60 * 60)
                    .sameSite("Lax")
                    .build();

            response.addHeader("Set-Cookie", jwtCookie.toString());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertUserEntityAndTokenToJwtUserResponse(user, jwtToken));
        } catch (BadCredentialsException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), "Credenciales erróneas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocurrió un error interno en el servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newUser(
            @RequestPart("user") String userJson,
            @RequestPart("image") MultipartFile image) {
        String urlImage = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserBase user = objectMapper.readValue(userJson, UserBase.class);

            user.setLikes(new ArrayList<>());
            user.setRegisterDate(java.time.LocalDate.now());
            user.setLevel(0L);

            if (!images.isExtensionImageValid(image)) {
                throw new ImageNotValidExtension("The extension is invalid");
            }
            urlImage = images.uploadFile(image, uploadUserDir);
            if (urlImage == null || urlImage.isEmpty()) {
                throw new ImageSubmitError("Error to submit the image");
            }
            user.setImageUrl(urlImage);

            GetUserFrontDTO dto;
            if (user instanceof Startup startup) {
                user.setUserType(UserType.STARTUP);
                startupService.changeStartupState(startup, Status.PENDING);
                startup.setProposals(new ArrayList<>());
                startup.setProducts(new ArrayList<>());
                startup.setOdsList(odsService.findAllById(
                        startup.getOdsList().stream().map(Ods::getId).collect(Collectors.toList())));
                dto = converter.convertStartupBaseToDto(startup);
            } else if (user instanceof Company company) {
                user.setUserType(UserType.COMPANY);
                company.setStatus(Status.PENDING);
                dto = converter.convertCompanypBaseToDto(company);
            } else {
                Client client = (Client) user;
                user.setUserType(UserType.CLIENT);
                List<Ods> preferences = odsService.findAllById(
                        client.getPreferences().stream()
                                .map(Ods::getId)
                                .collect(Collectors.toList()));
                client.setPreferences(preferences);
                dto = converter.convertClientBaseToDto(client);
            }

            if (service.existsByEmail(user.getEmail())) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN, "Email already exists");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
            }

            if (service.existsByName(user.getName())) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN, "Name already exists");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
            }

            emailVerificationService.sendVerificationEmail(user);

            service.newUser(user);
            dto.setId(user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (ImageNotValidExtension | ImageSubmitError e) {
            if (urlImage != null && !urlImage.isEmpty()) {
                images.deleteFile(urlImage, uploadUserDir);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN, "User or email alredy exists");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
        } catch (Exception e) {
            if (urlImage != null && !urlImage.isEmpty()) {
                images.deleteFile(urlImage, uploadUserDir);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", jwtCookie.toString());

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Sesión cerrada exitosamente");

        return ResponseEntity.ok(responseBody);
    }

    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(UserBase user, String jwtToken) {
        return JwtUserResponse.jwtUserResponseBuilder()
                .username(user.getEmail())
                .name(user.getName())
                .rol(user.getUserType())
                .id(user.getId())
                .imageUrl(user.getImageUrl())
                .token(jwtToken)
                .build();
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        try {

            if (email == null || email.isEmpty()) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Email is required");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);

            }

            UserBase user = service.findByEmail(email).orElse(null);
            if (user == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "User not found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
            }

            emailVerificationService.sendResetPasswordCode(user);

            SuccessDetails successMessage = new SuccessDetails(HttpStatus.OK.value(), "Email sent");

            return ResponseEntity.status(HttpStatus.OK).body(successMessage);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
        try {
            UserBase user = service.findByEmail(newPasswordDTO.getEmail()).orElse(null);

            if (user == null) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "User not found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
            }

            if (!newPasswordCodeService.getNewPasswordCode(user).equals(newPasswordDTO.getCode())) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), "Invalid code");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
            }

            if (newPasswordDTO.getNewPassword().length() < 8) {
                ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(),
                        "The password must have at least 8 characters");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);

            }

            service.newPassword(user, newPasswordDTO.getNewPassword());
            newPasswordCodeService.deleteNewPasswordCode(user);

            service.save(user);

            SuccessDetails successMessage = new SuccessDetails(HttpStatus.OK.value(), "Password changed");

            return ResponseEntity.status(HttpStatus.OK).body(successMessage);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @GetMapping("/user/me")
    public GetUserDTO me(@AuthenticationPrincipal UserBase user) {

        return converter.convertUserDTO(user);
    }
}