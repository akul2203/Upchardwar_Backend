package com.upchardwar.app.controller.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.dto.PageLabDto;
import com.upchardwar.app.entity.payload.CreateLabTestRequest;
import com.upchardwar.app.entity.payload.LabTestRequest;
import com.upchardwar.app.services.lab.ILabTestService;

@RestController
@RequestMapping("upchardwar/labTest")
@CrossOrigin("*")
public class LabTestController {

	@Autowired
	private ILabTestService labTestService;
	
	@PostMapping("/save")
	public ResponseEntity<?> addLab(@RequestBody CreateLabTestRequest labTestRequest) {
	    
	    
		return this.labTestService.createLabTest(labTestRequest);
	}
	
	
	//get All LabTest of perticular labTest
	
	@GetMapping("/all/{pageNo}/{pageSize}/{sortBy}/{labId}")
	public ResponseEntity<PageLabDto> getAllLabTest(
			@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
			@PathVariable("sortBy") String sortBy,@PathVariable("labId") Long labId) {
     System.out.println("kuchhhhhh");
	PageLabDto plto= this.labTestService.viewAllLabTest(pageNo, pageSize, sortBy, labId);
	return new ResponseEntity<PageLabDto>(plto, HttpStatus.OK);
	}
	
	
	//delete labTest by Id
	@DeleteMapping("/delete/{labTestId}")
	
	public ResponseEntity<?> deleteLabTest(@PathVariable("labTestId") Long labTestId){
		System.err.println("delete lab test");
		return this.labTestService.deleteLabTest(labTestId);
	}
	
	
	//update LabTest
	@PutMapping("/update/{labTestId}")
	public ResponseEntity<?> updateLabTest(@PathVariable("labTestId") Long labTestId , @RequestBody LabTestRequest request ){
return this.labTestService.updateLabTest(labTestId, request);		
	}
	
	
	//get LabTest by id

	@GetMapping("/get/{labTestId}")
	public ResponseEntity<?> getLabTest(@PathVariable("labTestId") Long labTestId){
		return this.labTestService.getLabTestById(labTestId);
	}
}
