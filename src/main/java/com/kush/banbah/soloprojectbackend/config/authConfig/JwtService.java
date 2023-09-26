package com.kush.banbah.soloprojectbackend.config.authConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * JwtService class to help manage JWT tokens
 */
@Service
public class JwtService {

    @Value("${SECRET_KEY:blank}")
    private String SECRET_KEY;


    /**
     * Extracts a claim from the provided JWT token.
     *
     * @param token The JWT token.
     * @param claimsResolver Function to resolve the claims.
     * @param <T> The type of the claim.
     * @return The resolved claim.
     */
    public <T> T extractClaim(String token, @NotNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param jwtToken The JWT token.
     * @return The username extracted from the token.
     */
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Generates a JWT token for the User.
     *
     * @param userDetails The UserDetails to generate a token for.
     * @return The generated JWT token.
     */
    public String generateToken(@NotNull UserDetails userDetails) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //seconds * min * hrs
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Checks if a JWT token is valid for the User.
     *
     * @param token The JWT token.
     * @param userDetails The UserDetails to validate the token against.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, @NotNull UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
