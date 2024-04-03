package com.upchardwar.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upchardwar.app.entity.doctor.Schedule;

import jakarta.transaction.Transactional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


	@Query("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId ORDER BY s.selectedDate DESC")	
	public List<Schedule> findByDoctorId(Long doctorId);

    //@Query("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId AND s.selectedDate >= CURRENT_DATE ORDER BY s.selectedDate ASC")
    @Query("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId AND s.selectedDate >= CURRENT_DATE AND s.isActive = true ORDER BY s.selectedDate ASC")
    List<Schedule> findUpcomingSchedulesByDoctor(@Param("doctorId") Long doctorId);

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.isActive = :status WHERE s.id = :scheduleId")
    int updateStatusById(Long scheduleId, Boolean status);

}
