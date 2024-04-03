package com.upchardwar.app.services.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upchardwar.app.entity.payload.DoctorInvoiceRequest;
import com.upchardwar.app.entity.payload.DoctorInvoiceResponse;

public interface IDoctorInvoiceService {


	    public DoctorInvoiceRequest createDoctorInvoice(DoctorInvoiceRequest doctorInvoiceRequest) ;
	    
	    Page<DoctorInvoiceResponse> getInvoiceByDoctorId(Long doctorId, Pageable pageable);

}
