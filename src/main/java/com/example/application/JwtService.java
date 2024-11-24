package com.example.application;

import io.jsonwebtoken.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
//@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    // Generar token con roles dinámicos
    public String generateToken(String username, List<String> roles) {
        try {
            // -------------------------------------------------------------------------------
            // HEADER
            // -------------------------------------------------------------------------------
            // The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            // Creating the Header of
            HashMap<String, Object> header = new HashMap<String, Object>();
            header.put("alg", signatureAlgorithm.toString()); // HS256
            header.put("typ", "JWT");
            // -------------------------------------------------------------------------------
            // CREATING TOKEN + ADDING HEADER
            // -------------------------------------------------------------------------------
            // Generate tokenJWT + adding header
            JwtBuilder tokenJWT = Jwts.builder()
                    .setHeader(header)
                    .claim("username", username)
                    .claim("roles", roles)
                    .issuedAt(new Date())
                    .signWith(signatureAlgorithm, secret.getBytes(StandardCharsets.UTF_8))
                    .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)));

            return tokenJWT.compact();

        } catch (Exception e) {
           // log.error("Error generating token for {}", username);
            throw  e;
        }
    }
}

