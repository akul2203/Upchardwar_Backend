package com.upchardwar.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.entity.Varification;
import com.upchardwar.app.entity.payload.PasswordResetRequest;
import com.upchardwar.app.entity.payload.VarificationRequest;
import com.upchardwar.app.services.IForgetPasswordService;

@RestController
@RequestMapping("/upchardwar/forgetpassword")
@CrossOrigin("*")
public class ForgetPasswordController {

	@Autowired
	private IForgetPasswordService forgetPasswordService;
	
	@PostMapping(value = "/generate-otp", produces = "application/json") 
	 public ResponseEntity<?> sendOtpToEmail(@RequestBody String email) {
	            System.out.println("hello at controller");
	            return   this.forgetPasswordService.generateOtp(email);
	        } 
	
	
	@PostMapping("/verify")
	 
	 public ResponseEntity<Object> verifyUser(@RequestBody VarificationRequest passwordResetRequest) {	
		 return ResponseEntity.ok(forgetPasswordService.verifyUser(passwordResetRequest.getEmail(), passwordResetRequest.getOtp()));  
	 }
	
	@PostMapping("/reset")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest){
	return	this.forgetPasswordService.forgetPassword(passwordResetRequest);
		
	}
	
	

}
