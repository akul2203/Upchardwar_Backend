package com.upchardwar.app.services.impl.lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.lab.LabInvoice;
import com.upchardwar.app.entity.lab.LabTest;
import com.upchardwar.app.entity.lab.LabTransaction;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.AppointmentRequest;
import com.upchardwar.app.entity.payload.BookingRequest;
import com.upchardwar.app.entity.payload.BookingResponse;
import com.upchardwar.app.entity.payload.GetLabBookingRequest;
import com.upchardwar.app.entity.payload.LabInvoiceResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.entity.status.LabTestStatus;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.LabBookingRepository;
import com.upchardwar.app.repository.LabRepository;
import com.upchardwar.app.repository.LabTestRepo;
import com.upchardwar.app.repository.PatientRepository;
import com.upchardwar.app.services.lab.IBookingService;

@Service
public class BookingServiceImpl implements IBookingService {
  
	@Autowired
	private LabBookingRepository labBookingRepository;
	
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private LabTestRepo labTestRepository;
	
	@Autowired
	private ModelMapper modelMapper;	
	
	@Autowired
	private LabRepository labRepository;
	
	private static final String KEY= "rzp_test_odnr6VrScjlKUB";
	
	private static final String KEY_SECRET="eKyCFd1LAi81MaQdRPV8ZVFr";
	
	private static final String CURRENCY="INR";
	
	
	
	
	public GetLabBookingRequest toLabBookingDTO(Booking booking) {
	    GetLabBookingRequest labBookingDTO = new GetLabBookingRequest();
	    labBookingDTO.setBookingId(booking.getBookingId());
	    labBookingDTO.setPatientId(booking.getPatient().getId());
	    labBookingDTO.setPatientName(booking.getPatient().getPatientName());
	    labBookingDTO.setLabTestId(booking.getLabTest().getId());
	    labBookingDTO.setTestName(booking.getLabTest().getTestName());// Assuming getId() returns lab test ID
	    labBookingDTO.setBookingDate(booking.getBookingDate());
	    labBookingDTO.setAmount(booking.getAmount());
	    labBookingDTO.setPurpose(booking.getPurpose());
	    labBookingDTO.setLabId(booking.getLabId());
	    labBookingDTO.setLabName(booking.getLabName());
	    labBookingDTO.setStatus(booking.getStatus());
	    labBookingDTO.setImageName(booking.getPatient().getImageName());

	    return labBookingDTO;
	}

	
	public LabInvoiceResponse invoiceConvertToDTO(LabInvoice invoice) {
	    LabInvoiceResponse labInvoiceDTO = new LabInvoiceResponse();
	    labInvoiceDTO.setInvoiceId(invoice.getInvoiceId());
	  // Assuming you have a getter for booking
	    labInvoiceDTO.setTotalAmount(invoice.getTotalAmount());
	    labInvoiceDTO.setInvoiceGenerateDate(invoice.getInvoiceGenerateDate());
	    labInvoiceDTO.setPaymentMethod(invoice.getPaymentMethod());
	
	 
	    return labInvoiceDTO;
	}

	
	@Override
	public ResponseEntity<?> BookingLabTest(BookingRequest request) {
		
		Map<String,Object> response =new HashMap<>();
		Patient patient = patientRepository.findById(request.getPatient().getId())
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.PAITENT_NOT_FOUND));

        LabTest labTest = labTestRepository.findById(request.getLabTest().getId())
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.LAB_NOT_FOUND));
        
        System.err.println((request.getBookingDate()));
        Booking booking = new Booking();
        booking.setPatient(patient);
        booking.setLabTest(labTest);
        booking.setAmount((request.getAmount()/100));
        booking.setPurpose(request.getPurpose());
       booking.setBookingDate(LocalDate.now());
       booking.setStatus(LabTestStatus.CONFIRM);
       booking.setLabId(request.getLabId());
       booking.setLabName(request.getLabName());
       

        // Other booking details can be set here

		labBookingRepository.save(booking);

		Long bookingId=booking.getBookingId();
		BookingResponse bRes=new BookingResponse();
		bRes.setBookingId(bookingId);
		
		response.put(AppConstant.MESSAGE, AppConstant.LAB_TEST_BOOKED);
		response.put(AppConstant.BOOKING_RESPONSE, bRes);
		return new ResponseEntity<>(response ,HttpStatus.OK);
	}

	
	public LabTransaction createTransaction(Double amount) {
		//amount
		try {
			JSONObject jsonObject=new JSONObject();
			
			jsonObject.put("amount", (amount*100));
			jsonObject.put("currency", CURRENCY);
			
			RazorpayClient razorpayClient=new RazorpayClient(KEY, KEY_SECRET);
		Order order=	razorpayClient.Orders.create(jsonObject);
		System.err.println(order);
		
		return prepareTransactionDetails(order);
			
		} catch (RazorpayException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	
	 public LabTransaction prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
		String currency = order.get("currency");
		Integer amount=order.get("amount");
		
	LabTransaction labTransaction= new LabTransaction(orderId,currency,amount);
	return labTransaction;
	}


	@Override
	public ResponseEntity<?> changeStatus(Long labTestId, Long labId,Long bookingId) {
		Map<String, Object> response=new HashMap<>();

            
        	 Optional<Booking>bookingOptional=labBookingRepository.findByLabTestIdAndLabIdAndBookingId(labTestId,labId,bookingId);
        	 if(bookingOptional.isPresent()) {
          	LabTestStatus status= bookingOptional.get().getStatus();
             switch (status) {
                 case CONFIRM:
                     status = LabTestStatus.LABPERSON_ASSIGNED;
                     bookingOptional.get().setStatus(status);
                     labBookingRepository.save(bookingOptional.get());
                     break;
                 case LABPERSON_ASSIGNED:
                     status = LabTestStatus.SAMPLE_TAKEN;
                     bookingOptional.get().setStatus(status);
                     labBookingRepository.save(bookingOptional.get());
                     break;
                 case SAMPLE_TAKEN:
                     status = LabTestStatus.REPORT_GENERATED;
                     bookingOptional.get().setStatus(status);
                     labBookingRepository.save(bookingOptional.get());
                     break;
                 case REPORT_GENERATED:
                     status = LabTestStatus.DELIVERED;
                     bookingOptional.get().setStatus(status);
                     labBookingRepository.save(bookingOptional.get());
                     break;
                 default:
                     
                     break;
                     
                 
             }
           
             response.put(AppConstant.MESSAGE, AppConstant.LAB_STATUS_CHANGE);   
             return new ResponseEntity<>(response,HttpStatus.OK);
        
	
	}
             return null;
}

	

	 
	 public List<GetLabBookingRequest> findByLabIdAndDate(Long labId, LocalDate bookingDate) {
	      List < Booking> b= labBookingRepository.findBylabIdAndBookingDate(labId, bookingDate);
	     List<GetLabBookingRequest> gb=new ArrayList<>();
	    	   for(Booking i:b) {
	    		   gb.add(toLabBookingDTO(i));
	    	   }
	      
	return gb;
}

private BookingRequest convertToRequestDto(Booking appointment) {
	
    return modelMapper.map(appointment, BookingRequest.class);
}


//to find total bookings counts
@Override
public Long totalBooking(Long labId) {
	Long count=labBookingRepository.countByLabId(labId);
	return count;
}

//to find todays total bookings counts
public Long todaysTotalBookingsOfLab(Long labId) {
	Long count =labBookingRepository.countByBookingDate(LocalDate.now());
	return count;
}


//to find total survices successfully provided
@Override
public Long TotalServiceProvided(Long labId) {
	
	Long count=labBookingRepository.countByLabIdAndStatus(labId, LabTestStatus.DELIVERED);
	return count;
}


public Page<GetLabBookingRequest> getBookingsByPatientId(Long patientId, Pageable pageable) {
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("bookingDate").descending());

    // Retrieve appointments with sorting by date
    Page<Booking> appointments = labBookingRepository.findByPatientId(patientId, pageable);

    // Convert and return the data
    return appointments.map(this::toLabBookingDTO);
}
	
}