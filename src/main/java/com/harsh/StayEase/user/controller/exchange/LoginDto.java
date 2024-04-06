package com.harsh.StayEase.user.controller.exchange;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Data transfer object for user login requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {
    
    /**
     * The email address of the user.
     */
    @NotBlank
    String email;

    /**
     * The password of the user.
     */
    @NotBlank
    String password;

}
