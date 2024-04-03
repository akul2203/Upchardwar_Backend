package com.upchardwar.app.services.doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.payload.AppointmentRequest;
import com.upchardwar.app.entity.status.AppointmentStatus;

public interface IAppointmentService {
	// public Map<String, Object> bookAppointment(Doctor doctor, Patient patient,
	// LocalDate appointmentDate, LocalTime appointmentTime);

	public Map<String, Object> bookAppointment(Appointment appointment);
	
    Page<AppointmentRequest> getAppointmentsByDoctorId(Long doctorId, Pageable pageable);
    
    Page<AppointmentRequest> getAppointmentsByPatientId(Long patientId, Pageable pageable);

	List<Appointment> findAppointmentsByDoctorIdAndDate(Long doctorId, LocalDate appointmentDate);

	public Optional<Appointment> cancelAppointment(Long appointmentId); 
	
	List<Appointment> findUpcomingAppointmentsByDoctorId(Long doctorId, LocalDate startDate);

	public List<Appointment> findAppointmentsByDoctorIdAndstatus(Long doctorId, AppointmentStatus status);
	
	public Long countpatient(Long drId );
	public Long counttodaypatient(Long drId );
	public Long countallappointmentbydrid(Long drId );

}
