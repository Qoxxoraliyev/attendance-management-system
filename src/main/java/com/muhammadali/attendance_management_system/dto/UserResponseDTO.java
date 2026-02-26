package com.muhammadali.attendance_management_system.dto;

import com.muhammadali.attendance_management_system.enums.Role;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        Role role
){}
