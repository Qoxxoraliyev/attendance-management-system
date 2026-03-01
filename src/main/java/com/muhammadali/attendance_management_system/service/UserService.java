package com.muhammadali.attendance_management_system.service;
import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.dto.UserRequestDTO;
import com.muhammadali.attendance_management_system.dto.UserResponseDTO;
import com.muhammadali.attendance_management_system.enums.Role;
import com.muhammadali.attendance_management_system.exceptions.UserNotFoundException;
import com.muhammadali.attendance_management_system.mapper.AttendanceMapper;
import com.muhammadali.attendance_management_system.mapper.UserMapper;
import com.muhammadali.attendance_management_system.model.User;
import com.muhammadali.attendance_management_system.repository.AttendanceRepository;
import com.muhammadali.attendance_management_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final AttendanceRepository attendanceRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AttendanceRepository attendanceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO save(UserRequestDTO dto){
        User user=UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password())); // password encode
        User saved=userRepository.save(user);
        return UserMapper.toResponse(saved);
    }


    @Transactional
    public UserResponseDTO update(Long id,UserRequestDTO dto){
        User user=getUserById(id);
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setRole(dto.role());
        User updated=userRepository.save(user);
        return UserMapper.toResponse(updated);
    }


    public List<UserResponseDTO> findStudents(){
        return userRepository.findByRole(Role.STUDENT)
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }


    public List<UserResponseDTO> findByFaculty(){
        return userRepository.findByFaculty(Role.FACULTY)
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }


    @Transactional
    public void delete(Long id){
        if (!userRepository.existsById(id)){
            throw new UserNotFoundException("User not found with id: "+id);
        }
        userRepository.deleteById(id);
    }


    public List<AttendanceDTO> findByUserId(Long userId){
        getUserById(userId);
        return attendanceRepository.findByUserId(userId)
                .stream()
                .map(AttendanceMapper::toResponse)
                .collect(Collectors.toList());
    }


    public List<UserResponseDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }




    private User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found with: "+id));
    }




}
