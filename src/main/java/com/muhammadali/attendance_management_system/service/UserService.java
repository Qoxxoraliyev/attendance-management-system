package com.muhammadali.attendance_management_system.service;
import com.muhammadali.attendance_management_system.dto.UserRequestDTO;
import com.muhammadali.attendance_management_system.dto.UserResponseDTO;
import com.muhammadali.attendance_management_system.exceptions.UserNotFoundException;
import com.muhammadali.attendance_management_system.mapper.UserMapper;
import com.muhammadali.attendance_management_system.model.User;
import com.muhammadali.attendance_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public UserResponseDTO save(UserRequestDTO dto){
        User user=UserMapper.toEntity(dto);
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





    private User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found with: "+id));
    }




}
