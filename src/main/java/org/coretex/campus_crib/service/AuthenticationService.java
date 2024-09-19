package org.coretex.campus_crib.service;


import org.coretex.campus_crib.Exceptions.EmailAlreadyExistsException;
import org.coretex.campus_crib.dto.JwtAuthenticationResponse;
import org.coretex.campus_crib.dto.RefreshTokenRequest;
import org.coretex.campus_crib.dto.SignInRequest;
import org.coretex.campus_crib.dto.SignUpRequest;
import org.coretex.campus_crib.entities.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest) throws EmailAlreadyExistsException;
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
