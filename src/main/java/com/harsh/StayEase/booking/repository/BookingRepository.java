package com.harsh.StayEase.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsh.StayEase.booking.entity.Booking;

/**
 * Repository interface for managing bookings in the database.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
