package com.harsh.StayEase.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.StayEase.hotel.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    
}
