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

    //TODO @Value("${jwt.secret}")
    private final String SECRET_KEY = "0752231ca83813e32b855639d94ad8b4966fcc47a563dbbbb6ca1a4d76f53285d1c5a81c2fbf7c62a53f00dbb541e1c74e9c3ebdf61c4f424938a3c8e7d79fa2367c03be6bf010784acb49734abe1415d6bbef815ac8663ddade980ca692002a4e5a75bf5d38ce59bf13e7e32a49efa66f2dc41c16ae0bd8f93daba5e87b62ffcf11a8a7d9fed11ac9a7f03a732b416759cd1cc7c329bff00b2f83ebb60cea7bbed22c8e1cd5e02e0b9105b85083b9afe2aab7dbb61aada042fe0bab18dda8eab2f64f880078ec8f1a488498986255cfa0a5dbe50faa40cdfc5251f879e1744c1e506daacc676d4d8c0996fd148462a8adcab1a20c03cbb9b55d6752227ef1f3";

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
