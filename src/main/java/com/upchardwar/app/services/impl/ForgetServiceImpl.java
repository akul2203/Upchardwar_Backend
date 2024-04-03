package com.upchardwar.app.services.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.User;
import com.upchardwar.app.entity.Varification;
import com.upchardwar.app.entity.payload.PasswordResetRequest;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.UserRepository;
import com.upchardwar.app.repository.VarificationRepository;
import com.upchardwar.app.services.IForgetPasswordService;
import com.upchardwar.app.utils.EmailServices;

@Service
public class ForgetServiceImpl implements IForgetPasswordService {

	@Autowired
	private EmailServices emailServices;

	@Autowired
	private VarificationRepository vRepository;

	@Autowired
	private UserRepository urepo;

	// Generate Otp For Forget Password
	@Override
	public ResponseEntity<?> generateOtp(String email) {
         Map<String, Object> response=new HashMap<>();
		
        System.err.println(email);
		Optional<Varification> forg = vRepository.findByEmail(email);

		if (forg.isPresent()) {
			System.err.println(forg.get().getEmail());
			String otp = emailServices.generateOtp();
			emailServices.sendEmail(otp, email);
			Varification f = forg.get();
			f.setOtp(otp);
			f.setExprireTime(LocalDateTime.now().plusSeconds(2*60));
		 vRepository.save(f);
		 response.put(AppConstant.MESSAGE, AppConstant.OTP_SENT_SUCCESS);
		 return new ResponseEntity<>(response,HttpStatus.OK);

		} else {
			response.put(AppConstant.MESSAGE, AppConstant.USER_NOT_EXIST_WITH_EMAIL);
			 return new ResponseEntity<>(response,HttpStatus.OK);
		}

	}

	public ResponseEntity<?> verifyUser(String email, String otp) {
		System.err.println(email + " " + otp+"---------------8");
		Optional<Varification> userRegistered = this.vRepository.findByEmailAndOtp(email, otp);
		
		Map<String, Object> response = new HashMap<>();
		if (userRegistered.isEmpty()) {
			response.put(AppConstant.MESSAGE, AppConstant.INVALID_OTP);
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}

		if (userRegistered.get().getExprireTime().isBefore(LocalDateTime.now())
				&& userRegistered.get().getOtp().equals(otp)) {
			response.put(AppConstant.MESSAGE, AppConstant.LINK_EXPIRED);
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}

		this.vRepository.save(userRegistered.get());
		response.put(AppConstant.MESSAGE, AppConstant.USER_VERIFICATION_SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

	}

	public ResponseEntity<?> forgetPassword(PasswordResetRequest passwordResetRequest) {

		Optional<User> user = urepo.getByEmail(passwordResetRequest.getEmail());// Replace with your UserRepository method
		User user1 =  user.get();
		Map<String, Object> response = new HashMap<>();
		// Ensure the user is found
		if (user == null) {
			response.put(AppConstant.MESSAGE, AppConstant.USER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		}

		// Update the password
		user1.setPassword(new BCryptPasswordEncoder().encode(passwordResetRequest.getNewPassword()));
		// Save the updated user details
		urepo.save(user1);
		response.put(AppConstant.MESSAGE, AppConstant.RESET_PASSWORD_SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

	}
}
