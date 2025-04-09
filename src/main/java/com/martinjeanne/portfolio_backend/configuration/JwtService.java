package com.martinjeanne.portfolio_backend.configuration;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    private static final long JWT_EXPIRATION = 1000 * 60 * 60; // = 1 hour

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Generate the token, also named a signed JWT (JWS)
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Jws<Claims> parseJwt(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e) {
            log.error("JWT expired or incorrect");
            return null;
        }
    }

    public String extractUsername(String token) {
        Jws<Claims> claimsJws = parseJwt(token);
        if (claimsJws == null) return null;
        return claimsJws
                .getPayload()
                .getSubject();
    }
}
