package com.muhammadali.attendance_management_system.repository;

import com.muhammadali.attendance_management_system.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
}
