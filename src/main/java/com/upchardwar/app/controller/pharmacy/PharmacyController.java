package com.upchardwar.app.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.entity.payload.PatientRequest;
import com.upchardwar.app.entity.payload.PatientResponse;
import com.upchardwar.app.entity.payload.PharmaRequest;
import com.upchardwar.app.entity.payload.PharmaResponse;
import com.upchardwar.app.services.pharmacy.IPharmaService;

@RestController
@RequestMapping("/upchardwar/pharmacy")
public class PharmacyController {
	
	@Autowired
	private IPharmaService pharmaService;

	@PostMapping("/save")
	public ResponseEntity<PharmaResponse> registerPharmacy(@RequestBody PharmaRequest pharmaRequest) {
	    
		return new ResponseEntity<PharmaResponse>(this.pharmaService.registerPharma(pharmaRequest),
				HttpStatus.OK);

	}
}
