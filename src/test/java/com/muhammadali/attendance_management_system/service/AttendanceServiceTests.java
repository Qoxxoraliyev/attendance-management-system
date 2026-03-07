package com.muhammadali.attendance_management_system.service;
import com.muhammadali.attendance_management_system.dto.AttendanceDTO;
import com.muhammadali.attendance_management_system.exceptions.AttendanceNotFoundException;
import com.muhammadali.attendance_management_system.exceptions.UserNotFoundException;
import com.muhammadali.attendance_management_system.model.Attendance;
import com.muhammadali.attendance_management_system.model.User;
import com.muhammadali.attendance_management_system.repository.AttendanceRepository;
import com.muhammadali.attendance_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTests {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    private User user;
    private Attendance attendance;
    private AttendanceDTO dto;

    @BeforeEach
    void setUp(){
        user=new User();
        user.setId(1L);
        attendance=new Attendance();
        attendance.setUser(user);
        attendance.setDate(LocalDate.now());

        dto=new AttendanceDTO(
                1L,
                LocalDate.now(),
                attendance.getStatus(),
                1L
        );
    }


    @Test
    void save_success(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        AttendanceDTO result=attendanceService.save(dto);
        verify(attendanceRepository).save(any(Attendance.class));
        assertThat(result).isNotNull();
    }


    @Test
    void save_userNotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(()->attendanceService.save(dto))
                .isInstanceOf(UserNotFoundException.class);
    }



    @Test
    void update_success(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance));
        AttendanceDTO result=attendanceService.update(1L,dto);
        assertThat(result).isNotNull();
    }


    @Test
    void update_attendanceNotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(attendanceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(()->attendanceService.update(1L,dto))
                .isInstanceOf(AttendanceNotFoundException.class);
    }

    @Test
    void delete_success(){
        when(attendanceRepository.existsById(1L)).thenReturn(true);
        attendanceService.delete(1L);
        verify(attendanceRepository).deleteById(1L);
    }


    @Test
    void findAll_success(){
        when(attendanceRepository.findAll()).thenReturn(List.of(attendance));
        List<AttendanceDTO> result=attendanceService.findAll();
        assertThat(result).hasSize(1);
    }


}
