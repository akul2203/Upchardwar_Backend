package com.upchardwar.app.services.lab;

import org.springframework.http.ResponseEntity;

import com.upchardwar.app.dto.PageLabDto;

public interface IPatientFavLabService {

	public PageLabDto getFavoriteLabsByPatientId(Long patientId, int pageNo, int pageSize, String sortBy) ;
	
	public ResponseEntity<?> removeLabFromFav(Long patientId , Long labId);
}
