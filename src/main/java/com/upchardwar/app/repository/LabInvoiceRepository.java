package com.upchardwar.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.lab.LabInvoice;

public interface LabInvoiceRepository extends JpaRepository<LabInvoice, Long> {


	Page<LabInvoice> findBylabId(Long labId, Pageable pageable);

}
