package com.upchardwar.app.services.impl.lab;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.Varification;
import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.lab.LabReviewRating;
import com.upchardwar.app.entity.lab.LabReviewReply;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.payload.LabReviewRatingRequest;
import com.upchardwar.app.entity.payload.LabReviewRatingResponse;
import com.upchardwar.app.entity.payload.LabReviewReplayRequest;
import com.upchardwar.app.entity.payload.LabReviewReplayResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.repository.LabRepository;
import com.upchardwar.app.repository.LabReviewRatingRepo;
import com.upchardwar.app.repository.LabReviewReplyRepository;
import com.upchardwar.app.repository.LabReviewRepository;
import com.upchardwar.app.repository.PatientRepository;
import com.upchardwar.app.repository.VarificationRepository;
import com.upchardwar.app.services.lab.ILabReviewRating;

@Service
public class LabReviewRatingServiceImpl implements ILabReviewRating {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LabReviewRatingRepo labReviewRatingRepository;

	@Autowired
	private LabReviewRepository reviewRepository;

	@Autowired
	private LabReviewReplyRepository replyRepository;

	@Autowired
	private LabRepository labRepository;

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private VarificationRepository varificationRepository;

	public LabReviewRatingResponse labReviewRatingToResponse(LabReviewRating labReviewRating) {
		return this.modelMapper.map(labReviewRating, LabReviewRatingResponse.class);
	}

	public LabReviewRating requestToLabReviewRating(LabReviewRatingRequest request) {
		return this.modelMapper.map(request, LabReviewRating.class);
	}

	@Override
	public Map<String, Object> addReview(LabReviewRatingRequest request) {
		Map<String, Object> response = new HashMap<>();

		Optional<Lab> lab = labRepository.findById(request.getLabId());
		if (lab.isPresent()) {

			Optional<LabReviewRating> anyMatch = lab.get().getLabReviewRatings().parallelStream()
					.filter(obj -> obj.getPatient().getId() == request.getPatientId()).findFirst();
			if (anyMatch.isEmpty()) {
				LabReviewRating reviewRating = new LabReviewRating();
				reviewRating.setLab(lab.get());
				
				reviewRating.setRating(request.getRating());
				reviewRating.setPatient(patientRepository.findById(request.getPatientId()).get());
				reviewRating.setDescription(request.getDescription());
				reviewRating.setCreateTime(LocalDateTime.now());
				
				lab.get().getLabReviewRatings().add(reviewRating);
				System.err.println(lab.get().getLabReviewRatings());
				lab.get().setRating(calculateAverageRatingOfLab(lab.get().getId()).getBody() );
				System.err.println(lab.get().getRating());
				labRepository.save(lab.get());
				LabReviewRatingResponse lr = convertToResponse(labReviewRatingRepository.save(reviewRating));
				response.put(AppConstant.LAB_REVIEW_RATING, lr);
			} else {
				LabReviewRating rating = anyMatch.get();
				rating.setRating(request.getRating());
				rating.setPatient(patientRepository.findById(request.getPatientId()).get());
				rating.setDescription(request.getDescription());
				rating.setCreateTime(LocalDateTime.now());
				lab.get().setRating(calculateAverageRatingOfLab(lab.get().getId()).getBody() );
				LabReviewRating save = labReviewRatingRepository.save(rating);
				response.put("message", "updated Successfully");
				response.put("data", save);

			}
		} else {
			response.put("message", "Lab  not found.");
		}

		return response;
	}

	public LabReviewRatingResponse convertToResponse(LabReviewRating reviewRating) {
		LabReviewRatingResponse doctorReviewRatingResponse = new LabReviewRatingResponse();
		doctorReviewRatingResponse.setId(reviewRating.getId());
		doctorReviewRatingResponse.setPatientId(reviewRating.getPatient().getId());
		doctorReviewRatingResponse.setLabId(reviewRating.getLab().getId()); // Assuming you have a doctor field in
																			// LabReviewRating
		doctorReviewRatingResponse.setRating(reviewRating.getRating());
		doctorReviewRatingResponse.setDescription(reviewRating.getDescription()); // Assuming description is the review
		doctorReviewRatingResponse.setPatientName(reviewRating.getPatient().getPatientName()); // Assuming name is a
																								// field in Patient
		doctorReviewRatingResponse.setImageName(reviewRating.getPatient().getImageName());
		doctorReviewRatingResponse.setReplyResponse(
				reviewRating.getReplies().parallelStream().map(obj -> convertToReplyResponse(obj)).toList());
		return doctorReviewRatingResponse;
	}

	public LabReviewReplayResponse convertToReplyResponse(LabReviewReply reviewRating) {
		LabReviewReplayResponse doctorReviewRatingResponse = new LabReviewReplayResponse();

		doctorReviewRatingResponse.setPatientId(reviewRating.getPatient().getId());
		// Assuming you have a doctor field in LabReviewRating
		doctorReviewRatingResponse.setId(reviewRating.getId());
		doctorReviewRatingResponse.setDescription(reviewRating.getDescription()); // Assuming description is the review
		doctorReviewRatingResponse.setPatientName(reviewRating.getPatient().getPatientName()); // Assuming name is a
																						// field in Patient
		doctorReviewRatingResponse.setImageName(reviewRating.getPatient().getImageName());
		doctorReviewRatingResponse.setReviewRatingId(reviewRating.getReviewRating().getId());
	
		return doctorReviewRatingResponse;
	}

	public Map<String, Object> addReviewReplly(LabReviewReplayRequest request) {
		Map<String, Object> response = new HashMap<>();

		Optional<LabReviewRating> optionalLabReviewRating = labReviewRatingRepository
				.findById(request.getReviewRatingId());

		System.err.println(optionalLabReviewRating.get().getPatient().getPatientName());
		if (optionalLabReviewRating.isPresent()) {
			// LabReviewRating reviewRating = new LabReviewRating();
			LabReviewReply labReviewReply = new LabReviewReply();

			labReviewReply.setPatient(patientRepository.findById(request.getPatientId()).get());
			labReviewReply.setDescription(request.getDescription());
			labReviewReply.setCreateTime(LocalDateTime.now());
			labReviewReply.setReviewRating(optionalLabReviewRating.get());

			LabReviewReply save = replyRepository.save(labReviewReply);
			optionalLabReviewRating.get().getReplies().add(save);
			labReviewRatingRepository.save(optionalLabReviewRating.get());
			LabReviewReplayResponse lr = convertToReplyResponse(save);
			response.put(AppConstant.MESSAGE, lr);

		} else {
			response.put(AppConstant.MESSAGE, AppConstant.LAB_NOT_FOUND);
		}

		return response;
	}

	public ResponseEntity<?> getLabReviewRatingById(Long id) {
		Optional<LabReviewRating> optionalLabReviewRating = labReviewRatingRepository.findById(id);
		if (optionalLabReviewRating.get() != null) {
			return new ResponseEntity<>(convertToResponse(optionalLabReviewRating.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Lab Review Rating not found", HttpStatus.NOT_FOUND);
		}
	}

	// get All ReviewRating By labId
	public ResponseEntity<?> getAllRatingOfLab(long labId) {
		Map<String, Object> response = new HashMap<>();
		List<LabReviewRating> labReviewRatings = labReviewRatingRepository.findByLabId(labId);
		if (labReviewRatings.isEmpty()) {
			response.put(AppConstant.MESSAGE, "this lab has no review yet");
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		} else {
			List<LabReviewRatingResponse> lr = labReviewRatings.stream().map(this::convertToResponse)
					.collect(Collectors.toList());
			response.put(AppConstant.RATINGS, lr);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@Override
	 public ResponseEntity<?> deleteReview(Long id, String email) {
	 
	     Map<String, Object> response = new HashMap<>();
	     if (email == null) {
	         response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	         return new ResponseEntity<>(response, HttpStatus.OK);
	     }

	     Optional<Patient> patientOptional = patientRepository.findByEmail(email);
	     if (patientOptional.isPresent()) {
	         Patient patient = patientOptional.get();
	         Optional<LabReviewRating> labReviewRatingOptional = labReviewRatingRepository.findByIdAndPatientId(id, patient.getId());
	         
	         if (labReviewRatingOptional.isPresent()) {
	             labReviewRatingRepository.delete(labReviewRatingOptional.get());
	             response.put(AppConstant.MESSAGE, "Lab review deleted successfully");
	             return new ResponseEntity<>(response, HttpStatus.OK);
	         } else {
	             response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	             return new ResponseEntity<>(response, HttpStatus.OK);
	         }
	     } else {
	         response.put(AppConstant.MESSAGE, AppConstant.PAITENT_NOT_FOUND);
	         return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	     }
	 }

	
	//for deleting reply  (this method is not in working)
	public ResponseEntity<?> deleteReply(Long id , String email, Long reviewId)	{
		 Map<String, Object> response = new HashMap<>();
		 if (email == null) {
	         response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	         return new ResponseEntity<>(response, HttpStatus.OK);
	     }
		 Optional<Patient> patientOptional = patientRepository.findByEmail(email);
	     if (patientOptional.isPresent()) {
		 Optional<LabReviewReply> lr= replyRepository.findByIdAndPatientIdAndReviewRatingId(id,patientOptional.get().getId(),reviewId);
		 if(lr.isPresent()) {
			 replyRepository.delete(lr.get());
			 response.put(AppConstant.MESSAGE, "Reply deleted successfully");
             return new ResponseEntity<>(response, HttpStatus.OK);
		 }
		 else {
             response.put(AppConstant.MESSAGE, AppConstant.LAB_REPLY_STATUS);
             return new ResponseEntity<>(response, HttpStatus.OK);
		 }
	     }
		 else {
	         response.put(AppConstant.MESSAGE, AppConstant.PAITENT_NOT_FOUND);
	         return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	
	     }
	    
		}
	
	

	public ResponseEntity<?> deleteReview1(Long id, String email) {
	    Map<String, Object> response = new HashMap<>();

	    if (email == null) {
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    Optional<Varification> var = varificationRepository.findByEmailAndIsActive(email, true);
	    if (var.isPresent()) {
	        Varification v1 = var.get();
	        Long roleId = v1.getRoleId();

	        switch (roleId.intValue()) {
	            case 1: // Admin Role
	                return deleteReviewAsAdmin(id, response);
	            case 4: // Lab Role
	                return deleteReviewAsLab(id, email, response);
	            case 3: // Patient Role
	                return deleteReviewAsPatient(id, email, response);
	            default:
	                response.put(AppConstant.MESSAGE, AppConstant.INVALID_ROLE);
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.INVALID_ROLE);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	}

	private ResponseEntity<?> deleteReviewAsAdmin(Long id, Map<String, Object> response) {
	    // Admin can delete any review
	    Optional<LabReviewRating> labReviewRatingOptional = labReviewRatingRepository.findById(id);
	    if (labReviewRatingOptional.isPresent()) {
	        labReviewRatingRepository.delete(labReviewRatingOptional.get());
	        response.put(AppConstant.MESSAGE, "Lab review deleted successfully");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	private ResponseEntity<?> deleteReviewAsLab(Long id, String email, Map<String, Object> response) {
	    // Lab can delete only their own assigned reviews
	    Optional<Lab> labOptional = labRepository.findByEmail(email);
	    if (labOptional.isPresent()) {
	        Optional<LabReviewRating> labReviewRatingOptional = labReviewRatingRepository.findByIdAndLabId(id, labOptional.get().getId());
	        if (labReviewRatingOptional.isPresent()) {
	            labReviewRatingRepository.delete(labReviewRatingOptional.get());
	            response.put(AppConstant.MESSAGE, "Lab review deleted successfully");
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_NOT_FOUND);
	        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	    }
	}

	private ResponseEntity<?> deleteReviewAsPatient(Long id, String email, Map<String, Object> response) {
	    // Patient can delete only their own reviews
	    Optional<Patient> patientOptional = patientRepository.findByEmail(email);
	    if (patientOptional.isPresent()) {
	        Optional<LabReviewRating> labReviewRatingOptional = labReviewRatingRepository.findByIdAndPatientId(id, patientOptional.get().getId());
	        if (labReviewRatingOptional.isPresent()) {
	            labReviewRatingRepository.delete(labReviewRatingOptional.get());
	            response.put(AppConstant.MESSAGE, "Lab review deleted successfully");
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.PAITENT_NOT_FOUND);
	        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	    }
	}

	public ResponseEntity<Double> calculateAverageRatingOfLab(Long labId) {
	    List<LabReviewRating> labReviewRatings = labReviewRatingRepository.findAllByLabId(labId);
	    
	    int oneStarCount = 0, twoStarCount = 0, threeStarCount = 0, fourStarCount = 0, fiveStarCount = 0;
	    int totalRatings = 0;

	    for (LabReviewRating rating : labReviewRatings) {
	        int stars = rating.getRating();
	        switch (stars) {
	            case 1:
	                oneStarCount++;
	                break;
	            case 2:
	                twoStarCount++;
	                break;
	            case 3:
	                threeStarCount++;
	                break;
	            case 4:
	                fourStarCount++;
	                break;
	            case 5:
	                fiveStarCount++;
	                break;
	            default:
	                // Handle unexpected rating value
	        }
	        totalRatings++;
	    }

	    double averageRating = 0.0;
	    if (totalRatings > 0) {
	        averageRating = (oneStarCount * 1 + twoStarCount * 2 + threeStarCount * 3 + fourStarCount * 4 + fiveStarCount * 5) / (double) totalRatings;
	    }

	    // Check if averageRating is NaN and handle it
	    if (Double.isNaN(averageRating)) {
	        // Handle NaN, maybe set a default value or log an error
	        averageRating = 0.0; // Setting a default value of 0.0
	    }

	    return ResponseEntity.ok(averageRating);
	}

	
	public ResponseEntity<?> deleteReply1(Long id, String email, Long reviewRatingId) {
	    Map<String, Object> response = new HashMap<>();

	    if (email == null) {
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_REVIEW_STATUS);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    Optional<Varification> var = varificationRepository.findByEmailAndIsActive(email, true);
	    if (var.isPresent()) {
	       Varification v1=var.get();
	        Long roleId = v1.getRoleId();

	        switch (roleId.intValue()) {
	            case 1: // Admin Role
	                return deleteReplyAsAdmin(id, response);
	            case 4: // Lab Role
	                return deleteReplyAsLab(id, email, reviewRatingId, response);
	            case 3: // Patient Role
	                return deleteReplyAsPatient(id, email, reviewRatingId, response);
	            default:
	                response.put(AppConstant.MESSAGE, AppConstant.INVALID_ROLE);
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.INVALID_ROLE);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	}

	private ResponseEntity<?> deleteReplyAsAdmin(Long id, Map<String, Object> response) {
	    // Admin can delete any reply
	    Optional<LabReviewReply> replyOptional = replyRepository.findById(id);
	    if (replyOptional.isPresent()) {
	        replyRepository.delete(replyOptional.get());
	        response.put(AppConstant.MESSAGE, "Lab reply deleted successfully");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_REPLY_STATUS);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	private ResponseEntity<?> deleteReplyAsLab(Long id, String email, Long reviewRatingId, Map<String, Object> response) {
	    // Lab can delete only their own replies for the given review rating ID
	    Optional<Lab> labOptional = labRepository.findByEmail(email);
	    if (labOptional.isPresent()) {
	        Optional<LabReviewReply> replyOptional = replyRepository.findByIdAndPatientIdAndReviewRatingId(id, labOptional.get().getId(), reviewRatingId);
	        if (replyOptional.isPresent()) {
	            replyRepository.delete(replyOptional.get());
	            response.put(AppConstant.MESSAGE, "Lab reply deleted successfully");
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            response.put(AppConstant.MESSAGE, AppConstant.LAB_REPLY_STATUS);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.LAB_NOT_FOUND);
	        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	    }
	}

	private ResponseEntity<?> deleteReplyAsPatient(Long id, String email, Long reviewRatingId, Map<String, Object> response) {
	    // Patient can delete only their own replies for the given review rating ID
	    Optional<Patient> patientOptional = patientRepository.findByEmail(email);
	    if (patientOptional.isPresent()) {
	        Optional<LabReviewReply> replyOptional = replyRepository.findByIdAndPatientIdAndReviewRatingId(id, patientOptional.get().getId(), reviewRatingId);
	        if (replyOptional.isPresent()) {
	            replyRepository.delete(replyOptional.get());
	            response.put(AppConstant.MESSAGE, "Lab reply deleted successfully");
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        } else {
	            response.put(AppConstant.MESSAGE, AppConstant.LAB_REPLY_STATUS);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } else {
	        response.put(AppConstant.MESSAGE, AppConstant.PATIENT_NOT_FOUND);
	        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	    }
	}

	
	
}
