package com.upchardwar.app.services.impl.lab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.upchardwar.app.dto.PageLabDto;
import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.lab.PatientFavoriteLab;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.GetLabResponse;
import com.upchardwar.app.entity.payload.LabRequest;
import com.upchardwar.app.entity.payload.LabResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.PatientFavLabRepository;
import com.upchardwar.app.repository.PatientRepository;
import com.upchardwar.app.services.lab.IPatientFavLabService;

@Service
public class PatientFavLabServiceImpl implements IPatientFavLabService {
@Autowired
private	PatientRepository patientRepository;

@Autowired
private ModelMapper modelMapper;

@Autowired
private PatientFavLabRepository patientFavoriteLabRepository;
	
public LabResponse labToLabResponse(Lab lab) {
	return this.modelMapper.map(lab, LabResponse.class);
}

	
// Getting All Fav Lab of perticular Patient:
	public PageLabDto getFavoriteLabsByPatientId(Long patientId, int pageNo, int pageSize, String sortBy) {
        // Create Pageable object with pagination and sorting
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        // Query the database directly based on patientId
        Page<PatientFavoriteLab> favoriteLabsPage = patientFavoriteLabRepository.findByPatientId(patientId, pageable);

        // Convert the Page of PatientFavoriteLab entities to a Page of LabDTO
        Page<LabResponse> labDTOPage = favoriteLabsPage.map(favoriteLab -> labToLabResponse(favoriteLab.getLab()));

        // Create and return the result DTO
        PageLabDto pageLabDto = new PageLabDto();
        pageLabDto.setContents(labDTOPage.getContent());
        pageLabDto.setTotalElements(labDTOPage.getTotalElements());

        return pageLabDto;
    }
	
	
	
	// remove lab from fav
	public ResponseEntity<?> removeLabFromFav(Long patientId , Long labId) {
		Map<String, Object> response = new HashMap<>();
		// TODO Auto-generated method stub
		
		 Optional<PatientFavoriteLab> optionalFavoriteLab = patientFavoriteLabRepository.findByPatientIdAndLabId(patientId , labId);

	        if (optionalFavoriteLab.isPresent()) {
	            PatientFavoriteLab patientFavoriteLab = optionalFavoriteLab.get();
	            // Remove the lab from the patient's favorite labs
	            patientFavoriteLabRepository.delete(patientFavoriteLab);
	            response.put(AppConstant.MESSAGE, AppConstant.LAB_REMOVED_FROM_FAV);
	            return new ResponseEntity<>(response,HttpStatus.OK);
	        }
	        else
	        {
	        	response.put(AppConstant.MESSAGE,AppConstant.LAB_NOT_REMOVED);
	        	 return new ResponseEntity<>(response,HttpStatus.OK);
	        }    
	}
}
