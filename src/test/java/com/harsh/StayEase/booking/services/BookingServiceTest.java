package com.harsh.StayEase.booking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;

import com.harsh.StayEase.booking.controller.exchanges.AddBookingDto;
import com.harsh.StayEase.booking.entity.Booking;
import com.harsh.StayEase.booking.repository.BookingRepository;
import com.harsh.StayEase.booking.services.implementations.BookingServiceImplementation;
import com.harsh.StayEase.hotel.entity.Hotel;
import com.harsh.StayEase.hotel.repository.HotelRepository;
import com.harsh.StayEase.user.entity.User;
import com.harsh.StayEase.user.services.UserAuthService;

@SpringBootTest
public class BookingServiceTest {

    @InjectMocks
    private BookingServiceImplementation bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private UserAuthService userAuthService;

    @MockBean
    private HotelRepository hotelRepository;

    @BeforeEach
    public void setUp() {
        bookingService = new BookingServiceImplementation(bookingRepository, userAuthService, hotelRepository);
    }

    @Test
    @Description("if checkout date is before checkin date illegal request")
    public void bookHotelRoom_IllegalStateException() {
        Long hotelId = 1L;
        Date checkInDate = new Date();
        Date checkOutDate = new Date(checkInDate.getTime() - 86400000); // One day before check-in
        AddBookingDto addBookingDto = new AddBookingDto(checkInDate, checkOutDate);
        User loggedInUser = new User();

        Hotel hotel = new Hotel();
        hotel.setNumberOfAvailableRooms(-1); // Simulate not enough available rooms
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(userAuthService.getLoggedInUser()).thenReturn(loggedInUser);

        assertThrows(IllegalStateException.class, () -> bookingService.bookHotelRoom(1L, addBookingDto));
    }

    @Test
    public void cancelBooking_Success() {
        
        Long bookingId = 1L;
        Booking booking = new Booking();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(bookingId);

        assertEquals(false, booking.isActive());
        verify(bookingRepository).save(booking);
    }
}
