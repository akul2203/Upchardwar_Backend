package com.upchardwar.app.services.impl.doctor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.doctor.DoctorInvoice;
import com.upchardwar.app.entity.payload.DoctorInvoiceRequest;
import com.upchardwar.app.entity.payload.DoctorInvoiceResponse;
import com.upchardwar.app.repository.DoctorInvoiceRepository;
import com.upchardwar.app.services.doctor.IDoctorInvoiceService;

@Service
public class DoctorInvoiceServiceImpl implements IDoctorInvoiceService {


	@Autowired
	private ModelMapper modelMapper;
	
@Autowired
private DoctorInvoiceRepository doctorInvoiceRepository;


	@Override
	public DoctorInvoiceRequest createDoctorInvoice(DoctorInvoiceRequest doctorInvoiceRequest) {
		// TODO Auto-generated method stub
		 DoctorInvoice request = new DoctorInvoice();
		 request.setId(doctorInvoiceRequest.getId());
		 request.setInvoiceGenerateDate(doctorInvoiceRequest.getInvoiceGenerateDate());
		 request.setAmount(doctorInvoiceRequest.getAmount());
		 request.setDoctor(doctorInvoiceRequest.getDoctor());
		 request.setAppointment(doctorInvoiceRequest.getAppointment());
		 request.setPatient(doctorInvoiceRequest.getPatient());
		 request.setInvoiceStatus(doctorInvoiceRequest.getInvoiceStatus());
		 request.setPaymentMethod(doctorInvoiceRequest.getPaymentMethod());
		 this.doctorInvoiceRepository.save(request);
		return doctorInvoiceRequest;
	}


	@Override
	public Page<DoctorInvoiceResponse> getInvoiceByDoctorId(Long doctorId, Pageable pageable) {
		
		pageable = PageRequest.of(0, 10, Sort.by("invoiceGenerateDate").descending());
		Page<DoctorInvoice> doctorinvoice = doctorInvoiceRepository.findByDoctorId(doctorId, pageable);
		Page<DoctorInvoiceResponse> doctorinvoiceresponse =setdata(doctorinvoice);
		return doctorinvoiceresponse;
       
	
		
	}


	

    private Page<DoctorInvoiceResponse> setdata(Page<DoctorInvoice> doctorinvoice) {
		// TODO Auto-generated method stub
    	List<DoctorInvoiceResponse> doctorInvoiceRespnse = doctorinvoice.getContent().stream().map(this::convertToResponse).collect(Collectors.toList());
    	 return new PageImpl<>(doctorInvoiceRespnse,doctorinvoice.getPageable(),doctorinvoice.getTotalElements());
	}

    private DoctorInvoiceResponse convertToResponse(DoctorInvoice doctorInvoice) {
     
        DoctorInvoiceResponse response = new DoctorInvoiceResponse();
       
       response.setAmount(doctorInvoice.getAmount());
       response.setPatientId(doctorInvoice.getPatient().getId());
       response.setAmount(doctorInvoice.getDoctor().getRate()+60);
       response.setId(doctorInvoice.getId());
       response.setImageName(doctorInvoice.getPatient().getImageName());
       response.setInvoiceGenerateDate(doctorInvoice.getInvoiceGenerateDate());
       response.setInvoiceStatus(doctorInvoice.getInvoiceStatus());
       response.setPatientName(doctorInvoice.getPatient().getPatientName());
       response.setPaymentMethod(doctorInvoice.getPaymentMethod());
        // Add more fields as needed
        return response;
    }

	private DoctorInvoice convertToRequestDto(DoctorInvoice doctorInvoice) {
        return modelMapper.map(doctorInvoice, DoctorInvoice.class);
    }




   


}
