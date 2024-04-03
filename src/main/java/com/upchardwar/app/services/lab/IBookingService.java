package com.upchardwar.app.services.lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.razorpay.Order;
import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.lab.LabTransaction;
import com.upchardwar.app.entity.payload.BookingRequest;
import com.upchardwar.app.entity.payload.GetLabBookingRequest;
import com.upchardwar.app.entity.status.LabTestStatus;

public interface IBookingService {

	public ResponseEntity<?> BookingLabTest(BookingRequest request);
	
	public LabTransaction createTransaction(Double amount);
	
     public LabTransaction prepareTransactionDetails(Order order) ;
     
//     public ResponseEntity<?> changeStatus( Long labTestId, Long labId);
     
     public List<GetLabBookingRequest> findByLabIdAndDate(Long labId, LocalDate bookingDate);

	 public ResponseEntity<?> changeStatus(Long labTestId, Long labId, Long bookingId);
	 
	 public Long totalBooking(Long labId);
	 
	 public Long todaysTotalBookingsOfLab(Long labId);
	 
	 public Long TotalServiceProvided(Long labId);
	 
	 public Page<GetLabBookingRequest> getBookingsByPatientId(Long patientId, Pageable pageable);
}
