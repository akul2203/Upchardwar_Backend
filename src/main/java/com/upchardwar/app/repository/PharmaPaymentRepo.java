package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.pharma.PharmaPayment;

public interface PharmaPaymentRepo extends JpaRepository<PharmaPayment, Long> {

}
