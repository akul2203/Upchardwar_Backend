package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.upchardwar.app.entity.doctor.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
