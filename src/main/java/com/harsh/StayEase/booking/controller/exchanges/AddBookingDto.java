package com.harsh.StayEase.booking.controller.exchanges;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class representing the request body for adding a booking.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBookingDto {

    /**
     * The check-in date of the booking.
     */
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date checkInDate;

    /**
     * The check-out date of the booking.
     */
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date checkOutDate;

}
