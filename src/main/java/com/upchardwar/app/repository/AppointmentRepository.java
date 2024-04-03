package com.upchardwar.app.repository;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.status.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query("SELECT a FROM Appointment a WHERE a.timeslote.id=:aid ")
	Appointment findAllAppointmentByTimeSlotId(Long aid);
	
	@Query("SELECT COUNT(DISTINCT a.patient.id) FROM Appointment a WHERE a.doctor.id = :drid")
	 Long countPatientsByDoctorId(Long drid);

    @Query("SELECT COUNT(DISTINCT a.patient.id) FROM Appointment a WHERE a.doctor.id = :drid AND a.appointmentDate = :today")
    Long countTodaysPatientsByDoctorId(@Param("drid") Long doctorId, @Param("today") LocalDate today);
    
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :doctorId")
    Long countAppointmentsByDoctorId(Long doctorId);
    
    List<Appointment> findByDoctorId(Long doctorId);
    Page<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status, Pageable pageable);

    List<Appointment> findByPatientId(Long patientId);
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);


    List<Appointment> findByDoctorIdAndAppointmentDateAndStatus(Long doctorId, LocalDate appointmentDate, AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate >= :startDate")
    List<Appointment> findByDoctorIdAndAppointmentDateAfter(Long doctorId, LocalDate startDate);
    
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.status >= :status")
    List<Appointment> findByDoctorIdAndAppointmentStatus(Long doctorId, AppointmentStatus status);

    @Query("SELECT DISTINCT a.patient.id FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Long> findPatientIdsByDoctorId(@Param("doctorId") Long doctorId);


}




