package com.harsh.StayEase.user.services.implementations;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harsh.StayEase.exception.exceptions.ResourceNotFoundException;
import com.harsh.StayEase.user.controller.exchange.LoginDto;
import com.harsh.StayEase.user.controller.exchange.SignUpUserDto;
import com.harsh.StayEase.user.dtos.UserDto;
import com.harsh.StayEase.user.entity.User;
import com.harsh.StayEase.user.entity.enums.Role;
import com.harsh.StayEase.user.repository.UserRepository;
import com.harsh.StayEase.user.services.UserAuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the UserAuthService interface.
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserAuthServiceImplementation implements UserAuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private ModelMapper modelMapper;

    @Override
    public UserDto signUp(SignUpUserDto requestUserDto) {
        if (requestUserDto == null || requestUserDto.getEmail() == null || requestUserDto.getPassword() == null) {
            log.error("Invalid sign up request: {}", requestUserDto);
            throw new IllegalArgumentException("Request cannot be null or missing required fields");
        }

        User user = new User();
        user.setFirstName(requestUserDto.getFirstName());
        user.setLastName(requestUserDto.getLastName());
        user.setEmail(requestUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestUserDto.getPassword()));
        
        if(requestUserDto.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        } else {
            user.setRole(requestUserDto.getRole());
        }

        // save the user
        User savedUser = userRepository.save(user);
        
        // log
        log.info("User signed up successfully: {}", requestUserDto.getEmail());

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public Optional<User> authenticate(LoginDto loginDto) {
        // log
        log.info("Authenticating user with email: {}", loginDto.getEmail());
            
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        log.info("Authentication successful for user with email: {}", loginDto.getEmail());
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "Email", loginDto.getEmail()));
        return Optional.of(user);
    }

    @Override
    public User getLoggedInUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User loggedInUser = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email", username));
        return loggedInUser;
    }
    
}
