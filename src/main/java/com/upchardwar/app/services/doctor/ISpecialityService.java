package com.upchardwar.app.services.doctor;

import java.util.List;

import org.springframework.data.domain.Page;

import com.upchardwar.app.entity.doctor.Speciality;
import com.upchardwar.app.entity.payload.SpecialityRequest;
import com.upchardwar.app.entity.payload.SpecialityResponse;

public interface ISpecialityService {
	
	public SpecialityResponse createSpeciality(SpecialityRequest specialityRequest );
	
	public SpecialityResponse getSpecialityById(Long id);
	
	public String deleteSpecialityById(Long id);
	
	public Page<SpecialityResponse> getAllSpeciality(Integer pageNo, Integer pageSize);
	
	public List<SpecialityResponse> searchSpeciality(Integer pageNo,Integer pageSize,SpecialityRequest speciality,String sortBy);
	
	public SpecialityResponse updateSpeciality(SpecialityRequest request);
	public List<Speciality> allSpeciality();
}
