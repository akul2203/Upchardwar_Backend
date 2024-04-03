package com.upchardwar.app.services.impl.doctor;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upchardwar.app.entity.doctor.Reply;
import com.upchardwar.app.repository.ReplyreviewdoctorRepo;
import com.upchardwar.app.services.doctor.ReplyService;

@Service
public class ReplyServiceimpl implements ReplyService{

	
	@Autowired
	private ReplyreviewdoctorRepo replyreviewdoctorRepo;
	
	
	@Override
	public Reply saveReply(Reply reply) {
		
		return replyreviewdoctorRepo.save(reply);
	}

	@Override
	public Reply getReplyById(Long id) {
		
		return replyreviewdoctorRepo.getById(id);
	}

	@Override
	public List<Reply> getAllRepliesByReview(Long reviewId) {
		
		return replyreviewdoctorRepo.findByReviewId(reviewId);
	}

	@Override
	public void deleteReply(Long id) {
		
		this.replyreviewdoctorRepo.deleteById(id);
	}

}
