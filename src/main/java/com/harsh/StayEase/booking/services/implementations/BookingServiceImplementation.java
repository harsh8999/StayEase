package com.harsh.StayEase.booking.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.harsh.StayEase.booking.controller.exchanges.AddBookingDto;
import com.harsh.StayEase.booking.dto.BookingDto;
import com.harsh.StayEase.booking.entity.Booking;
import com.harsh.StayEase.booking.repository.BookingRepository;
import com.harsh.StayEase.booking.services.BookingService;
import com.harsh.StayEase.exception.exceptions.ResourceNotFoundException;
import com.harsh.StayEase.hotel.entity.Hotel;
import com.harsh.StayEase.hotel.repository.HotelRepository;
import com.harsh.StayEase.user.entity.User;
import com.harsh.StayEase.user.services.UserAuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BookingServiceImplementation implements BookingService {

    private BookingRepository bookingRepository;
    private UserAuthService userAuthService;
    private HotelRepository hotelRepository;
    
    @Override
    public BookingDto bookHotelRoom(Long hotelId, AddBookingDto addBookingDto) {

        User loggedInUser = userAuthService.getLoggedInUser();
        log.debug("Booking Hotel Room for user Id {} and hotel Id {}", loggedInUser.getId(), hotelId);
        
        if(hotelId == null) {
            throw new IllegalArgumentException("Hotel ID cannot be null");
        }

        if(addBookingDto == null) {
            throw new IllegalArgumentException("Request cannot be null or missing required fields");    
        }

        // if checkout date is before checkin date illegal request
        if(addBookingDto.getCheckOutDate().before(addBookingDto.getCheckInDate())) {
            log.error("Check-out date must be after check-in date");
            throw new IllegalStateException("Check-out date must be after check-in date");
        }

        log.debug("Checking availability for hotel with ID {}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "Hotel ID", Long.toString(hotelId)));

        if(hotel.getNumberOfAvailableRooms() < 0) {
            log.error("Not enough available rooms for the specified dates");
            throw new IllegalStateException("Not enough available rooms for the specified dates");
        }
        
        Booking booking = new Booking();
        booking.setActive(true);
        booking.setUser(loggedInUser);
        booking.setHotel(hotel);
        booking.setCheckInDate(addBookingDto.getCheckInDate());
        booking.setCheckOutDate(addBookingDto.getCheckOutDate());

        Booking savedBooking = bookingRepository.save(booking);

        // reduce available rooms
        hotel.setNumberOfAvailableRooms(hotel.getNumberOfAvailableRooms() - 1);

        hotelRepository.save(hotel);

        log.debug("Booking successfully created for hotel ID: {}", hotelId);

        BookingDto bookingDto = BookingDto.builder()
            .id(savedBooking.getId())
            .userId(loggedInUser.getId())
            .hotelId(hotelId)
            .checkInDate(savedBooking.getCheckInDate())
            .checkOutDate(savedBooking.getCheckOutDate()).build();
        return bookingDto;
    }

    @Override
    public void cancelBooking(Long bookingId) {
        if(bookingId == null) {
            throw new IllegalArgumentException("Hotel ID cannot be null");
        }
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking", "Booking ID", Long.toString(bookingId)));

        booking.setActive(false);

        bookingRepository.save(booking);
    }
    
}
