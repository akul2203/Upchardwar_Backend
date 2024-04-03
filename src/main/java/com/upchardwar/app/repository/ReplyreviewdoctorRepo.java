package com.upchardwar.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.doctor.Reply;

public interface ReplyreviewdoctorRepo extends JpaRepository<Reply,Long > {

	List<Reply> findByReviewId(Long reviewId);
}
