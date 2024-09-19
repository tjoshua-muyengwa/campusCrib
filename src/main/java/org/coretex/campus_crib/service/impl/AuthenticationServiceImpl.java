package org.coretex.campus_crib.service.impl;


import lombok.RequiredArgsConstructor;
import org.coretex.campus_crib.Exceptions.EmailAlreadyExistsException;
import org.coretex.campus_crib.dto.JwtAuthenticationResponse;
import org.coretex.campus_crib.dto.RefreshTokenRequest;
import org.coretex.campus_crib.dto.SignInRequest;
import org.coretex.campus_crib.dto.SignUpRequest;
import org.coretex.campus_crib.entities.Role;
import org.coretex.campus_crib.entities.Session;
import org.coretex.campus_crib.entities.User;
import org.coretex.campus_crib.repository.SessionRepository;
import org.coretex.campus_crib.repository.UserRepository;
import org.coretex.campus_crib.service.AuthenticationService;
import org.coretex.campus_crib.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    @Qualifier("JWTService")
    private final JWTService jwtService;
    private final SessionRepository sessionRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public User signUp(SignUpRequest signUpRequest) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + signUpRequest.getEmail());
        }


        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setSecondName(signUpRequest.getLastName());
        user.setUsername_(signUpRequest.getUsername_());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("Email already exists: " + signUpRequest.getEmail(), e);
        }
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){

        Optional<Session> session = sessionRepository.findByEmail(signInRequest.getEmail());
        session.ifPresent(sessionRepository::delete);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setCode(200);
        jwtAuthenticationResponse.setName(user.getFirstName()+" "+user.getSecondName());
        jwtAuthenticationResponse.setRole(user.getRole());
        jwtAuthenticationResponse.setEmail(signInRequest.getEmail());
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setUsername_(user.getUsername_());
        jwtAuthenticationResponse.setUserId(user.getId());
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        Session session2 = new Session();
        session2.setUserId(user.getId());
        session2.setUuid(jwt);
        session2.setUsername(user.getUsername_());
        session2.setEmail(user.getEmail());
        session2.setName(user.getFirstName()+" "+user.getSecondName());
        session2.setRole(user.getRole());
        session2.setLocalDateTime(LocalDateTime.now());
        sessionRepository.save(session2);

        return jwtAuthenticationResponse;

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){



        String userMail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userMail).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;

        }
        return null;
    }


}
