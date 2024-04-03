package com.upchardwar.app.controller.doctor;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.upchardwar.app.entity.doctor.Reply;
import com.upchardwar.app.entity.doctor.Review;
import com.upchardwar.app.services.doctor.ReplyService;
import com.upchardwar.app.services.doctor.ReviewService;

@RestController
@RequestMapping("upchardwar/doctor/reviewrating")
@CrossOrigin("*")
public class DoctorReviewRatingController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReplyService replyService;

	@PostMapping("/review/add")
	public ResponseEntity<?> addReviewsByDoctor(@RequestBody Review review) {
		
		return new ResponseEntity<>(reviewService.saveReview(review), HttpStatus.OK);
	}
	
	
	@GetMapping("/reviews/by-doctor/{doctorId}")
	public ResponseEntity<List<Review>> getReviewsByDoctor(@PathVariable Long doctorId) {
		List<Review> reviews = reviewService.getAllReviewsByDoctor(doctorId);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	@GetMapping("/reviews/by-patient/{patientId}")
	public ResponseEntity<List<Review>> getReviewsByPatient(@PathVariable Long patientId) {
		List<Review> reviews = reviewService.getAllReviewsByPatient(patientId);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	// for replies of reviews
	// for replies of reviews
	// for replies of reviews
	// for replies of reviews
	// for replies of reviews
	
	@GetMapping("/replies/by-review/{reviewId}")
	public ResponseEntity<List<Reply>> getRepliesByReview(@PathVariable Long reviewId) {
		List<Reply> replies = replyService.getAllRepliesByReview(reviewId);
		return new ResponseEntity<>(replies, HttpStatus.OK);
	}

	@PostMapping("/replies")
	public ResponseEntity<Reply> createReply(@RequestBody Reply reply) {
		Reply createdReply = replyService.saveReply(reply);
		return new ResponseEntity<>(createdReply, HttpStatus.CREATED);
	}

	@PutMapping("/replies/{id}")
	public ResponseEntity<Reply> updateReply(@PathVariable Long id, @RequestBody Reply reply) {
		Reply existingReply = replyService.getReplyById(id);
		if (existingReply == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reply.setId(id);
		Reply updatedReply = replyService.saveReply(reply);
		return new ResponseEntity<>(updatedReply, HttpStatus.OK);
	}

	@DeleteMapping("/replies/{id}")
	public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
		Reply existingReply = replyService.getReplyById(id);
		if (existingReply == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		replyService.deleteReply(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}