package com.harsh.StayEase.user.controller.exchange;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Data transfer object for user login response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponseDto {

    /**
     * The JWT token generated upon successful login.
     */
    String token;
    
    /**
     * The expiration time of the JWT token, in milliseconds.
     */
    long expiresIn;
    
}
