package org.coretex.campus_crib.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.coretex.campus_crib.Exceptions.EmailAlreadyExistsException;
import org.coretex.campus_crib.dto.JwtAuthenticationResponse;
import org.coretex.campus_crib.dto.RefreshTokenRequest;
import org.coretex.campus_crib.dto.SignInRequest;
import org.coretex.campus_crib.dto.SignUpRequest;
import org.coretex.campus_crib.entities.User;
import org.coretex.campus_crib.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) throws EmailAlreadyExistsException {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignInRequest signInRequest){
        log.info("logging in");
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }


}

