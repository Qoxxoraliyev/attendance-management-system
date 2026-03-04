package com.muhammadali.attendance_management_system.controller;

import com.muhammadali.attendance_management_system.dto.auth.LoginRequestDTO;
import com.muhammadali.attendance_management_system.security.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticationAndGetToken(@RequestBody LoginRequestDTO loginRequestDTO){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.username(),
                        loginRequestDTO.password()
                )
        );

        if (authentication.isAuthenticated()){
            String token=jwtService.generateToken(loginRequestDTO.username());
            return ResponseEntity.ok(
                    java.util.Map.of("token", token)
            );
        }
        throw new UsernameNotFoundException("Invalid user request");
    }
}
