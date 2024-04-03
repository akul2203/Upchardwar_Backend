package com.upchardwar.app.services.impl.doctor;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upchardwar.app.entity.doctor.Review;
import com.upchardwar.app.repository.ReviewdoctorRepo;
import com.upchardwar.app.services.doctor.ReplyService;
import com.upchardwar.app.services.doctor.ReviewService;

@Service
public class ReviewServiceimpl implements ReviewService {

	
    @Autowired
    private ReplyService replyService;

    
	@Autowired
	private ReviewdoctorRepo reviewdoctorRepo;

	@Override
	public Review saveReview(Review review) {	

		return this.reviewdoctorRepo.save(review);
	}

	@Override
	@SuppressWarnings(value = { "all" })
	public Review getReviewById(Long id) {

		return reviewdoctorRepo.getById(id);
	}

	@Override
	public List<Review> getAllReviewsByDoctor(Long doctorId) {

		return reviewdoctorRepo.findByDoctorId(doctorId);
	}

	@Override
	public List<Review> getAllReviewsByPatient(Long patientId) {

		return reviewdoctorRepo.findByPatientId(patientId);
	}

	@Override
	public void deleteReview(Long id) {

		this.reviewdoctorRepo.deleteById(id);

	}

	
}
