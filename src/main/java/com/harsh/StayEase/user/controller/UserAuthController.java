package com.harsh.StayEase.user.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.StayEase.jwt_security.service.JwtService;
import com.harsh.StayEase.user.controller.exchange.LoginDto;
import com.harsh.StayEase.user.controller.exchange.LoginResponseDto;
import com.harsh.StayEase.user.controller.exchange.SignUpUserDto;
import com.harsh.StayEase.user.dtos.UserDto;
import com.harsh.StayEase.user.entity.User;
import com.harsh.StayEase.user.services.UserAuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class for handling user authentication and registration endpoints.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserAuthController {
    
    private static final String UN_AUTHENTICATED_BASE_URL = "/auth";
    
    private UserAuthService userAuthService;
    private JwtService jwtService;

    /**
     * Handles the root directory endpoint and returns a welcome message.
     * 
     * @return ResponseEntity containing the welcome message.
     */
    @GetMapping("/")
    public ResponseEntity<String> getRootDirectory() {
        String message = "Welcome to the StayEase application!";

        // Log the message
        log.info("Root directory accessed. Response: {}", message);

        return ResponseEntity.ok().body(message);
    }

    /**
     * Handles the user sign-up endpoint and returns the newly signed-up user.
     * 
     * @param signUpUserDto The SignUpUserDto containing user sign-up details.
     * @return ResponseEntity containing the newly signed-up user DTO.
     */
    @PostMapping(UN_AUTHENTICATED_BASE_URL + "/signup")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpUserDto signUpUserDto) {
        // Log the message
        log.info("Received sign-up request for email: {}", signUpUserDto.getEmail());

        UserDto userDto = userAuthService.signUp(signUpUserDto);

        // Log the message
        log.info("User signed up successfully with email: {}", signUpUserDto.getEmail());

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    /**
     * Handles the user login endpoint and returns a JWT token upon successful authentication.
     * 
     * @param loginDto The LoginDto containing user login details.
     * @return ResponseEntity containing the JWT token and expiration time upon successful authentication.
     */
    @GetMapping(UN_AUTHENTICATED_BASE_URL + "/generatetoken")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto entity) {

        // Log the message
        log.info("Received login request for email: {}", entity.getEmail());

        Optional<User> optionalUser = userAuthService.authenticate(entity);
        // Check if the user is authenticated
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String jwtToken = jwtService.generateToken(user);
            
            // Log the message
            log.info("User logged in successfully with email: {}", entity.getEmail());

            // Create and return the login response with JWT token and expiration time
            LoginResponseDto loginResponse = new LoginResponseDto(jwtToken, jwtService.getJWT_EXPIRATION_TIME());
            return ResponseEntity.ok().body(loginResponse);
        }

        // Log the message
        log.error("Failed login attempt for email: {}", entity.getEmail());

        // If user authentication fails, return unauthorized status
        return ResponseEntity.badRequest().build();
    }
}
