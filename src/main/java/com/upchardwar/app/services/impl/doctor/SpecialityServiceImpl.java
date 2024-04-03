package com.upchardwar.app.services.impl.doctor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.doctor.Speciality;
import com.upchardwar.app.entity.payload.SpecialityRequest;
import com.upchardwar.app.entity.payload.SpecialityResponse;
import com.upchardwar.app.exception.ResourceAlreadyExistException;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.SpecialityRepository;
import com.upchardwar.app.services.doctor.ISpecialityService;


@Service
public class SpecialityServiceImpl implements ISpecialityService {
	@Autowired
	private SpecialityRepository srepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public SpecialityResponse specialityToSpecialityResponse(Speciality speciality) {
		return this.modelMapper.map(speciality,SpecialityResponse.class);
	}
	
	public Speciality specialityRequestToSpeciality(SpecialityRequest specialityRequest) {
		return this.modelMapper.map(specialityRequest, Speciality.class);
	}

	
	@Override
	public SpecialityResponse createSpeciality(SpecialityRequest specialityRequest) {
		Optional<Speciality> s=this.srepo.findBySpName(specialityRequest.getSpName());
		
		if(s.isPresent()) 
			throw new ResourceAlreadyExistException("this speciality already exist");
		
		Speciality sp=this.specialityRequestToSpeciality(specialityRequest);
		
		return this.specialityToSpecialityResponse(this.srepo.save(sp));
	}

	@Override
	public SpecialityResponse getSpecialityById(Long id) {
		Optional<Speciality>  s=this.srepo.findById(id);
		if(s.isPresent()) 
			return this.specialityToSpecialityResponse(s.get());
		
			throw new ResourceNotFoundException("speciality with this id not exist");
		
	}

	
	@Override
	public String deleteSpecialityById(Long id) {
		Optional<Speciality>  s=this.srepo.findById(id);
	    
		if(s.isEmpty()) {
			throw new ResourceNotFoundException("speciality with this id not exist");
		}
		   this.srepo.delete(s.get());	
          		return "deleted successfully";
	
	}

	@Override
	public Page<SpecialityResponse> getAllSpeciality(Integer pageNo, Integer pageSize) {
		PageRequest page = PageRequest.of(pageNo, pageSize);
	         Page<Speciality> pag = this.srepo.findAll(page);
		 return pag.map(u ->this.specialityToSpecialityResponse(u));
	}

	@Override
	public List<SpecialityResponse> searchSpeciality(Integer pageNo, Integer pageSize, SpecialityRequest speciality,String sortBy) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Match anywhere in the string
                .withIgnoreCase() // Ignore case when matching strings
                .withMatcher("id", match->match.transform(value->value.map(id->((Integer)id==0)?null:id)));
		
		Example<Speciality> example = Example.of(specialityRequestToSpeciality(speciality),exampleMatcher);
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC,sortBy);
		Page<Speciality> findAllSpeciality = this.srepo.findAll(example, pageable);
		return findAllSpeciality.getContent().stream().map(s->specialityToSpecialityResponse(s)).collect(Collectors.toList());
	
	}

	@Override
	public SpecialityResponse updateSpeciality(SpecialityRequest request) {
		     
			
			Speciality s=	this.srepo.save(specialityRequestToSpeciality(request));
			return this.specialityToSpecialityResponse(s);
		
	}

	public List<Speciality> allSpeciality(){
	   return  this.srepo.findAll();
	          
	}
	
	
	
	

	

}
