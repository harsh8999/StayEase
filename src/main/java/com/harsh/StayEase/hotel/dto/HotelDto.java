package com.harsh.StayEase.hotel.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelDto {
    
    Long id;
    String name;
    String location;

    String description;

    int numberOfAvailableRooms;

}
