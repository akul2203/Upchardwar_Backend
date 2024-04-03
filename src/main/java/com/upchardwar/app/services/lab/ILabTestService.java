package com.upchardwar.app.services.lab;

import org.springframework.http.ResponseEntity;

import com.upchardwar.app.dto.PageLabDto;
import com.upchardwar.app.entity.payload.CreateLabTestRequest;
import com.upchardwar.app.entity.payload.LabTestRequest;

public interface ILabTestService {

	public  ResponseEntity<?> createLabTest(CreateLabTestRequest request);
	public PageLabDto viewAllLabTest(int pageNo, int pageSize, String sortBy, Long labId);
	public ResponseEntity<?> deleteLabTest(Long labTestId);
	public ResponseEntity<?> updateLabTest(Long labTestId,LabTestRequest request);
	 public ResponseEntity<?> getLabTestById(Long labTestId);
}
