package com.upchardwar.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.upchardwar.app.entity.payload.PatientRequest;
import com.upchardwar.app.entity.payload.PatientResponse;

public interface IPatientService {
	public PatientResponse createPatient(PatientRequest request);

	public PatientResponse getPatientByemail(String email);

	public String deletePatientById(Long id);

	public Page<PatientResponse> getAllPatient(Integer pageNo, Integer pageSize);

	public List<PatientResponse> searchPatient(Integer pageNo, Integer pageSize, PatientRequest patientRequest,
			String sortBy);

	public ResponseEntity<?> updatePatient(Long id, PatientRequest request, MultipartFile imageFile);
	
	public ResponseEntity<?> addPatient(PatientRequest request,MultipartFile file);
}
