package com.upchardwar.app.services.impl.pharma;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.Role;
import com.upchardwar.app.entity.User;
import com.upchardwar.app.entity.UserRole;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.PatientRequest;
import com.upchardwar.app.entity.payload.PatientResponse;
import com.upchardwar.app.entity.payload.PharmaRequest;
import com.upchardwar.app.entity.payload.PharmaResponse;
import com.upchardwar.app.entity.pharma.Pharmacy;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceAlreadyExistException;
import com.upchardwar.app.repository.PharmaRepository;
import com.upchardwar.app.repository.UserRepository;
import com.upchardwar.app.services.pharmacy.IPharmaService;

@Service
public class PharmaServiceImpl implements IPharmaService {
    
	
	@Autowired
	private PharmaRepository pharmaRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	

	public PharmaResponse pharmaToPharmaResponse(Pharmacy pharmacy) {
		return this.modelMapper.map(pharmacy, PharmaResponse.class);
	}

	public Pharmacy pharmaRequestToPharma(PharmaRequest pharmaRequest) {
		return this.modelMapper.map(pharmaRequest, Pharmacy.class);
	}
	@Override
	public PharmaResponse registerPharma(PharmaRequest pharmaRequest) {
		
		Optional<Pharmacy> s = this.pharmaRepository.findByEmail(pharmaRequest.getEmail());

		if (s.isPresent())
			throw new ResourceAlreadyExistException(AppConstant.PHARMA_WITH_EMAIL_ALREADY_EXIST);

		Pharmacy p =this.pharmaRequestToPharma(pharmaRequest);
		
		User user=new User();
		user.setName(p.getPharmaName());
		user.setEmail(p.getEmail());
		user.setPassword(p.getPassword());
		String encPwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(encPwd);
		
		Set<UserRole> roles=new HashSet();
	     Role role=new Role();
	     role.setRoleId(5L);     
	     UserRole userRole = new UserRole();
	     userRole.setRole(role);
	     userRole.setUser(user);
	     roles.add(userRole);
        user.setUserRole(roles);
       
       
        this.userRepository.save(user);
      		
		

		return this.pharmaToPharmaResponse(this.pharmaRepository.save(p));
	}

}
