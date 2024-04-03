package com.upchardwar.app.controller.doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.payload.AppointmentRequest;
import com.upchardwar.app.entity.status.AppointmentStatus;
import com.upchardwar.app.services.doctor.IAppointmentService;

@RestController
@RequestMapping("/upchardwar/appointment")
@CrossOrigin("*")
public class Appointmentcontroller {

	@Autowired
	private IAppointmentService appointmentService;

	@PostMapping("/book")
	public synchronized ResponseEntity<Map<String, Object>> bookAppointment(@RequestBody Appointment appointmentDto) {
	    Map<String, Object> response = appointmentService.bookAppointment(appointmentDto);
	    if(response.containsKey("ERROR"))
	    {
            return ResponseEntity.status(404).body(response);
	    }
	    return ResponseEntity.ok(response);
	}
	
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Page<AppointmentRequest>> getAppointmentsByDoctorId(
            @PathVariable Long doctorId, Pageable pageable) {
    	  System.err.println("1"+pageable);
        Page<AppointmentRequest> appointments = appointmentService.getAppointmentsByDoctorId(doctorId, pageable);
        System.err.println("2");
        return  ResponseEntity.ok(appointments);
    }

    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<AppointmentRequest>> getAppointmentsByPatientId(
            @PathVariable Long patientId, Pageable pageable) {
        Page<AppointmentRequest> appointments = appointmentService.getAppointmentsByPatientId(patientId, pageable);
        return ResponseEntity.ok(appointments);
    }

    
    //todays appointment for doctor
    @GetMapping("/doctor/today/{doctorId}")
    public ResponseEntity<List<Appointment>> gettodaysAppointmentsByDoctorAndDate(
            @PathVariable Long doctorId) {	
    	LocalDate today = LocalDate.now();  
        List<Appointment> appointments = appointmentService.findAppointmentsByDoctorIdAndDate(doctorId, today);
        return ResponseEntity.ok(appointments);
    }
    
    //upcoming appointment for doctor
    @GetMapping("/doctor/{doctorId}/upcoming-appointments")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsByDoctor(
            @PathVariable Long doctorId) {
        // Get tomorrow's date
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        System.out.println(tomorrow);
        // Find upcoming appointments starting from tomorrow
        List<Appointment> upcomingAppointments = appointmentService.findUpcomingAppointmentsByDoctorId(doctorId, tomorrow);
        return ResponseEntity.ok(upcomingAppointments);
    }
    
    
//    get appointment by doctor id and status SHEDULED
    @GetMapping("/doctor/status/{doctorId}")
    public ResponseEntity<List<Appointment>> gettodaysAppointmentsByDoctorAndstatus(
            @PathVariable Long doctorId) {
    	
    	AppointmentStatus status =AppointmentStatus.SCHEDULED;
        
        List<Appointment> appointments = appointmentService.findAppointmentsByDoctorIdAndstatus(doctorId, status);
        return ResponseEntity.ok(appointments);
    }
    
    
    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId) {
        Optional<Appointment> canceledAppointment = appointmentService.cancelAppointment(appointmentId);

        if (canceledAppointment.isPresent()) {
            return ResponseEntity.ok("Appointment canceled successfully");
        } else {
            return ResponseEntity.status(404).body("Appointment not found or already canceled");
        }
    }
    
    @GetMapping("/patient/count/{drId}")
    public ResponseEntity<Long> countpatient(@PathVariable Long drId) {
       
            return ResponseEntity.ok(appointmentService.countpatient(drId));
       
    }
    
    @GetMapping("/patient/today/count/{drId}")
    public ResponseEntity<Long> counttodayspatient(@PathVariable Long drId) {
            return ResponseEntity.ok(appointmentService.counttodaypatient(drId));
       
    }
    
    @GetMapping("/patient/all/count/{drId}")
    public ResponseEntity<Long> countallappointment(@PathVariable Long drId) {
            return ResponseEntity.ok(appointmentService.countallappointmentbydrid(drId));
       
    }
}
























//import java.security.Principal;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.upchardwar.app.dto.AppointmentDto;
//import com.upchardwar.app.dto.PageAppointmentDto;
//import com.upchardwar.app.entity.doctor.TimeSlote;
//import com.upchardwar.app.entity.payload.ScheduleRequest;
//import com.upchardwar.app.services.doctor.IAppointmentService;
//import com.upchardwar.app.services.doctor.IDoctorService;
//import com.upchardwar.app.services.doctor.IScheduleService;
//import com.upchardwar.app.services.doctor.ITimeSlotService;
//
//@RestController
//@RequestMapping("/upchardwar/appointment")
//@CrossOrigin("*")
//public class Appointmentcontroller {
//
//	@Autowired
//	private ITimeSlotService timeSlotService;
//
//	@Autowired
//	private IScheduleService scheduleService;
//
//	@Autowired
//	private IDoctorService doctorService;
//
//	@Autowired
//	private IAppointmentService appointmentService;
//
//	// create schedule
////	@PostMapping("/createSchedule")
////	public ResponseEntity<Map<String, Object>> createSchdule(@RequestBody ScheduleRequest request, Principal p) {
////		System.err.println(p.getName());
////
////		// scheduleService.createSchedule( request,p.getName());
////		return new ResponseEntity<Map<String, Object>>(scheduleService.createSchedule(request, p.getName()),
////				HttpStatus.CREATED);
////
////	}
//
//	// for creating a timeslot
//	@PostMapping("/createTimeSlote")
//	public ResponseEntity<Map<String, Object>> createTimeSlote(@RequestBody TimeSlote timeSlote) {
//		Long id = timeSlote.getSchedule().getId();
//		System.out.println(timeSlote.getSchedule().getId());
//		return new ResponseEntity<Map<String, Object>>(timeSlotService.createTimeSlots(id, 60), HttpStatus.CREATED);
//	}
//
//	// to book an appointment
////	@PostMapping("/book-appointment")
////	public ResponseEntity<Map<String, Object>> bookAppointment(@RequestBody Appointment appointment) {
////		Doctor doctor = appointment.getDoctor();
////		System.out.println(doctor.toString());
////		Patient patient = appointment.getPatient();
////		System.out.println(patient.toString());
////		LocalDate appointmentDate = appointment.getAppointmentDate();
////		System.out.println(appointmentDate.toString());
////		LocalTime appointmentTime = appointment.getAppointmentTime();
////		System.out.println(appointmentTime.toString());
////
////		return new ResponseEntity<Map<String, Object>>(
////				appointmentService.bookAppointment(doctor, patient, appointmentDate, appointmentTime),
////				HttpStatus.CREATED);
////
////	}
//
//	@PostMapping("/book-appointment")
//	public ResponseEntity<Map<String, Object>> bookAppointment(@RequestBody AppointmentDto appointmentDto) {
//		return new ResponseEntity<Map<String, Object>>(appointmentService.bookAppointment(appointmentDto),
//				HttpStatus.CREATED);
//	}
//
//	// to get todays appointment
//	@GetMapping("/todaysAppointments")
//	public ResponseEntity<Map<String, Object>> getTodaysAppointment() {
//
//		return new ResponseEntity<Map<String, Object>>(appointmentService.todaysAppointment(), HttpStatus.OK);
//
//	}
//
//	@GetMapping("/upcomingAppointments")
//	public ResponseEntity<Map<String, Object>> upcomingAppointment() {
//		return new ResponseEntity<Map<String, Object>>(appointmentService.upcomingAppointment(), HttpStatus.OK);
//
//	}
//
//	// to find appointmentDetails
//	@GetMapping("/appointmentDetails/{id}")
//	public ResponseEntity<Map<String, Object>> allappointmentDetails(@PathVariable("id") long id) {
//		return new ResponseEntity<Map<String, Object>>(appointmentService.getAppointmentDetails(id), HttpStatus.OK);
//
//	}
//
//	// to cancel an appointment
//	@GetMapping("/cancelAppointment/{id}")
//	public ResponseEntity<Map<String, Object>> cancelAppointment(@PathVariable("id") long id) {
//		return new ResponseEntity<Map<String, Object>>(appointmentService.cancelAppointment(id), HttpStatus.OK);
//
//	}
//
//	@PutMapping("/updateAppointment")
//	public ResponseEntity<Map<String, Object>> updatAppointment(@RequestBody AppointmentDto appointmentDto) {
//
//		return new ResponseEntity<Map<String, Object>>(appointmentService.updateAppointment(appointmentDto),
//				HttpStatus.OK);
//
//	}
//
//	@PutMapping("/rescheduleAppointment")
//	public ResponseEntity<Map<String, Object>> rescheduleAppointment(@RequestBody AppointmentDto appointmentDto) {
//
//		return new ResponseEntity<Map<String, Object>>(appointmentService.rescheduledAppointment(appointmentDto),
//				HttpStatus.OK);
//
//	}
//
//	@GetMapping("/notify")
//	public ResponseEntity<Map<String, Object>> notifys() {
//
//		return new ResponseEntity<Map<String, Object>>(appointmentService.notifyPatientBeforeAppointment(),
//				HttpStatus.OK);
//
//	}
//	
//	//get total patient of specific doctor
//	@GetMapping("/countPatient")
//	public ResponseEntity<Map<String, Object>> totalpetient(Principal p) {
//
//		return new ResponseEntity<Map<String, Object>>(appointmentService.countTotalPatientOfDoctor(p.getName()),HttpStatus.OK);
//
//	}
//	
//	@GetMapping("/countTodaysPetient")
//	public ResponseEntity<Map<String, Object>> totalpetientOfTOdays(Principal p) {
//
//		return new ResponseEntity<Map<String, Object>>(appointmentService.countTodaysTotalPatientOfDoctor(p.getName()),HttpStatus.OK);
//
//	}
//    
//	@GetMapping("/countUpcomingAppointments")
//	public ResponseEntity<Map<String, Object>> totalupcomingappointment(Principal p) {
//
//		return new ResponseEntity<Map<String, Object>>(appointmentService.doctorsUpcomingTotalAppointment(p.getName()),HttpStatus.OK);
//
//	}
//	
////	@GetMapping("/getAllAppointment")
////	public ResponseEntity<?> getAllAppointments() {
////
////		return new ResponseEntity<>(appointmentService.getAllAppointment(),HttpStatus.OK);
////
////	}
//		
//	@PostMapping("/search/{pageNo}/{pageSize}/{sortBy}")
//	public ResponseEntity<PageAppointmentDto> getAllAppointment(@RequestBody AppointmentDto request,
//			@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
//			@PathVariable("sortBy") String sortBy) {
//     System.out.println("kuchhhhhh");
//	PageAppointmentDto pdto= this.appointmentService.viewAllAppointments(pageNo, pageSize, sortBy, request);
//	return new ResponseEntity<PageAppointmentDto>(pdto, HttpStatus.OK);
//  
//	
//	}
//	
//	//get All appointments of specific doctor
//	@GetMapping("/all/{pageNo}/{pageSize}/{sortBy}")
//	public ResponseEntity<PageAppointmentDto> getAllAppointmentOfDoctor(Principal p,
//			@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
//			@PathVariable("sortBy") String sortBy) {
//		
//		String email=p.getName();
//     System.out.println("kuchhhhhh");
//	PageAppointmentDto pdto= this.appointmentService.viewAllAppointments(pageNo, pageSize, sortBy, email);
//	return new ResponseEntity<PageAppointmentDto>(pdto, HttpStatus.OK);
//  
//	
//	}
//	
//	//get All appointments of specific patient
//	
//	@GetMapping("/all/patient/{pageNo}/{pageSize}/{sortBy}")
//	public ResponseEntity<PageAppointmentDto> getAllAppointmentOfPatient(Principal p,
//			@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
//			@PathVariable("sortBy") String sortBy) {
//		
//		String email=p.getName();
//     System.out.println("kuchhhhhh");
//	PageAppointmentDto pdto= this.appointmentService.viewAppointmentsByPatient(pageNo, pageSize, sortBy, email);
//	return new ResponseEntity<PageAppointmentDto>(pdto, HttpStatus.OK);
//  
//	
//	}
//	
//	
//}
