package com.upchardwar.app.services.doctor;

import java.util.List;

import com.upchardwar.app.entity.doctor.Review;

public interface ReviewService {

    Review saveReview(Review review);
    Review getReviewById(Long id);
    List<Review> getAllReviewsByDoctor(Long doctorId);
    List<Review> getAllReviewsByPatient(Long patientId);
    void deleteReview(Long id);
}
