package com.muhammadali.attendance_management_system.dto.auth;

public record LoginResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType
){}

