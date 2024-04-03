package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.pharma.PharmaDocuments;

public interface PharmaDocumentRepo extends JpaRepository<PharmaDocuments, Long> {

}
