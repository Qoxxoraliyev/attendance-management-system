package com.muhammadali.attendance_management_system.controller;
import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }


    @PostMapping
    public ResponseEntity<AttendanceDTO> create(@RequestBody AttendanceDTO dto){
        return ResponseEntity.ok(attendanceService.save(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> update(@PathVariable Long id,@RequestBody AttendanceDTO dto){
        return ResponseEntity.ok(attendanceService.update(id,dto));
    }


    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> findAll(){
        return ResponseEntity.ok(attendanceService.findAll());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(Long id){
        attendanceService.delete(id);
        return ResponseEntity.ok("Successful");
    }


}
