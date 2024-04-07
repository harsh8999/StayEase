package com.harsh.StayEase.user.services;

import java.util.Optional;

import com.harsh.StayEase.user.controller.exchange.LoginDto;
import com.harsh.StayEase.user.controller.exchange.SignUpUserDto;
import com.harsh.StayEase.user.dtos.UserDto;
import com.harsh.StayEase.user.entity.User;

/**
 * Service interface for user authentication and registration operations.
 */
public interface UserAuthService {

    /**
     * Registers a new user based on the provided SignUpUserDto.
     * 
     * @param requestUserDto The SignUpUserDto containing user registration information.
     * @return The UserDto representing the registered user.
     */
    UserDto signUp(SignUpUserDto requestUserDto);

    /**
     * Authenticates a user based on the provided LoginDto.
     * 
     * @param loginDto The LoginDto containing user login information.
     * @return An Optional containing the authenticated User if successful, empty otherwise.
     */
    Optional<User> authenticate(LoginDto loginDto);

    /**
     * Retrieves the currently logged-in user.
     * 
     * @return The User object representing the currently logged-in user.
     * @throws IllegalStateException if no user is currently logged in.
     */
    User getLoggedInUser();

}
