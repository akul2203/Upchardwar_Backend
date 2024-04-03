package com.upchardwar.app.services.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.Role;
import com.upchardwar.app.entity.User;
import com.upchardwar.app.entity.UserRole;
import com.upchardwar.app.entity.Varification;
import com.upchardwar.app.entity.payload.PasswordResetRequest;
import com.upchardwar.app.entity.payload.ResendOtpRequest;
import com.upchardwar.app.entity.payload.VarificationRequest;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.UserRepository;
import com.upchardwar.app.repository.VarificationRepository;
import com.upchardwar.app.services.IAuthService;
import com.upchardwar.app.utils.EmailServices;

@Service
public class AuthServiceImpl implements IAuthService, UserDetailsService {

	@Autowired
	private UserRepository urepo;

	@Autowired
	private EmailServices eServices;

	@Autowired
	private VarificationRepository varRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) {
	//	System.out.println(urepo.findByEmail(email).get());
		Optional<User> user = urepo.findByEmail(email);
		if(user.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);
		}
		
		System.out.println(email);
		//System.out.println(passwordEncoder.encode("1234"));

		List<GrantedAuthority> list = user.get().getUserRole().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole().getRoleName())).collect(Collectors.toList());

		//System.out.println(user.get().getPassword());
		return new org.springframework.security.core.userdetails.User(email, user.get().getPassword(), list);
	}

	
	
	public ResponseEntity<?> EmailVarification(Varification var) {
		Map<String, Object> response = new HashMap<>();
//		  
		Varification varification;

		Optional<Varification> user = this.varRepository.findByEmail(var.getEmail());

		// checking if user account is already present
		if (user.isPresent() && user.get().getIsActive()) {
			response.put(AppConstant.MESSAGE, AppConstant.USER_ALREADY_REGISTERED_WITH_EMAIL);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// checking if user account is already present and is verified or is active
		if (user.isPresent() && !user.get().getIsActive())
			varification = user.get();

		else {
			varification = new Varification();

		}

		String otp = eServices.generateOtp();
		
        varification.setRoleId(var.getRoleId());
        System.out.println(varification.getRoleId()+".......................");
		varification.setEmail(var.getEmail());
		varification.setName(var.getName());
		varification.setPassword(passwordEncoder.encode(var.getPassword()));
		varification.setOtp(otp);
		varification.setExprireTime(LocalDateTime.now().plusSeconds(2*60));

		// sending email for verification
		Boolean isSentSuccessfully = eServices.sendEmail(otp, var.getEmail());

		if (isSentSuccessfully) {

			response.put(AppConstant.MESSAGE, AppConstant.USER_REGISTRATION_SUCCESS);
			response.put(AppConstant.EMAIL_STATUS, AppConstant.VERIFICATION_EMAIL_SEND);
			response.put(AppConstant.EMAIL, var.getEmail());
			Optional<Varification> userAccount = this.varRepository.findByEmail(var.getEmail());

			if (userAccount.isPresent())
				this.varRepository.deleteById(userAccount.get().getId());

			this.varRepository.save(varification);

		} else {
			response.put(AppConstant.MESSAGE, AppConstant.VARIFICATION_FAILED);
			response.put(AppConstant.EMAIL_STATUS, AppConstant.EMAIL_SEND_STATUS_FAILED);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public Varification getVarifiedByEmailAndIsActive(String email) {
		Optional<Varification> var = varRepository.findByEmailAndIsActive(email, false);
		if (var.isEmpty())
			throw new ResourceNotFoundException(AppConstant.THIS_USER_IS_NOT_VARIFIED);

		return var.get();

	}

	@Override
	public ResponseEntity<?> verifyUser(VarificationRequest request) {
		System.err.println(request.getEmail()+"  "+request.getOtp());		
		Optional<Varification> userRegistered = this.varRepository.findByEmailAndOtp(request.getEmail(),
				request.getOtp());
		
		System.err.println(userRegistered.isPresent());
		System.out.println("test------------>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(request.getEmail());
		System.out.println(request.getOtp());
		Map<String, Object> response = new HashMap<>();
		if (userRegistered.isEmpty()) {
			response.put(AppConstant.MESSAGE, AppConstant.INVALID_OTP);
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}

		if (userRegistered.get().getIsActive()) {
			response.put(AppConstant.MESSAGE, AppConstant.USER_ALREADY_REGISTERED_WITH_EMAIL);
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}
		
		if (userRegistered.get().getExprireTime().isBefore(LocalDateTime.now())&& userRegistered.get().getOtp().equals(request.getOtp())) {
         response.put(AppConstant.MESSAGE, AppConstant.LINK_EXPIRED);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
      }

		Varification var=userRegistered.get();
		var.setIsActive(true);
		User user = new User();
	user.setName(var.getName());
	user.setEmail(var.getEmail());
	
	
	user.setPassword(var.getPassword());
	//String encPwd = passwordEncoder.encode(user.getPassword());
	user.setPassword(var.getPassword());
	Set<UserRole> roles = new HashSet();
	Role role = new Role();
	
	System.err.println(var.getRoleId());
	role.setRoleId(var.getRoleId());
	UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		roles.add(userRole);
	user.setUserRole(roles);	
	 user.setStatus(AppConstant.USER_STATUS_NOT_VARIFIED);
	 
		this.urepo.save(user);
		this.varRepository.save(userRegistered.get());
		response.put(AppConstant.MESSAGE, AppConstant.USER_VERIFICATION_SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

	}



	@Override
	public ResponseEntity<?> forgetPassword(PasswordResetRequest passwordResetRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	
public ResponseEntity<?> resendOtp(ResendOtpRequest request) {
		
	Optional<Varification> user = this.varRepository.findByEmail(request.getEmail());
		
		Map<String, Object> response = new HashMap<>();
		
		if(user.isEmpty()) {
			response.put(AppConstant.MESSAGE, AppConstant.USER_NOT_FOUND);
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
		if(user.isPresent() && user.get().getIsActive()) {
			response.put(AppConstant.MESSAGE, AppConstant.USER_ALREADY_REGISTERED_WITH_EMAIL);
			return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
		}
		String otp = eServices.generateOtp();
		Varification userRegistration = user.get();
		userRegistration.setOtp(otp);
		userRegistration.setExprireTime(LocalDateTime.now().plusSeconds(2*60));

		Boolean isSentSuccessfully = eServices.sendEmail(otp, userRegistration.getEmail());
		if (isSentSuccessfully) {

			response.put(AppConstant.MESSAGE, AppConstant.OTP_SENT_SUCCESS);
			response.put(AppConstant.EMAIL_STATUS, AppConstant.VERIFICATION_EMAIL_SEND);
			response.put(AppConstant.EMAIL, request.getEmail());
			this.varRepository.save(userRegistration);
		}else {
			response.put(AppConstant.MESSAGE, AppConstant.USER_REGISTRATION_FAILED);
			response.put(AppConstant.EMAIL_STATUS, AppConstant.EMAIL_SEND_STATUS_FAILED);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	


		

		
		
	
}
