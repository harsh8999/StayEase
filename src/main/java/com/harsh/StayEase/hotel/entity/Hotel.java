package com.harsh.StayEase.hotel.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Entity class representing a hotel.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Hotel {
    
    /**
     * The unique identifier for the hotel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    /**
     * The name of the hotel.
     */
    @Column(nullable = false)
    String name;

    /**
     * The location of the hotel.
     */
    String location;

    /**
     * The description of the hotel.
     */
    String description;

    /**
     * The number of available rooms in the hotel.
     */
    int numberOfAvailableRooms;

    /**
     * The date and time when the hotel was created.
     * This field is automatically managed by the database.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    Date createdAt;
}
