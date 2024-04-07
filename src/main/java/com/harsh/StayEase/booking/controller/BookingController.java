package com.harsh.StayEase.booking.controller;

import org.springframework.web.bind.annotation.RestController;

import com.harsh.StayEase.booking.controller.exchanges.AddBookingDto;
import com.harsh.StayEase.booking.dto.BookingDto;
import com.harsh.StayEase.booking.services.BookingService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller class for booking-related.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class BookingController {
    
    private static final String BASE_URL = "/bookings";

    private BookingService bookingService;

    /**
     * Handles the request to book a room in a hotel.
     * 
     * @param hotelId The ID of the hotel to book a room in.
     * @param addBookingDto The request body containing the details of the booking.
     * @return ResponseEntity containing the details of the booking that was created.
     */
    @PostMapping(BASE_URL + "/hotels/{hotelId}/book")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<BookingDto> bookRoom(@PathVariable("hotelId") Long hotelId, @RequestBody AddBookingDto addBookingDto) {
        log.info("Received request to book room for hotel ID: {}", hotelId);
        BookingDto bookingDto = bookingService.bookHotelRoom(hotelId, addBookingDto);
        log.info("Booking successfully created for hotel ID: {}", hotelId);

        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    /**
     * Handles the request to cancel a booking.
     * 
     * @param bookingId The ID of the booking to cancel.
     * @return ResponseEntity containing a message indicating the success of the cancellation.
     */
    @DeleteMapping(BASE_URL + "/{bookingId}/cancel")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<Map<String, String>> cancelBooking(@PathVariable("bookingId") Long bookingId) {

        log.info("Received request to cancel booking with ID: {}", bookingId);
        bookingService.cancelBooking(bookingId);
        log.info("Booking with ID {} has been cancelled", bookingId);
        return new ResponseEntity<>(Map.of("Message", "Booking has been cancelled."), HttpStatus.OK);
    }
}
