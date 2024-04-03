package com.upchardwar.app.services.lab;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.upchardwar.app.dto.PageLabDto;
import com.upchardwar.app.entity.payload.GetLabRequest;
import com.upchardwar.app.entity.payload.GetLabResponse;
import com.upchardwar.app.entity.payload.LabRequest;
import com.upchardwar.app.entity.payload.LabResponse;

public interface ILabService {
	public ResponseEntity<?> registerLab(LabRequest labRequest);

	public LabResponse updateLab(LabRequest request);

 public Page<GetLabResponse> searchLab(Integer pageNo, Integer pageSize, GetLabRequest labRequest, String sortBy);
 
 public ResponseEntity<?> deleteLabById(Long id);

 
 public ResponseEntity<?> addLab(LabRequest request,MultipartFile file,List<MultipartFile> multipartFiles);
 
 public ResponseEntity<?> findLabByUserId(Long userId);
 
 
 public PageLabDto viewAllLab(int pageNo, int pageSize, String sortBy);
 
public ResponseEntity<?> getLabById(Long Id);

public ResponseEntity<?> makeLabFav(Long userId, Long labId);


 


}
