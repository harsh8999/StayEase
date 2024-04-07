package com.harsh.StayEase.hotel.controller.exchange;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelUpdateDto {
    
    String name;
    
    String location;

    String description;

    int numberOfAvailableRooms;
}
