package com.harsh.StayEase.jwt_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.harsh.StayEase.exception.exceptions.ResourceNotFoundException;
import com.harsh.StayEase.user.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class for user security settings.
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class UserSecurityConfig {

    private UserRepository userRepository;

    /**
     * Defines a UserDetailsService bean that retrieves user details from the UserRepository.
     * 
     * @return The UserDetailsService bean
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            log.debug("Fetching user details for username: {}", username);
            return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email", username));
        };
    }
    
    /**
     * Defines a PasswordEncoder bean for encoding and verifying passwords.
     * 
     * @return The PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines an AuthenticationManager bean to manage authentication processes.
     * 
     * @param configuration The authentication configuration.
     * @return The AuthenticationManager bean
     * @throws Exception If an error occurs while creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Defines an AuthenticationProvider bean for user authentication.
     * 
     * @return The AuthenticationProvider bean
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
