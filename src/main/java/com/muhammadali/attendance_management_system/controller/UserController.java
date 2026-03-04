package com.muhammadali.attendance_management_system.controller;

import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.dto.UserRequestDTO;
import com.muhammadali.attendance_management_system.dto.UserResponseDTO;
import com.muhammadali.attendance_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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


    @GetMapping("/{userId}/attendance")
    public ResponseEntity<List<AttendanceDTO>> getUserAttendance(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findByUserId(userId));
    }


    @GetMapping("/students")
    public ResponseEntity<List<UserResponseDTO>> getStudents(){
        return ResponseEntity.ok(userService.findStudents());
    }



    @GetMapping("/faculty")
    public ResponseEntity<List<UserResponseDTO>> getFaculty(){
        return ResponseEntity.ok(userService.findByFaculty());
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }



    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
