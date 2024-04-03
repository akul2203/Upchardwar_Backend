package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.lab.LabPayment;

public interface LabPaymentRepo extends JpaRepository<LabPayment, Long> {

}
