package com.upchardwar.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.lab.LabReviewReply;

public interface LabReviewReplyRepository extends JpaRepository<LabReviewReply, Long> {

	

	Optional<LabReviewReply> findByIdAndPatientIdAndReviewRatingId(Long id, Long id2, Long reviewId);

}
