package com.upchardwar.app.services.impl.doctor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.doctor.DoctorDocument;
import com.upchardwar.app.entity.doctor.DoctorQualification;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.DoctorRequest;
import com.upchardwar.app.entity.payload.DoctorResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceAlreadyExistException;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.AppointmentRepository;
import com.upchardwar.app.repository.DoctorRepository;
import com.upchardwar.app.repository.PatientRepository;
import com.upchardwar.app.repository.UserRepository;
import com.upchardwar.app.services.IFileService;
import com.upchardwar.app.services.doctor.IDoctorService;

@Service
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@SuppressWarnings("unused")
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@SuppressWarnings("unused")
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IFileService fileService;

	public static final String DIRECTORY = System.getProperty("user.dir") + "../src/main/resources/static/images";

	public DoctorResponse doctorToDoctorResponse(Doctor doctor) {
		return this.modelMapper.map(doctor, DoctorResponse.class);
	}

	public Doctor doctorRequestToDoctor(DoctorRequest doctorRequest) {
		return this.modelMapper.map(doctorRequest, Doctor.class);
	}


	@Override
	public ResponseEntity<?> updateDoctor(Long id, DoctorRequest request, MultipartFile imagefile) {
		Doctor existingdoctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));

		if (existingdoctor == null) {
			return null;
		}

		existingdoctor.setName(request.getName());
		existingdoctor.setCity(request.getCity());
		existingdoctor.setAddress(request.getAddress());
		existingdoctor.setState(request.getState());
		existingdoctor.setPostalcode(request.getPostalcode());
		existingdoctor.setPhone(request.getPhone());
		existingdoctor.setCountry(request.getCountry());

		if (imagefile != null) {

			System.err.println(imagefile.getOriginalFilename());
			String documentImageName = fileService.uploadFileInFolder(imagefile, "Doctor  PROFILE");
			System.err.println(documentImageName);
			existingdoctor.setImageName(documentImageName);

		}

		// Save the updated patient
		Doctor updateddoctor = doctorRepository.save(existingdoctor);

		return ResponseEntity.ok(updateddoctor);
	}

	@Override
	public DoctorResponse getDoctorById(Long id) {

		Optional<Doctor> s = this.doctorRepository.findByIdAndStatus(AppConstant.DOCTOR_STATUS_APPROVED, id);
		if (s.isPresent())

			return this.doctorToDoctorResponse(s.get());

		throw new ResourceNotFoundException(AppConstant.DOCTOR_WITH_ID_NOT_EXIST);

	}

	@Override
	public String deleteDoctorById(Long id) {
		Optional<Doctor> s = this.doctorRepository.findById(id);

		if (s.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.DOCTOR_WITH_ID_NOT_EXIST);
		}
		this.doctorRepository.delete(s.get());
		return "deleted successfully";
	}

	@Override
	public Page<DoctorResponse> getAllDoctor(Integer pageNo, Integer pageSize) {
		PageRequest page = PageRequest.of(pageNo, pageSize);
		Page<Doctor> pag = this.doctorRepository.findByStatus(AppConstant.DOCTOR_STATUS_APPROVED, page);

		return pag.map(u -> this.doctorToDoctorResponse(u));
	}

	@Override
	public List<DoctorResponse> searchDoctor(Integer pageNo, Integer pageSize, DoctorRequest doctorRequest,
			String sortBy) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Match anywhere in the string
				.withIgnoreCase() // Ignore case when matching strings
				.withMatcher("id", match -> match.transform(value -> value.map(id -> ((Integer) id == 0) ? null : id)));

		Example<Doctor> example = Example.of(doctorRequestToDoctor(doctorRequest), exampleMatcher);
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, sortBy);
		Page<Doctor> findAllDoctor = this.doctorRepository.findAll(example, pageable);
		return findAllDoctor.getContent().stream().map(s -> doctorToDoctorResponse(s)).collect(Collectors.toList());

	}

//	@Override
//	public DoctorResponse updateDoctor(DoctorRequest request) {
//
//		if (request.getStatus() == AppConstant.DOCTOR_NOT_APPROVED) {
//			throw new ResourceNotApprovedException(AppConstant.DOCTOR_NOT_APPROVED);
//		}
//		Doctor doc = this.doctorRepository.save(this.doctorRequestToDoctor(request));
//		return this.doctorToDoctorResponse(doc);
//	}

	@SuppressWarnings("unused")
	public ResponseEntity<?> addDoctor(DoctorRequest request, MultipartFile file, List<MultipartFile> multipartFiles) {
		Map<String, Object> response = new HashMap<>();
		Optional<Doctor> s = this.doctorRepository.findByEmail(request.getEmail());
		if (s.isPresent())
			throw new ResourceAlreadyExistException(AppConstant.DOCTOR_WITH_EMAIL_ALREADY_EXIST);
		Doctor d = this.doctorRequestToDoctor(request);
		String imageName = UUID.randomUUID().toString() + file.getOriginalFilename();
		d.setImageName(imageName);
		if (file != null) {
			String filename = StringUtils.cleanPath(imageName);
			d.setDocumentType(file.getContentType());
			Path fileStorage = Paths.get(AppConstant.DIRECTORY, filename).toAbsolutePath().normalize();
			try {
				Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
			}
		}
		List<DoctorDocument> doctorDocuments = new ArrayList<>();
		if (multipartFiles != null) {
			for (MultipartFile file1 : multipartFiles) {
				DoctorDocument d1 = new DoctorDocument();
				d1.setDocumentName(file.getOriginalFilename());
				d1.setDocumentType(file.getContentType());
				String filename = StringUtils.cleanPath(file.getOriginalFilename());
				d1.setFileName(filename);
				d1.setUploadDate(LocalDate.now());
				Path fileStorage = Paths.get(DIRECTORY, filename).toAbsolutePath().normalize();
				try {
					Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
				}
				doctorDocuments.add(d1);
			}
		}
		List<DoctorQualification> qualifications = new ArrayList<>();
		for (DoctorQualification qualificationRequest : request.getQualifications()) {
			DoctorQualification qualification = new DoctorQualification();
			qualification.setDegree(qualificationRequest.getDegree());
			qualification.setCollege(qualificationRequest.getCollege());
			qualification.setCompletionYear(qualificationRequest.getCompletionYear());
			qualification.setDoctor(d);
			qualifications.add(qualification);
		}
		d.setQualifications(qualifications);
		d.setDoctorDocuments(doctorDocuments);
		Doctor dr1 = this.doctorRepository.save(d);
		response.put(AppConstant.MESSAGE, AppConstant.DOCTOR_CREATED_MESSAGE);
		response.put(AppConstant.DOCTOR_CREATED, dr1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<?> getDoctorByUserId(Long userId) {
		Map<String, Object> response = new HashMap<>();
		Doctor doc = this.doctorRepository.findByUserid(userId);
		DoctorResponse drRes = doctorToDoctorResponse(doc);
		response.put(AppConstant.MESSAGE, AppConstant.DOCTOR_FOUND);
		response.put(AppConstant.DOCTOR, drRes);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public List<Doctor> filterDoctorsByKeyword(String keyword) {
		return doctorRepository.filterDoctorsByKeyword(keyword);
	}

	@Override
	public Collection<Doctor> getAllDoctorses() {
		// TODO Auto-generated method stub
		return this.doctorRepository.findAll();
	}

	@Override
	public List<Patient> mypatient(Long drid) {
		// TODO Auto-generated method stub
		List<Long> patientIdsList= appointmentRepository.findPatientIdsByDoctorId(drid);
		List<Patient> patients=patientRepository.findAllById(patientIdsList);
		return patients;	}

}
