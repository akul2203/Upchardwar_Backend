package com.upchardwar.app.services.doctor;

import java.util.List;

import com.upchardwar.app.entity.doctor.Reply;

public interface ReplyService {

    Reply saveReply(Reply reply);
    Reply getReplyById(Long id);
    List<Reply> getAllRepliesByReview(Long reviewId);
    void deleteReply(Long id);
}
