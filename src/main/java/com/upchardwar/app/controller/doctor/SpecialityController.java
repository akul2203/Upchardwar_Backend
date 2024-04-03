package com.upchardwar.app.controller.doctor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.upchardwar.app.entity.doctor.Speciality;
import com.upchardwar.app.entity.payload.SpecialityRequest;
import com.upchardwar.app.entity.payload.SpecialityResponse;
import com.upchardwar.app.services.doctor.ISpecialityService;


@RestController
@RequestMapping("upchardwar/speciality")
@CrossOrigin("*")
public class SpecialityController {
    @Autowired
	private ISpecialityService specialityService;
    
    //to create speciality
    @PostMapping("/")
    public ResponseEntity<SpecialityResponse> addspeciality(@RequestBody SpecialityRequest specialityRequest){
    	return new ResponseEntity<SpecialityResponse> (this.specialityService.createSpeciality(specialityRequest),HttpStatus.CREATED);
    }
    
    //to get speciality by id
    @GetMapping("/{id}")
    public ResponseEntity<SpecialityResponse> getSpeciality(@PathVariable("id") Long id){
    	return new ResponseEntity<SpecialityResponse> (this.specialityService.getSpecialityById(id),HttpStatus.OK);
    }
    
    //to delete specific by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpeciality(@PathVariable("id") Long id){
    	return new ResponseEntity<String> (this.specialityService.deleteSpecialityById(id),HttpStatus.OK);
    }
    
    //to get all speciality
    @GetMapping("/{pageNo}/{pageSize}")
    public ResponseEntity<Page<SpecialityResponse>> getAllSpeciality(@PathVariable("pageNo") Integer pageNo,@PathVariable("pageSize") Integer pageSize){
        Page<SpecialityResponse> sr  =	this.specialityService.getAllSpeciality(pageNo, pageSize);
    	return new ResponseEntity<Page<SpecialityResponse>> (sr,HttpStatus.OK);
    }
    
    //search specific speciality
    @PostMapping("/search/{pageNo}/{pageSize}/{sortBy}")
    public ResponseEntity<List<SpecialityResponse>> search(@RequestBody SpecialityRequest request,@PathVariable("pageNo") Integer pageNo,@PathVariable("pageSize") Integer pageSize,@PathVariable("sortBy") String sortBy){
    	List<SpecialityResponse> sr=this.specialityService.searchSpeciality(pageNo, pageSize, request,sortBy);
    	return new ResponseEntity<List<SpecialityResponse>>(sr,HttpStatus.OK);
    }
    
    @PutMapping("/")
    public ResponseEntity<SpecialityResponse> update(@RequestBody SpecialityRequest request){
    	
        
    	return new ResponseEntity<SpecialityResponse>(this.specialityService.updateSpeciality(request),HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Speciality>> all(){
    	List<Speciality> sr=this.specialityService.allSpeciality();
    	return new ResponseEntity<List<Speciality>>(sr,HttpStatus.OK);
    }
    
    
    
}
