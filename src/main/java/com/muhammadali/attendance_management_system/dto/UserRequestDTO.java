package com.muhammadali.attendance_management_system.dto;


import com.muhammadali.attendance_management_system.enums.Role;

public record UserRequestDTO(

        String firstName,
        String lastName,
        String email,
        String password,
        Role role
){}
