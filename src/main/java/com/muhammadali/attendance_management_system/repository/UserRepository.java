package com.muhammadali.attendance_management_system.repository;

import com.muhammadali.attendance_management_system.enums.Role;
import com.muhammadali.attendance_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByFaculty(Role role);
}
