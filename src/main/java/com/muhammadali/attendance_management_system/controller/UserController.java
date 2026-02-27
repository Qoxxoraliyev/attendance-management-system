package com.muhammadali.attendance_management_system.controller;

import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.dto.UserRequestDTO;
import com.muhammadali.attendance_management_system.dto.UserResponseDTO;
import com.muhammadali.attendance_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto){
        return ResponseEntity.ok(userService.save(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,@RequestBody UserRequestDTO dto){
        return ResponseEntity.ok(userService.update(id,dto));
    }


    @GetMapping("/{userId}")
    public List<AttendanceDTO> getUserAttendance(@PathVariable Long userId){
        return userService.findByUserId(userId);
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(Long id){
        userService.delete(id);
        return ResponseEntity.ok("successful");
    }



}
