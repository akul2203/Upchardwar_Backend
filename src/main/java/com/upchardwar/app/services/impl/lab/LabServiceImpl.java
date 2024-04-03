package com.upchardwar.app.services.impl.lab;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.upchardwar.app.dto.PageLabDto;
import com.upchardwar.app.entity.Location;
import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.lab.LabDocument;
import com.upchardwar.app.entity.lab.PatientFavoriteLab;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.GetLabRequest;
import com.upchardwar.app.entity.payload.GetLabResponse;
import com.upchardwar.app.entity.payload.LabRequest;
import com.upchardwar.app.entity.payload.LabResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.BadRequestException;
import com.upchardwar.app.exception.ResourceAlreadyExistException;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.LabRepository;
import com.upchardwar.app.repository.LocationRepository;
import com.upchardwar.app.repository.PatientRepository;
import com.upchardwar.app.repository.UserRepository;
import com.upchardwar.app.services.IFileService;
import com.upchardwar.app.services.lab.ILabService;

@Service
public class LabServiceImpl implements ILabService {

	

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LabRepository labRepository;
	
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IFileService fileService;

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private LabReviewRatingServiceImpl impl;

	public LabResponse labToLabResponse(Lab lab) {
		return this.modelMapper.map(lab, LabResponse.class);
	}

	public Lab GetlabRequestToLab(GetLabRequest getlabRequest) {
		return this.modelMapper.map(getlabRequest, Lab.class);
	}


	public GetLabResponse labToGetLabResponse(Lab lab) {
		return this.modelMapper.map(lab, GetLabResponse.class);
	}

	public Lab labRequestToLab(LabRequest labRequest) {
		return this.modelMapper.map(labRequest, Lab.class);
	}

	public ResponseEntity<?> registerLab(LabRequest labRequest) {
		Map<String, Object> response = new HashMap<>();
		// TODO Auto-generated method stub
		Lab l = this.labRequestToLab(labRequest);
		   LabResponse lr= labToLabResponse(this.labRepository.save(l));
	      response.put(AppConstant.LAB_CREATION, lr);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	
	}

	@Override

	public LabResponse updateLab(LabRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//for searching a lab....
	@Override
	public Page<GetLabResponse> searchLab(Integer pageNo, Integer pageSize, GetLabRequest labRequest, String sortBy) {
		  Lab lab=this.GetlabRequestToLab(labRequest);
		  lab.setId(null);
		  lab.setIsApproved(null);
		  lab.setLabReviewRatings(null);
		  lab.setIsDeleted(null);
		  lab.getLocation().setId(null);
		//labRequest.setLocation(null);
		System.out.println(labRequest);
//		     // Create an ExampleMatcher with desired matching options
	        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues()
	                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Match anywhere in the string
	                .withIgnoreCase() // Ignore case when matching strings
	                .withMatcher("id", match->match.transform(value->value.map(id -> (id.equals(0L))?null:id)));
	               
	        
	        // Create an Example using the Product class and the ExampleMatcher
	     
	        
	        Example<Lab> example = Example.of(lab,exampleMatcher);
            System.err.println(example);
	        // Create a PageRequest for pagination
	        Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.Direction.ASC,sortBy);
            System.err.println(pageable);
	        // Perform the search with pagination
	    	Page<Lab> findAllLab = labRepository.findAll(example, pageable);
	    	
	    	System.err.println(findAllLab.get().toList());
	    	return findAllLab.map(this::labToGetLabResponse);
		
	}
	

  
	

//	public LabResponse registerLab(LabRequest request) {
//		Optional<Lab> s = this.labRepository.findByLabName(request.getLabName());
//
//		if (s.isPresent())
//			throw new ResourceAlreadyExistException(AppConstant.LAB_ALREADY_EXIST);
//
//		Lab l =this.labRequestToLab(request);
//		
//		User user=new User();
//		user.setName(l.getLabName());
//		user.setEmail(l.getEmail());
//		user.setPassword(l.getPassword());
//		String encPwd = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encPwd);
//		user.setPhone(l.getPhone());
//		Set<UserRole> roles=new HashSet();
//	     Role role=new Role();
//	     role.setRoleId(4L);     
//	     UserRole userRole = new UserRole();
//	     userRole.setRole(role);
//	     userRole.setUser(user);
//	     roles.add(userRole);
//        user.setUserRole(roles);
//       
//        this.userRepository.save(user);
//
//		return this.labToLabResponse(this.labRepository.save(l));
//	}
//    
//	public LabResponse getLabById(Long id) {
//
//		Optional<Lab> s = this.labRepository.findByIdAndIsapproved(true,id);
//		if (s.isPresent())
//
//			return this.labToLabResponse(s.get());
//
//		throw new ResourceNotFoundException(AppConstant.LAB_WITH_ID_NOT_EXIST);
//
//	}
//
//	@Override
	public ResponseEntity<?> deleteLabById(Long id) {
		Map<String, Object> response = new HashMap<>();

		Optional<Lab> s = this.labRepository.findById(id);

		if (s.isPresent()) {
			Lab l = s.get();
			l.setIsDeleted(true);

			System.out.println("dfghj");

			labRepository.save(l);
		} else {
			throw new ResourceNotFoundException(AppConstant.LAB_WITH_ID_NOT_EXIST);
		}

		response.put(AppConstant.MESSAGE, AppConstant.LAB_DELETE_SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

//
	//View all lab which is not deleted
		public PageLabDto viewAllLab(int pageNo, int pageSize, String sortBy) {
			
			
			// Create Pageable object with pagination and sorting
			System.out.println("inside a method ...........");
			Pageable pageable = PageRequest.of(pageNo, pageSize, Direction.ASC, sortBy);
			// Query the database directly based on doctorId
			Page<Lab> findAllLab = labRepository.findByIsDeletedAndIsApproved(pageable,false,true);

			// Convert the Page of Appointment entities to a Page of AppointmentDto
			Page<GetLabResponse> map = findAllLab.map(this::labToGetLabResponse);

			// Reverse the order of content if needed
			List<GetLabResponse> content = map.getContent();
			 for(GetLabResponse l:content) {
				 System.err.println(l.getLabName());
			 }
			List<GetLabResponse> newList = null;
			if (content != null && !content.isEmpty()) {
				newList = new ArrayList<>(content);
				Collections.reverse(newList);
			}

			// Create and return the result DTO
			PageLabDto prr = new PageLabDto();
			prr.setContents(newList);
			prr.setTotalElements(findAllLab.getTotalElements());

			return prr;
		}



	@Override
	public ResponseEntity<?> addLab(LabRequest request, MultipartFile file, List<MultipartFile> multipartFiles) {
		Map<String, Object> response = new HashMap<>();
        
		Optional<Lab> op = this.labRepository.findByEmail(request.getEmail());
		if (op.isPresent())
			throw new ResourceAlreadyExistException(AppConstant.LAB_ALREADY_EXIST);
		Lab l = this.labRequestToLab(request);
		String imageName = UUID.randomUUID().toString() + file.getOriginalFilename();
		//l.setImageName(imageName);

//		if (file != null) {
//
//			String filename = StringUtils.cleanPath(imageName);
//			l.setDocumentType(file.getContentType());
//
//			Path fileStorage = Paths.get(AppConstant.DIRECTORY, filename).toAbsolutePath().normalize();
//
//			try {
//				Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
//			} catch (IOException e) {
//
//			}
			
			
			  if (file != null) {
					String profileName= fileService.uploadFileInFolder(file, AppConstant.LAB_DIR);
					//System.err.println("IMAGE :: "+profileName);
					 l.setImageName(profileName);
				}
		
		List<LabDocument> labDocuments = new ArrayList<>();
		if (multipartFiles != null) {
			for (MultipartFile file1 : multipartFiles) {
				LabDocument l1 = new LabDocument();
				l1.setDocumentName(file.getOriginalFilename());
				l1.setDocType(file.getContentType());
				String filename = StringUtils.cleanPath(file.getOriginalFilename());
//				l1.setFileName(filename);
//				Path fileStorage = Paths.get(AppConstant.DIRECTORY, filename).toAbsolutePath().normalize();
//				try {
//					Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
//
//				} catch (Exception e) {
//
//				}
//				labDocuments.add(l1);
//			}
				StringUtils.cleanPath(file.getOriginalFilename());
				l1.setFileName(filename);
				String documentImageName= fileService.uploadFileInFolder(file, AppConstant.LAB_DOC_DIR);
				l1.setFileName(documentImageName);
				labDocuments.add(l1);

			}
				
		}

		l.setLabDocument(labDocuments);
//		
		Lab lb = this.labRepository.save(l);
		Location location = request.getLocation();
		location.setId(lb.getId());
		location = this.locationRepository.save(location);
		response.put(AppConstant.MESSAGE, AppConstant.LAB_CREATED_MESSAGE);
		

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	
	
	
	
//find lab by id:	

	public ResponseEntity<?> findLabById(Long id) {
		Map<String, Object> response = new HashMap<>();

		Lab l = labRepository.findById(id).orElseThrow(() -> new BadRequestException(AppConstant.LAB_NOT_FOUND));

		LabResponse lr = labToLabResponse(l);
		response.put(AppConstant.MESSAGE, AppConstant.LAB_FIND);

		response.put(AppConstant.LAB, l);

		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<?> findLabByUserId(Long userId) {
	    Map<String, Object> response = new HashMap<>();
	    	Lab l=	labRepository.findByUserId(userId);
	    	LabResponse lr=this.labToLabResponse(l);
	    	System.out.println(lr.getLabName());
	    	response.put(AppConstant.LAB, lr);
	    	System.out.println(response);
	    	return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	

	
	
	
	

	@Override
	public ResponseEntity<?> getLabById(Long Id) {
	    Map<String, Object> response = new HashMap<>();
	  
	    Optional<Lab> l =labRepository.findById(Id);
	    if(l.isEmpty()) {
	    	throw new ResourceNotFoundException(AppConstant.LAB_NOT_FOUND);
	    }
	    Lab lab=l.get();
	   System.err.println(lab.getBiography());
	    LabResponse lr=this.labToLabResponse(lab);
	    response.put(AppConstant.LAB, lr);
	    
		return new ResponseEntity<>(response,HttpStatus.OK);
	}


	
	
	
	
	//To make lab favorite

	public ResponseEntity<?> makeLabFav( Long labId,Long userId) {
	    Map<String, Object> response = new HashMap<>();

	    // Find the lab by ID
	    Optional<Lab> labOptional = labRepository.findById(labId);
	    if (labOptional.isEmpty()) {
	        throw new ResourceNotFoundException(AppConstant.LAB_NOT_FOUND);
	    }
	    Lab lab = labOptional.get();

	    Optional<Patient> patientOptional = patientRepository.findById(userId);
	    if (patientOptional.isEmpty()) {
	        throw new ResourceNotFoundException(AppConstant.PAITENT_NOT_FOUND);
	    }
	    Patient patient = patientOptional.get();

	    // Check if the lab is already a favorite for the patient
	    if (isLabFavoriteForPatient(lab, patient)) {
	       
	        response.put(AppConstant.MESSAGE, "Lab is already added as a favorite for this patient");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    // Proceed with making the lab a favorite
	    makeLabFavoriteForPatient(lab, patient);
    
	    response.put(AppConstant.MESSAGE, "success");
	  
	    LabResponse labResponse = this.labToLabResponse(lab);

	   
	    response.put(AppConstant.LAB, labResponse);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// already favorite ko check krane ke liye
	private boolean isLabFavoriteForPatient(Lab lab, Patient patient) {
	    
	    return patient.getFavoriteLabs().stream().anyMatch(favLab -> favLab.getLab().getId().equals(lab.getId()));
	}

	
	// this is for make lab fav
	private void makeLabFavoriteForPatient(Lab lab, Patient patient) {
	    // Create a new PatientFavoriteLab entity and add it to the patient's favorite labs list
	    PatientFavoriteLab patientFavoriteLab = new PatientFavoriteLab();
	    patientFavoriteLab.setLab(lab);
	    patientFavoriteLab.setPatient(patient);
	    patient.getFavoriteLabs().add(patientFavoriteLab);

	    // Save the changes to update the patient's favorite labs list
	    patientRepository.save(patient);
	}

	

	




}
