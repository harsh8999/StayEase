package com.harsh.StayEase.hotel.services;

import java.util.List;

import com.harsh.StayEase.hotel.controller.exchange.AddHotelDto;
import com.harsh.StayEase.hotel.controller.exchange.HotelUpdateDto;
import com.harsh.StayEase.hotel.dto.HotelDto;

/**
 * Service interface for hotel-related operations.
 */
public interface HotelService {

    // anyone can access
    /**
     * Retrieves all hotels.
     * 
     * @return A list of HotelDto representing all hotels.
     */
    List<HotelDto> getAllHotels();

    /**
     * Retrieves a hotel by its unique identifier.
     * 
     * @param hotelId The unique identifier of the hotel.
     * @return The HotelDto representing the hotel with the specified ID.
     */
    HotelDto getHotelById(Long hotelId);

    
    // only for ADMIN
    /**
     * Adds a new hotel. (Accessible to ADMIN)
     * 
     * @param addHotelDto The AddHotelDto containing information to add a new hotel.
     * @return The HotelDto representing the newly added hotel.
     */
    HotelDto addHotel(AddHotelDto addHotelDto);

    /**
     * Deletes a hotel by its unique identifier. (Accessible to ADMIN)
     * 
     * @param hotelId The unique identifier of the hotel to delete.
     */
    void deleteHotel(Long hotelId);

    // only for HOTEL_MANAGER
    /**
     * Updates details of a hotel. (Accessible to HOTEL_MANAGER)
     * 
     * @param hotelId The unique identifier of the hotel to update.
     * @param hotelUpdateDto The HotelUpdateDto containing updated information.
     * @return The HotelDto representing the updated hotel.
     */
    HotelDto updateHotelDetails(Long hotelId, HotelUpdateDto hotelUpdateDto);
    
}
