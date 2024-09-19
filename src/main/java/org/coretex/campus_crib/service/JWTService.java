package org.coretex.campus_crib.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JWTService {

    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token,UserDetails userDetails);

    String generateRefreshToken(Map<String,Object> extractClaims, UserDetails userDetails);
}

