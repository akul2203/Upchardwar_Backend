package com.upchardwar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.lab.LabDocument;

public interface LabDocumentRepository extends JpaRepository<LabDocument, Long> {

}
