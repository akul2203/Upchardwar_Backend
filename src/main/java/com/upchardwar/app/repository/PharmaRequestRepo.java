package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.pharma.PharmaRequest;


public interface PharmaRequestRepo extends JpaRepository<PharmaRequest, Long> {

}
