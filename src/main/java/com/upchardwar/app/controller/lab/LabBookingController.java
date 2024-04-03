package com.upchardwar.app.controller.lab;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.lab.LabTransaction;
import com.upchardwar.app.entity.payload.AppointmentRequest;
import com.upchardwar.app.entity.payload.BookingRequest;
import com.upchardwar.app.entity.payload.GetLabBookingRequest;
import com.upchardwar.app.services.lab.IBookingService;

@RestController
@RequestMapping("upchardwar/labBooking")
@CrossOrigin("*")
public class LabBookingController {

	@Autowired
	private IBookingService bookingService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> booking(@RequestBody BookingRequest request){
       System.err.println(request);
		return this.bookingService.BookingLabTest(request);
	}
	
	@GetMapping("/createTransaction/{amount}")
	public LabTransaction createTransaction(@PathVariable(name ="amount") Double amount) {
	return	bookingService.createTransaction(amount);
	}
	
	 @PutMapping("/labtests/{labTestId}/changeStatus/{labId}/{bookingId}")
	    public ResponseEntity<?> changeStatus(@PathVariable Long labTestId, @PathVariable Long labId,@PathVariable Long bookingId) {
		 return bookingService.changeStatus(labTestId, labId , bookingId);
	 }
	 
	 @GetMapping("/lab/today/{labId}")
	    public ResponseEntity<List<GetLabBookingRequest>> gettodaysBookingByLabAndDate( @PathVariable Long labId) {
	        return ResponseEntity.ok(bookingService.findByLabIdAndDate(labId, LocalDate.now()));
	    }
	 
	 @GetMapping("/totalBookig/{labId}")
	 public ResponseEntity<Long> totalBookingsOfLab(@PathVariable Long labId){
		 return new ResponseEntity<Long>(bookingService.totalBooking(labId),HttpStatus.OK);
	 }
	 
	 
	 @GetMapping("/todaysTotalBookings/{labId}")
	 public ResponseEntity<Long> totalTodaysBookingOfLab(@PathVariable Long labId){
		 return new ResponseEntity<Long>(bookingService.todaysTotalBookingsOfLab(labId),HttpStatus.OK);
	 }
	 
	 @GetMapping("/success/{labId}")
	 public ResponseEntity<Long> totalSuccessfulServices(@PathVariable Long labId){
		 return new ResponseEntity<Long>(bookingService.TotalServiceProvided(labId),HttpStatus.OK);
	 }
	 
	 @GetMapping("/patientTotalBooking/{patientId}")
	 public ResponseEntity<?> totalBookingOfPatient(@PathVariable Long patientId, Pageable pageable){
		 return new ResponseEntity<>(bookingService.getBookingsByPatientId(patientId, pageable),HttpStatus.OK);
	 }
}
