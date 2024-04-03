package com.upchardwar.app.controller.lab;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.entity.payload.LabReviewRatingRequest;
import com.upchardwar.app.entity.payload.LabReviewReplayRequest;
import com.upchardwar.app.services.lab.ILabReviewRating;

@RestController
@RequestMapping("upchardwar/labreviewrating")
@CrossOrigin("*")
public class LabReviewRatingController {

	@Autowired
	private ILabReviewRating service;
	
	@PostMapping("/")
	public ResponseEntity<?> addreview(@RequestBody LabReviewRatingRequest request) {
		System.out.println("pipip");
		System.err.println(request.getLabId());
		System.err.println(request.getPatientId());
		return new ResponseEntity<>(this.service.addReview(request), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/reply")
	public ResponseEntity<?> addreviewReply(@RequestBody LabReviewReplayRequest request) {
		System.out.println("pipip");
		return new ResponseEntity<>(this.service.addReviewReplly(request), HttpStatus.CREATED);
	}
	
	
    @GetMapping("/{id}")
    public ResponseEntity<?> getLabReviewRatingById(@PathVariable Long id) {
        return service.getLabReviewRatingById(id);
       
    }
    
    @GetMapping("/lab/{labId}")
    public ResponseEntity<?> getLabReviewRatingsByLabId(@PathVariable Long labId) {
      return service.getAllRatingOfLab(labId);
    }

    
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteLabReviewRating(@PathVariable Long reviewId,Principal p){
    	 System.err.println(reviewId);
    	return service.deleteReview1(reviewId, p.getName());
    }
	
    @DeleteMapping("/{reviewId}/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long reviewId,Principal p ,@PathVariable Long replyId){
    System.err.println(reviewId);
    System.err.println(replyId);
    	return service.deleteReply1(replyId, p.getName(),reviewId);
    }
}
