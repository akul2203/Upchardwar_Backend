package com.upchardwar.app.services.impl.lab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.upchardwar.app.dto.AppointmentDto;
import com.upchardwar.app.dto.PageAppointmentDto;
import com.upchardwar.app.dto.PageLabDto;
import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.lab.LabTest;
import com.upchardwar.app.entity.payload.CreateLabTestRequest;
import com.upchardwar.app.entity.payload.CreateLabTestResponse;
import com.upchardwar.app.entity.payload.LabTestRequest;
import com.upchardwar.app.entity.payload.LabTestResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.LabRepository;
import com.upchardwar.app.repository.LabTestRepo;
import com.upchardwar.app.services.lab.ILabTestService;

@Service
public class LabTestServiceImpl implements ILabTestService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LabTestRepo labTestRepo;
	
	
	@Autowired
	private LabRepository labRepository;
	
	
	
	public CreateLabTestResponse labTestToLabTestResponse(LabTest labTest) {
		return this.modelMapper.map(labTest, CreateLabTestResponse.class);
	}

	public LabTest creatLabTestRequestToLabTest(CreateLabTestRequest labTestRequest) {
		return this.modelMapper.map(labTestRequest, LabTest.class);
	}

	
	public LabTestResponse ltToLabTestResponse(LabTest labTest) {
		return this.modelMapper.map(labTest,LabTestResponse.class);
	}
	
	public LabTest labTestRequestToLabTest(LabTestRequest labTestRequest) {
		return this.modelMapper.map(labTestRequest, LabTest.class);
	}



	
	
	public ResponseEntity<?> createLabTest(CreateLabTestRequest request) {
	    Map<String, Object> response = new HashMap<>();
	    
	    System.err.println(request.getId());
	    LabTest labTest = this.creatLabTestRequestToLabTest(request);
	    
	    
	    Long labId = request.getLabId(); 
	    Lab lab = labRepository.findById(labId).orElse(null);

	    if (lab != null) {
	        labTest.setLab(lab); // Set the Lab for the LabTest
	        LabTest savedLabTest = labTestRepo.save(labTest);

	        // Convert saved LabTest to response format
	        CreateLabTestResponse labTestResponse = this.labTestToLabTestResponse(savedLabTest);

	        response.put(AppConstant.MESSAGE, AppConstant.LAB_TEST_CREATED);
	        response.put(AppConstant.LAB_TEST, labTestResponse);

	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } else {
	       
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_NOT_FOUND);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

       
	
	//View all labtest which is not deleted
	public PageLabDto viewAllLabTest(int pageNo, int pageSize, String sortBy, Long labId) {
		// Create Pageable object with pagination and sorting
		System.err.println("inside a method");
		Pageable pageable = PageRequest.of(pageNo, pageSize, Direction.ASC, sortBy);
		// Query the database directly based on doctorId
		Page<LabTest> findAllLabTest = labTestRepo.findByLabIdAndIsDelete(labId, pageable,false);

		System.err.println(findAllLabTest.getTotalElements());
		// Convert the Page of Appointment entities to a Page of AppointmentDto
		Page<LabTestResponse> map = findAllLabTest.map(this::ltToLabTestResponse);

		// Reverse the order of content if needed
		List<LabTestResponse> content = map.getContent();
		List<LabTestResponse> newList = null;
		if (content != null && !content.isEmpty()) {
			newList = new ArrayList<>(content);
			Collections.reverse(newList);
		}

		// Create and return the result DTO
		PageLabDto prr = new PageLabDto();
		prr.setContents(newList);
		prr.setTotalElements(findAllLabTest.getTotalElements());
    
		return prr;
	}
	
	
	
	//soft deletetion of labTest
	public ResponseEntity<?> deleteLabTest(Long labTestId){
		Map<String, Object> response =new HashMap<>();
	Optional<LabTest> labTest=	this.labTestRepo.findById(labTestId);
		if(labTest.isPresent()) {
			LabTest lt=labTest.get();
			lt.setIsDelete(true);
			labTestRepo.save(lt);
			response.put(AppConstant.MESSAGE, AppConstant.LABTEST_DELETED);
			return new ResponseEntity<>(response,HttpStatus.OK);
			
		}
		
			response.put(AppConstant.MESSAGE,AppConstant.LABTEST_NOT_FOUND);
            return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	

	public ResponseEntity<?> updateLabTest( LabTestRequest labTestRequest){
		
		return null;
	}
	
	
	//to update labTest
	
	public ResponseEntity<?> updateLabTest(Long labTestId,LabTestRequest request) {
	    Map<String, Object> response = new HashMap<>();

	   System.err.println(request);
	    
	    Optional<LabTest> labTestOptional = labTestRepo.findByIdAndIsDelete(labTestId,false);

	    if (labTestOptional.isPresent()) {
	        LabTest existingLabTest = labTestOptional.get();

	
	        existingLabTest.setTestName(request.getTestName());
	        existingLabTest.setDescription(request.getDescription());
	        existingLabTest.setAvailability(request.getAvailability());
	        existingLabTest.setRates(request.getRates());
	        
	        
	        LabTest updatedLabTest = labTestRepo.save(existingLabTest);

	       
	        CreateLabTestResponse labTestResponse = this.labTestToLabTestResponse(updatedLabTest);

	        response.put(AppConstant.MESSAGE, AppConstant.LAB_TEST_UPDATED);
	        response.put(AppConstant.LAB_TEST, labTestResponse);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_TEST_NOT_FOUND);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}
	
	
	//to get LabTest by Id
	
	public ResponseEntity<?> getLabTestById(Long labTestId){
		 Map<String, Object> response = new HashMap<>();

		   
		    Optional<LabTest> labTestOptional = labTestRepo.findByIdAndIsDelete(labTestId,false);

		    if (labTestOptional.isPresent()) {
		        LabTest existingLabTest = labTestOptional.get();
		        CreateLabTestResponse labTestResponse = this.labTestToLabTestResponse(existingLabTest);

		        response.put(AppConstant.MESSAGE, AppConstant.LAB_TEST_FOUND);
		        response.put(AppConstant.LAB_TEST, labTestResponse);
		        return new ResponseEntity<>(response, HttpStatus.OK);
		    }
		    else 
		    {
		        response.put(AppConstant.MESSAGE, AppConstant.LAB_TEST_NOT_FOUND);
		        return new ResponseEntity<>(response, HttpStatus.OK);
		    
	      }
	}
	

}
