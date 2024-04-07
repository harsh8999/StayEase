package com.harsh.StayEase.hotel.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.harsh.StayEase.exception.exceptions.ResourceNotFoundException;
import com.harsh.StayEase.hotel.controller.exchange.AddHotelDto;
import com.harsh.StayEase.hotel.controller.exchange.HotelUpdateDto;
import com.harsh.StayEase.hotel.dto.HotelDto;
import com.harsh.StayEase.hotel.entity.Hotel;
import com.harsh.StayEase.hotel.repository.HotelRepository;
import com.harsh.StayEase.hotel.services.HotelService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class HotelServiceImplementation implements HotelService {

    private HotelRepository hotelRepository;
    private ModelMapper modelMapper;
    
    @Override
    public List<HotelDto> getAllHotels() {
        log.info("Fetching all Hotels");
        List<Hotel> hotels = hotelRepository.findAll();
        List<HotelDto> hotelDtos = new ArrayList<>();
        hotels.forEach(hotel -> hotelDtos.add(modelMapper.map(hotel, HotelDto.class)));
        log.info("Returning {} Hotels", hotelDtos.size());
        return hotelDtos;
    }

    @Override
    public HotelDto getHotelById(Long hotelId) {
        if(hotelId == null) {
            throw new IllegalArgumentException("Hotel ID cannot be null");
        }
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "Hotel Id", Long.toString(hotelId)));
        log.info("Returning Hotel Id : {}", hotelId);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto addHotel(AddHotelDto addHotelDto) {
        if (addHotelDto == null) {
            log.error("Invalid request: Hotel DTO is null.");
            throw new IllegalArgumentException("Request cannot be null or missing required fields");    
        }

        log.info("Adding hotel with name: {}, with available Rooms: {}", addHotelDto.getName(), addHotelDto.getNumberOfAvailableRooms());

        Hotel hotel = Hotel.builder()
            .name(addHotelDto.getName())
            .description(addHotelDto.getDescription())
            .location(addHotelDto.getLocation())
            .numberOfAvailableRooms(addHotelDto.getNumberOfAvailableRooms())
            .build();
        
        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Hotel added successfully with ID: {}", savedHotel.getId());

        return modelMapper.map(savedHotel, HotelDto.class);
    }

    @Override
    public void deleteHotel(Long hotelId) {
        log.info("Deleting Hotel by hotel ID: {}", hotelId);
        if(hotelId == null) {
            throw new IllegalArgumentException("Hotel Id cannot be null!!!");
        }
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "Hotel ID", Long.toString(hotelId)));
        hotelRepository.delete(hotel);
        log.info("Hotel deleted successfully");
    }

    @Override
    public HotelDto updateHotelDetails(Long hotelId, HotelUpdateDto hotelUpdateDto) {
        log.info("Updating Hotel by hotel ID: {}", hotelId);
        if(hotelId == null) {
            throw new IllegalArgumentException("Hotel Id cannot be null!!!");
        }

        if (hotelUpdateDto == null) {
            log.error("Invalid request: Hotel Update Dto is null.");
            throw new IllegalArgumentException("Request cannot be null or missing required fields");    
        }

        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel", "Hotel ID", Long.toString(hotelId)));
        
        // update if details is present and not blank
        if(hotelUpdateDto.getName() != null && !hotelUpdateDto.getName().isBlank()) {
            hotel.setName(hotelUpdateDto.getName());
        }
        if(hotelUpdateDto.getDescription() != null && !hotelUpdateDto.getDescription().isBlank()) {
            hotel.setDescription(hotelUpdateDto.getDescription());
        }
        if(hotelUpdateDto.getLocation() != null && !hotelUpdateDto.getLocation().isBlank()) {
            hotel.setLocation(hotelUpdateDto.getLocation());
        }
        if(hotelUpdateDto.getNumberOfAvailableRooms() >= 0) {
            hotel.setNumberOfAvailableRooms(hotelUpdateDto.getNumberOfAvailableRooms());
        }

        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Hotel Updated successfully with ID: {}", savedHotel.getId());

        return modelMapper.map(savedHotel, HotelDto.class);

    }

}
