package com.upchardwar.app.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.doctor.PatientAppointmentFile;

public interface PatientAppointmentFileRepository extends JpaRepository<PatientAppointmentFile, Long> {

	PatientAppointmentFile findByDoctorAndDate(Doctor doctor, LocalDate appointmentDate);

}
