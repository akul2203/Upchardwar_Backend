package com.upchardwar.app.services.impl.patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.upchardwar.app.entity.User;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.PatientRequest;
import com.upchardwar.app.entity.payload.PatientResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceAlreadyExistException;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.PatientRepository;
import com.upchardwar.app.repository.UserRepository;
import com.upchardwar.app.services.IFileService;
import com.upchardwar.app.services.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IFileService fileService;


	public PatientResponse patientToPatientResponse(Patient patient) {
		return this.modelMapper.map(patient, PatientResponse.class);
	}

	public Patient patientRequestToPatient(PatientRequest patientRequest) {
		return this.modelMapper.map(patientRequest, Patient.class);
	}

	@Override
	public PatientResponse createPatient(PatientRequest request) {
		Optional<Patient> s = this.patientRepository.findByEmail(request.getEmail());

		if (s.isPresent())
			throw new ResourceAlreadyExistException(AppConstant.THIS_PATIENT_ALREADY_EXIST);

		Patient p = this.patientRequestToPatient(request);

//		User user=new User();
//		user.setName(p.getPatientName());
//		user.setEmail(p.getEmail());
//		user.setPassword(p.getPassword());
//		String encPwd = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encPwd);
//		Set<UserRole> roles=new HashSet();
//	     Role role=new Role();
//	     role.setRoleId(2L);     
//	     UserRole userRole = new UserRole();
//	     userRole.setRole(role);
//	     userRole.setUser(user);
//	     roles.add(userRole);
//        user.setUserRole(roles);
//     
//       
//        this.userRepository.save(user);

		return this.patientToPatientResponse(this.patientRepository.save(p));

	}

	@Override
	public ResponseEntity<?> addPatient(PatientRequest request, MultipartFile file) {
		Optional<User> u = this.userRepository.findByEmail(request.getEmail());
		Optional<Patient> s = this.patientRepository.findByEmail(request.getEmail());
		if(s.isEmpty()!=true) {
			Map<String, Object> r = new HashMap<>();
			r.put("already present with this email", null);
			return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
			
		}
		System.out.println();
		System.out.println(request);
//		if (u.isPresent())
//			u.get().setStatus(AppConstant.User_verified);
//		else
//			throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);
		System.out.println("hvs");
//		request.get
		Map<String, Object> response = new HashMap<>();
		Patient p = this.patientRequestToPatient(request);
	


		if (file != null) {
			
			
			
			 System.err.println(file.getOriginalFilename());
			 String documentImageName= fileService.uploadFileInFolder(file, "PATIENT PROFILE");
				System.err.println(documentImageName);
				p.setImageName(documentImageName);
			
			
		}
		response.put(AppConstant.MESSAGE, AppConstant.PATIENT_CREATED);
		PatientResponse res = this.patientToPatientResponse(this.patientRepository.save(p));
		response.put(AppConstant.PATIENT, res);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public PatientResponse getPatientByemail(String email) {
		Optional<Patient> s = this.patientRepository.findByEmail(email);

	     
	    if (s.isPresent()) {
            Patient patient = s.get();
            return patientToPatientResponse(patient);
        } else {
            throw new ResourceNotFoundException(AppConstant.PAITENT_NOT_FOUND);
        }

	}

	@Override
	public String deletePatientById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PatientResponse> getAllPatient(Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientResponse> searchPatient(Integer pageNo, Integer pageSize, PatientRequest patientRequest,
			String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public ResponseEntity<?> updatePatient(Long id, PatientRequest request, MultipartFile imageFile) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));


        existingPatient.setBloodGroup(request.getBloodGroup());
        existingPatient.setCity(request.getCity());
        existingPatient.setAddress(request.getAddress());
        existingPatient.setState(request.getState());
        existingPatient.setZipcode(request.getZipcode());
        existingPatient.setMobile(request.getMobile());
		
		
		if (imageFile != null) {
			
			
			
			 System.err.println(imageFile.getOriginalFilename());
			 String documentImageName= fileService.uploadFileInFolder(imageFile, "PATIENT PROFILE");
				System.err.println(documentImageName);
				existingPatient.setImageName(documentImageName);
			
			
		}
   
        // Save the updated patient
        Patient updatedPatient = patientRepository.save(existingPatient);

        return ResponseEntity.ok(updatedPatient);
    }

}
