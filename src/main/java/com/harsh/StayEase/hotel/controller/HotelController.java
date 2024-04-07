package com.harsh.StayEase.hotel.controller;

import org.springframework.web.bind.annotation.RestController;

import com.harsh.StayEase.hotel.controller.exchange.AddHotelDto;
import com.harsh.StayEase.hotel.controller.exchange.HotelUpdateDto;
import com.harsh.StayEase.hotel.dto.HotelDto;
import com.harsh.StayEase.hotel.services.HotelService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controller class for handling hotel-related HTTP requests.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class HotelController {
    
    private static final String BASE_URL = "/hotels";

    private HotelService hotelService;

    /**
     * Retrieves all hotels.
     * 
     * @return ResponseEntity containing a list of HotelDto representing all hotels.
     */
    @GetMapping(BASE_URL + "/getall")
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        log.info("Fetching all hotels");
        List<HotelDto> hotelDtos = hotelService.getAllHotels();
        log.info("Retrieved {} hotels", hotelDtos.size());
        return new ResponseEntity<>(hotelDtos, HttpStatus.OK);
    }

    @PostMapping(BASE_URL + "/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelDto> addHotel(@Valid @RequestBody AddHotelDto addHotelDto) {
        log.info("Adding a new hotel: {}", addHotelDto);
        HotelDto hotelDto = hotelService.addHotel(addHotelDto);
        log.info("New hotel added successfully: {}", hotelDto);
        return new ResponseEntity<>(hotelDto, HttpStatus.CREATED);
    }

    @DeleteMapping(BASE_URL + "/{hotel_id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable("hotel_id") Long hotelId) {
        log.info("Deleting hotel with ID: {}", hotelId);
        hotelService.deleteHotel(hotelId);
        log.info("Hotel with ID {} deleted successfully", hotelId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(BASE_URL + "/{hotel_id}/update")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable("hotel_id") Long hotelId, @RequestBody HotelUpdateDto hotelUpdateDto) {
        log.info("Updating hotel with ID: {}", hotelId);
        HotelDto hotelDto = hotelService.updateHotelDetails(hotelId, hotelUpdateDto);
        log.info("Hotel with ID {} updated successfully", hotelId);
        return new ResponseEntity<>(hotelDto, HttpStatus.OK);
    }
    

}
