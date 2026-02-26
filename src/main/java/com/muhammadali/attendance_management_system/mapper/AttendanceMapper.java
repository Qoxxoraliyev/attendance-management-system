package com.muhammadali.attendance_management_system.mapper;

import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.enums.AttendanceStatus;
import com.muhammadali.attendance_management_system.model.Attendance;
import com.muhammadali.attendance_management_system.model.User;

public class AttendanceMapper {

    public static Attendance toEntity(AttendanceDTO dto, User user, AttendanceStatus status){
        Attendance attendance=new Attendance();
        attendance.setDate(dto.date());
        attendance.setStatus(dto.status());
        attendance.setUser(user);
        return attendance;
    }


    public static AttendanceDTO toResponse(Attendance attendance){
        return new AttendanceDTO(
                attendance.getId(),
                attendance.getDate(),
                attendance.getStatus(),
                attendance.getUser().getId()
        );
    }



}
