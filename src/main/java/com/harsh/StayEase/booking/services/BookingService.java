package com.harsh.StayEase.booking.services;

import com.harsh.StayEase.booking.controller.exchanges.AddBookingDto;

import com.harsh.StayEase.booking.dto.BookingDto;

/**
 * Service interface for managing bookings.
 */
public interface BookingService {
    
    // only single room is allowed to book at a time
    /**
     * Books a hotel room for the specified hotel and booking details.
     * 
     * @param hotelId The ID of the hotel for which the room is being booked.
     * @param addBookingDto The DTO containing the booking details.
     * @return The DTO representing the booked booking.
     */
    BookingDto bookHotelRoom(Long hotelId, AddBookingDto addBookingDto);

    /**
     * Cancels a booking with the specified ID.
     * 
     * @param bookingId The ID of the booking to be cancelled.
     */
    void cancelBooking(Long bookingId);

}
