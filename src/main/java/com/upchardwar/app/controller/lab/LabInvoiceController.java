package com.upchardwar.app.controller.lab;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.entity.payload.DoctorInvoiceRequest;
import com.upchardwar.app.entity.payload.DoctorInvoiceResponse;
import com.upchardwar.app.entity.payload.LabInvoiceRequest;
import com.upchardwar.app.entity.payload.LabInvoiceResponse;
import com.upchardwar.app.services.doctor.IDoctorInvoiceService;
import com.upchardwar.app.services.lab.ILabInvoiceService;

@RestController
@RequestMapping("/upchardwar/invoice")
@CrossOrigin("*")
public class LabInvoiceController {

	@Autowired
	private ILabInvoiceService  labInvoiceService;

    @PostMapping("lab/create")
    public ResponseEntity<?> createDoctorInvoice(@RequestBody LabInvoiceRequest labInvoiceRequest) {
        labInvoiceRequest.setInvoiceGenerateDate(LocalDateTime.now());
        
//        System.out.println(labInvoiceRequest.getBooking()+"atcghjchvkwvedghdscwvkhv");
     	return labInvoiceService.createLabInvoice(labInvoiceRequest);
//        System.out.println(doctorInvoice+"atcghjchvkwvedghdscwvkhv");
//        System.out.println(createdInvoice);
        
       
    }
    
    @GetMapping("/get/lab/{labId}")
    public ResponseEntity<Page<LabInvoiceResponse>> getAppointmentsBylabId(
            @PathVariable Long labId, Pageable pageable) {
    	System.err.println("1");
        Page<LabInvoiceResponse> labinvoice = labInvoiceService.getInvoiceByLabId(labId, pageable);
        return ResponseEntity.ok(labinvoice);
    }
}
