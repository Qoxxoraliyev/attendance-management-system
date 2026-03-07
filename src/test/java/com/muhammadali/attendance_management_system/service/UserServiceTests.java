package com.muhammadali.attendance_management_system.service;
import com.muhammadali.attendance_management_system.dto.UserRequestDTO;
import com.muhammadali.attendance_management_system.dto.UserResponseDTO;
import com.muhammadali.attendance_management_system.enums.Role;
import com.muhammadali.attendance_management_system.exceptions.UserNotFoundException;
import com.muhammadali.attendance_management_system.model.User;
import com.muhammadali.attendance_management_system.repository.AttendanceRepository;
import com.muhammadali.attendance_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {


    @Mock
    private UserRepository userRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp(){
        user=new User();
        user.setId(1L);
        user.setFirstName("Ali");
        user.setLastName("Valiyev");
        user.setEmail("ali@gmail.com");
        user.setRole(Role.STUDENT);

        requestDTO=new UserRequestDTO(
                "Ali",
                "Valiyev",
                "ali@gmail.com",
                "123456",
                Role.STUDENT
        );

        responseDTO=new UserResponseDTO(
                1L,
                "Ali",
                "Valiyev",
                Role.STUDENT
        );
    }


    @Test
    void save_success(){

        when(passwordEncoder.encode("123456")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.save(requestDTO);

        verify(userRepository).save(any(User.class));

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.firstName()).isEqualTo("Ali");
    }


    @Test
    void update_success(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.update(1L, requestDTO);

        verify(userRepository).save(any(User.class));

        assertThat(result).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("Ali");
    }



    @Test
    void findStudents_success(){
        when(userRepository.findByRole(Role.STUDENT))
                .thenReturn(List.of(user));
        List<UserResponseDTO> result=userService.findStudents();
        assertThat(result).hasSize(1);
    }


    @Test
    void findFaculty_success(){
        user.setRole(Role.FACULTY);
        when(userRepository.findByRole(Role.FACULTY))
                .thenReturn(List.of(user));

        List<UserResponseDTO> result=userService.findByFaculty();
        assertThat(result).hasSize(1);
    }


    @Test
    void findById_notFound(){
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThatThrownBy(()->userService.findById(1L))
                .isInstanceOf(UserNotFoundException.class);
    }


    @Test
    void delete_success(){
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }


    @Test
    void delete_notFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(()->userService.delete(1L))
                .isInstanceOf(UserNotFoundException.class);
    }




}
