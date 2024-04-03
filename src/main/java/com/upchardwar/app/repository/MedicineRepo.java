package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.pharma.Medicine;

public interface MedicineRepo extends JpaRepository<Medicine, Long> {

}
