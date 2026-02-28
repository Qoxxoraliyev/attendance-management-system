package com.muhammadali.attendance_management_system.controller;

import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.dto.UserRequestDTO;
import com.muhammadali.attendance_management_system.dto.UserResponseDTO;
import com.muhammadali.attendance_management_system.dto.auth.LoginRequestDTO;
import com.muhammadali.attendance_management_system.security.jwt.JwtService;
import com.muhammadali.attendance_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody LoginRequestDTO
                                                  loginRequestDTO){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.username(),
                        loginRequestDTO.password())
        );
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(loginRequestDTO.username());
        }
        else {
            throw  new UsernameNotFoundException("Invalid user request");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(Long id){
        userService.delete(id);
        return ResponseEntity.ok("successful");
    }



}
