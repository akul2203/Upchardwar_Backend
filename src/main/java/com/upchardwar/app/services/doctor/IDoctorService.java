package com.upchardwar.app.services.doctor;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.DoctorRequest;
import com.upchardwar.app.entity.payload.DoctorResponse;


public interface IDoctorService {
	public DoctorResponse doctorToDoctorResponse(Doctor doctor);
	
	public Doctor doctorRequestToDoctor(DoctorRequest doctorRequest);
	

	public ResponseEntity<?> updateDoctor(Long id,DoctorRequest request , MultipartFile imagefile);

	public DoctorResponse getDoctorById(Long id);

	public String deleteDoctorById(Long id);

	public Page<DoctorResponse> getAllDoctor(Integer pageNo, Integer pageSize);

	public List<DoctorResponse> searchDoctor(Integer pageNo, Integer pageSize, DoctorRequest doctorRequest,
			String sortBy);


	public ResponseEntity<?> addDoctor(DoctorRequest request,MultipartFile file,List<MultipartFile> multipartFiles);

	public ResponseEntity<?> getDoctorByUserId(Long userId);

	List<Doctor> filterDoctorsByKeyword(String keyword);

	public Collection<Doctor> getAllDoctorses();
	
	public List<Patient> mypatient(Long drid);


	
}
