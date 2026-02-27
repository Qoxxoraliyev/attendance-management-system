package com.muhammadali.attendance_management_system.service;
import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.exceptions.AttendanceNotFoundException;
import com.muhammadali.attendance_management_system.exceptions.UserNotFoundException;
import com.muhammadali.attendance_management_system.mapper.AttendanceMapper;
import com.muhammadali.attendance_management_system.model.Attendance;
import com.muhammadali.attendance_management_system.model.User;
import com.muhammadali.attendance_management_system.repository.AttendanceRepository;
import com.muhammadali.attendance_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AttendanceDTO save(AttendanceDTO dto){
        User user=getUser(dto.userId());
        Attendance attendance= AttendanceMapper.toEntity(dto,user,dto.status());
        Attendance saved=attendanceRepository.save(attendance);
        return AttendanceMapper.toResponse(saved);
    }


    @Transactional
    public AttendanceDTO update(Long id,AttendanceDTO dto){
        User user=getUser(dto.userId());
        Attendance attendance=getAttendance(id);
        attendance.setDate(dto.date());
        attendance.setStatus(dto.status());
        attendance.setUser(user);
        return AttendanceMapper.toResponse(attendance);
    }


    @Transactional
    public void delete(Long id){
        if (!attendanceRepository.existsById(id)){
            throw new AttendanceNotFoundException("Attendance not found with id: "+id);
        }
        attendanceRepository.deleteById(id);
    }


    public List<AttendanceDTO> findAll(){
        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceMapper::toResponse)
                .collect(Collectors.toList());
    }


    private Attendance getAttendance(Long id){
        return attendanceRepository.findById(id)
                .orElseThrow(()->new AttendanceNotFoundException("Attendance not found with id: "+id));
    }


    private User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found with id: "+id));
    }




}
