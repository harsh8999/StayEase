package com.harsh.StayEase.hotel.controller.exchange;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddHotelDto {
    
    @NotBlank
    String name;

    @NotBlank
    String location;

    String description;

    int numberOfAvailableRooms;
}
