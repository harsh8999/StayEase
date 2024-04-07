package com.harsh.StayEase.booking.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.harsh.StayEase.hotel.entity.Hotel;
import com.harsh.StayEase.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.Data;

/**
 * Entity class representing a booking made by a user for a hotel.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    
    /**
     * The ID of the booking.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    /**
     * The user who made the booking.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
    
    /**
     * The hotel for which the booking is made.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    Hotel hotel;

    /**
     * The timestamp when the booking was made.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "booking_time")
    Date bookingTime;

    /**
     * The check-in date of the booking.
     */
    Date checkInDate;

    /**
     * The check-out date of the booking.
     */
    Date checkOutDate;

    /**
     * Flag indicating if the booking is active.
     */
    boolean isActive;

}
