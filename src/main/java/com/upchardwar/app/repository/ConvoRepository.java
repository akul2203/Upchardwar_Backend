package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.Conversation;

public interface ConvoRepository extends JpaRepository<Conversation, Long>{

}
