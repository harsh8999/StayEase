package com.harsh.StayEase.booking.dto;

import java.util.Date;

import com.harsh.StayEase.hotel.entity.Hotel;
import com.harsh.StayEase.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class representing a booking.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    
    /**
     * The ID of the booking.
     */
    Long id;

    /**
     * The user who made the booking.
     */
    Long userId;

    /**
     * The hotel where the booking was made.
     */
    Long hotelId;

    /**
     * The check-in date of the booking.
     */
    Date checkInDate;

    /**
     * The check-out date of the booking.
     */
    Date checkOutDate;
}
