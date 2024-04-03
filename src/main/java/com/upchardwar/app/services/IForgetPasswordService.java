package com.upchardwar.app.services;

import org.springframework.http.ResponseEntity;

import com.upchardwar.app.entity.Varification;
import com.upchardwar.app.entity.payload.PasswordResetRequest;

public interface IForgetPasswordService {

	public ResponseEntity<?> generateOtp(String email);
	
	public ResponseEntity<?> verifyUser(String email,String otp);
	
	public ResponseEntity<?> forgetPassword(PasswordResetRequest passwordResetRequest);

}
