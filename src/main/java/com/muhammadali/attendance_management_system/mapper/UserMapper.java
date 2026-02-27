package com.muhammadali.attendance_management_system.mapper;

import com.muhammadali.attendance_management_system.dto.UserRequestDTO;
import com.muhammadali.attendance_management_system.dto.UserResponseDTO;
import com.muhammadali.attendance_management_system.model.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto){
        User user=new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        return user;
    }


    public static UserResponseDTO toResponse(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }


}
