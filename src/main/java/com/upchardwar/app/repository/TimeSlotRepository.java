package com.upchardwar.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.upchardwar.app.entity.doctor.TimeSlote;

import jakarta.transaction.Transactional;

public interface TimeSlotRepository extends JpaRepository<TimeSlote, Long> {

	@Modifying
    @Transactional
	@Query("DELETE FROM TimeSlote WHERE schedule.id=:sid")
	void deleteTimeSlotsBySchduleId(Long sid);

	@Query("SELECT t FROM TimeSlote t WHERE t.schedule.id=:sid")
	List<TimeSlote> getAllTimeSlotsBySechdule(Long sid);

	void deleteByScheduleId(Long id);

	
	

}
