package com.upchardwar.app.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.status.LabStatus;
import com.upchardwar.app.entity.status.LabTestStatus;


public interface LabBookingRepository extends JpaRepository<Booking, Long> {

	
	Page<Booking> findByLabId(Long labId, Pageable pageable);
	 Optional<Booking> findByLabTestId(Long testId);


	List<Booking> findBylabIdAndBookingDate(Long labId, LocalDate bookingDate);
	Optional<Booking> findByLabTestIdAndLabIdAndBookingId(Long labTestId, Long labId, Long bookingId);
	Long countByLabId(Long labId);
	
	Long countByLabIdAndStatus(Long labId, LabTestStatus confirm);
	long countByBookingDate(LocalDate bookingDate);
	Page<Booking> findByPatientId(Long patientId, Pageable pageable);
}
