package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.pharma.MedicineOrder;

public interface MedicineOrderRepo extends JpaRepository<MedicineOrder, Long> {

}
