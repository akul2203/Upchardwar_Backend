package com.upchardwar.app.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.upchardwar.app.entity.Varification;
import com.upchardwar.app.entity.payload.PasswordResetRequest;
import com.upchardwar.app.entity.payload.ResendOtpRequest;
import com.upchardwar.app.entity.payload.VarificationRequest;

public interface IAuthService {
	public UserDetails loadUserByUsername(String username);
	
	public ResponseEntity<?> EmailVarification(Varification var);


	public ResponseEntity<?> verifyUser(VarificationRequest request);
	
	public ResponseEntity<?> forgetPassword(PasswordResetRequest passwordResetRequest);
	
	public ResponseEntity<?> resendOtp(ResendOtpRequest request) ;
}
