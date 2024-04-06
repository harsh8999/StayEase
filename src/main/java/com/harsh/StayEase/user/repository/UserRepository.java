package com.harsh.StayEase.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsh.StayEase.user.entity.User;

/**
 * Repository interface for accessing User entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email.
     * 
     * @param email The email address of the user to find.
     * @return An Optional containing the user with the specified email, or empty if not found.
     */
    Optional<User> findByEmail(String email);
    
}
