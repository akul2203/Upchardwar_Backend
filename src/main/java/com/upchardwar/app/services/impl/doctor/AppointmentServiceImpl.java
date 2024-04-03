package com.upchardwar.app.services.impl.doctor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.doctor.DoctorInvoice;
import com.upchardwar.app.entity.payload.AppointmentRequest;
import com.upchardwar.app.entity.status.AppointmentStatus;
import com.upchardwar.app.repository.AppointmentRepository;
import com.upchardwar.app.repository.DoctorInvoiceRepository;
import com.upchardwar.app.services.doctor.IAppointmentService;
import com.upchardwar.app.utils.EmailServices;

@Service
@Configuration
public class AppointmentServiceImpl implements IAppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private ModelMapper modelMapper;	
	
	@Autowired
    private DoctorInvoiceRepository doctorInvoiceRepository;


//	@Override
//	public Map<String, Object> bookAppointment(Appointment appointment) {
//
//		appointment.setBookingdate(LocalDate.now());
//		appointment.setStatus(AppointmentStatus.SCHEDULED);
//		System.out.println(appointment.getBookingdate());
//		System.out.println(appointment.getPurpose());
//		// Save the appointment
//		Appointment savedAppointment = appointmentRepository.save(appointment);
//		Map<String, Object> response = new HashMap<>();
//		response.put("message", "Appointment booked successfully");
//		response.put("appointment", savedAppointment);
//		return response;
//	}

	@Override
	public Map<String, Object> bookAppointment(Appointment appointment) {

		Long apIdLong =appointment.getTimeslote().getId();
//	  Appointment appointment2=	appointmentRepository.findAllAppointmentByTimeSlotId(apIdLong);
//	  
//		if(appointment2.getTimeslote().getId().equals(appointment.getTimeslote().getId())) {
//			Map<String, Object> response1 = new HashMap<>();
//			response1.put("message", "ALERT !! Appointment Booked With this timeslote");
//			response1.put("appointment", appointment);
//			return response1;
//		}
//		
		appointment.setBookingdate(LocalDate.now());
		appointment.setStatus(AppointmentStatus.SCHEDULED);
		System.out.println(appointment.getBookingdate());
		System.out.println(appointment.getPurpose());
		// Save the appointment
		Appointment savedAppointment = appointmentRepository.save(appointment);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Appointment booked successfully");
		response.put("appointment", savedAppointment);
		return response;
	}

    @Override
    public Page<AppointmentRequest> getAppointmentsByPatientId(Long patientId, Pageable pageable) {
    	pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("appointmentDate").descending());

        Page<Appointment> appointments = appointmentRepository.findByPatientId(patientId, pageable);
        return appointments.map(this::convertToRequestDto);
    }

    @Override
    public Page<AppointmentRequest> getAppointmentsByDoctorId(Long doctorId, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("appointmentDate").descending());

        // Retrieve appointments with sorting by date
        Page<Appointment> appointments = appointmentRepository.findByDoctorIdAndStatus(doctorId, AppointmentStatus.SCHEDULED,pageable);

        // Cnvert and return the data
        return appointments.map(this::convertToRequestDto);
    }

    private AppointmentRequest convertToRequestDto(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentRequest.class);
    }

    @Override
    public List<Appointment> findAppointmentsByDoctorIdAndDate(Long doctorId, LocalDate appointmentDate) {
        return appointmentRepository.findByDoctorIdAndAppointmentDateAndStatus(doctorId, appointmentDate,AppointmentStatus.SCHEDULED);
    }
    
    @Override
    public List<Appointment> findUpcomingAppointmentsByDoctorId(Long doctorId, LocalDate startDate) {

        return appointmentRepository.findByDoctorIdAndAppointmentDateAfter(doctorId, startDate);
    }


	@Override
	public List<Appointment> findAppointmentsByDoctorIdAndstatus(Long doctorId, AppointmentStatus status) {
		// TODO Auto-generated method stub
		return appointmentRepository.findByDoctorIdAndAppointmentStatus(doctorId, status);
	}


	@Override
	public Optional<Appointment> cancelAppointment(Long appointmentId) {

		   Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);

	        if (optionalAppointment.isPresent()) {
	            Appointment appointment = optionalAppointment.get();
	            
	            // Check if the appointment is not already canceled
	            if (!AppointmentStatus.CANCELLED.equals(appointment.getStatus())) {
	                // Update the appointment status to CANCELED
	                appointment.setStatus(AppointmentStatus.CANCELLED);

	                // Save the updated appointment
	                appointmentRepository.save(appointment);

	                // Update corresponding DoctorInvoice status to REFUND
	                updateDoctorInvoiceStatus(appointment);	 
	                EmailServices emailServices = new EmailServices();
	                String subject = "Appointment Cancellation";
	                String body = "Your appointment has been canceled. We apologize for any inconvenience.";
	                boolean result = emailServices.sendEmail("otp_placeholder", "gehlotakul@gmail.com", subject, body);

	                if(result) {
	                	System.err.println("Email Sent Success");
	                }
	                return Optional.of(appointment);
	            }
	        }
        return Optional.empty();
	}
	
	private void updateDoctorInvoiceStatus(Appointment appointment) {
        Optional<DoctorInvoice> optionalDoctorInvoice = doctorInvoiceRepository.findByAppointment(appointment);
        optionalDoctorInvoice.ifPresent(doctorInvoice->{
            doctorInvoice.setInvoiceStatus("REFUNDED");
            doctorInvoice.setAmount(-doctorInvoice.getAmount());
            doctorInvoiceRepository.save(doctorInvoice);
        });
    }

	@Override
	public Long countpatient(Long drId) {
		 return appointmentRepository.countPatientsByDoctorId(drId);
		
	}

	@Override
	public Long counttodaypatient(Long drId) {

        LocalDate today = LocalDate.now();

		return appointmentRepository.countTodaysPatientsByDoctorId(drId, today);
	}

	@Override
	public Long countallappointmentbydrid(Long drId) {
		// TODO Auto-generated method stub
		return appointmentRepository.countAppointmentsByDoctorId(drId);
	}	  
}
