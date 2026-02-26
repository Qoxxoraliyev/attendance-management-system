package com.muhammadali.attendance_management_system.dto;

import com.muhammadali.attendance_management_system.enums.AttendanceStatus;

import java.time.LocalDate;

public record AttendanceDTO(
        Long id,
        LocalDate date,
        AttendanceStatus status,
        Long userId
) {}
