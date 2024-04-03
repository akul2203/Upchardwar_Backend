package com.upchardwar.app.services.impl.lab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.doctor.DoctorInvoice;
import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.lab.LabInvoice;
import com.upchardwar.app.entity.payload.DoctorInvoiceResponse;
import com.upchardwar.app.entity.payload.LabInvoiceRequest;
import com.upchardwar.app.entity.payload.LabInvoiceResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.repository.LabBookingRepository;
import com.upchardwar.app.repository.LabInvoiceRepository;
import com.upchardwar.app.services.lab.ILabInvoiceService;

@Service
public class LabInvoiceServiceImpl implements ILabInvoiceService {

	@Autowired
	private LabInvoiceRepository labInvoiceRepository;

	@Autowired
	private LabBookingRepository labBookingRepository;

	public ResponseEntity<?> createLabInvoice(LabInvoiceRequest request) {
		Map<String, Object> response = new HashMap<>();
		request.getBooking().setBookingId(request.getInvoiceId());
		System.err.println(request.getInvoiceId() + ".......");

		// Ensure that the Booking entity is properly populated
		Booking booking = request.getBooking();
		if (booking == null || booking.getBookingId() == null) {
			// Handle the case where Booking entity is not properly populated
			response.put(AppConstant.MESSAGE, AppConstant.BOOKING_NOT_PROVIDED);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Check if the Booking entity is managed or not
		if (!labBookingRepository.existsById(booking.getBookingId())) {
			// If not managed, save the Booking entity first
			booking = labBookingRepository.save(booking);
		}

		// Now, save the LabInvoice entity
		LabInvoice li = new LabInvoice();
		li.setInvoiceId(request.getInvoiceId());
		li.setInvoiceGenerateDate(request.getInvoiceGenerateDate());
		li.setTotalAmount(request.getTotalAmount());
		li.setLabTest(request.getLabTest());
		li.setBooking(booking); // Set the managed Booking entity
		li.setPatient(request.getPatient());
		li.setPaymentMethod(request.getPaymentMethod());
        li.setLabId(request.getLabId());
		labInvoiceRepository.save(li); // Save the LabInvoice entity

		response.put(AppConstant.MESSAGE, AppConstant.INVOICE_CREATE_SUCCESS);
		response.put(AppConstant.LAB_INVOICE, li);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	

	
	public Page<LabInvoiceResponse> getInvoiceByLabId(Long labId, Pageable pageable) {

	pageable = PageRequest.of(0, 10, Sort.by("invoiceGenerateDate").descending());
	Page<LabInvoice> labinvoice = labInvoiceRepository.findBylabId(labId, pageable);
	Page<LabInvoiceResponse> labinvoiceresponse = setdata(labinvoice);
		return labinvoiceresponse;

	}

	private Page<LabInvoiceResponse> setdata(Page<LabInvoice> labinvoice) {
//		// TODO Auto-generated method stub
	List<LabInvoiceResponse> doctorInvoiceRespnse = labinvoice.getContent().stream().map(this::convertToResponse)
			.collect(Collectors.toList());
	return new PageImpl<>(doctorInvoiceRespnse, labinvoice.getPageable(), labinvoice.getTotalElements());
	}

	private LabInvoiceResponse convertToResponse(LabInvoice doctorInvoice) {

		LabInvoiceResponse response = new LabInvoiceResponse();

		response.setTotalAmount(doctorInvoice.getTotalAmount());
		response.setPatientId(doctorInvoice.getPatient().getId());
		// response.setAmount(doctorInvoice.getDoctor().getRate()+60);
		response.setInvoiceId(doctorInvoice.getInvoiceId());
		response.setImageName(doctorInvoice.getPatient().getImageName());
		response.setInvoiceGenerateDate(doctorInvoice.getInvoiceGenerateDate());

		response.setPatientName(doctorInvoice.getPatient().getPatientName());
		response.setTestName(doctorInvoice.getLabTest().getTestName());
		response.setPaymentMethod(doctorInvoice.getPaymentMethod());
		//response.setBookingId(doctorInvoice.getBooking().getBookingId());
		// Add more fields as needed
		return response;
	}

}
