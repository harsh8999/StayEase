package com.harsh.StayEase.jwt_security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service class for JWT token generation and validation.
 */
@Service
public class JwtService {
    
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiration-time}")
    private long JWT_EXPIRATION_TIME;

    /**
     * Get the expiration time of JWT tokens.
     * 
     * @return The expiration time of JWT tokens in milliseconds.
     */
    public long getJWT_EXPIRATION_TIME() {
        return JWT_EXPIRATION_TIME;
    }

    /**
     * Extracts the username from the JWT token.
     * 
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String extractUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a claim from the JWT token using a provided resolver function.
     * 
     * @param token The JWT token.
     * @param claimResolver The function to resolve the claim.
     * @return The resolved claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the given UserDetails.
     * 
     * @param userDetails The UserDetails object representing the user.
     * @return The generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for the given UserDetails with additional claims.
     * 
     * @param extraClaims Additional claims to include in the token.
     * @param userDetails The UserDetails object representing the user.
     * @return The generated JWT token.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, JWT_EXPIRATION_TIME);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
            .builder()
            .claims().empty().add(extraClaims).and()
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), Jwts.SIG.HS512)
            .compact();

    }

    /**
     * Validates whether the JWT token is valid for the given UserDetails.
     * 
     * @param token The JWT token.
     * @param userDetails The UserDetails object representing the user.
     * @return true if the token is valid for the user, otherwise false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks whether the JWT token has expired.
     * 
     * @param token The JWT token.
     * @return true if the token has expired, otherwise false.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * 
     * @param token The JWT token.
     * @return The expiration date extracted from the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
