package com.upchardwar.app.controller.doctor;

import java.time.LocalDate;

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
import com.upchardwar.app.services.doctor.IDoctorInvoiceService;

@RestController
@RequestMapping("/upchardwar/invoice")
@CrossOrigin("*")
public class DoctorInvoiceController {
	
	@Autowired
	private IDoctorInvoiceService  doctorInvoiceService;

    @PostMapping("/create")
    public DoctorInvoiceRequest createDoctorInvoice(@RequestBody DoctorInvoiceRequest doctorInvoiceRequest) {
        doctorInvoiceRequest.setInvoiceGenerateDate(LocalDate.now());
        System.out.println(doctorInvoiceRequest+"atcghjchvkwvedghdscwvkhv");
     	DoctorInvoiceRequest createdInvoiceRequest = doctorInvoiceService.createDoctorInvoice(doctorInvoiceRequest);
        return createdInvoiceRequest;
    }
    
    @GetMapping("/get/doctor/{doctorId}")
    public ResponseEntity<Page<DoctorInvoiceResponse>> getAppointmentsByDoctorId(
            @PathVariable Long doctorId, Pageable pageable) {
    	System.err.println("1");
        Page<DoctorInvoiceResponse> doctorinvoice = doctorInvoiceService.getInvoiceByDoctorId(doctorId, pageable);
        return ResponseEntity.ok(doctorinvoice);
    }
	
} 
