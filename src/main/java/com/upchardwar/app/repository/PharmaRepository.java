package com.upchardwar.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upchardwar.app.entity.pharma.Pharmacy;

public interface PharmaRepository extends JpaRepository<Pharmacy, Long> {
	public Optional<Pharmacy> findByEmail(String email);

	@Query("SELECT p FROM Pharmacy p WHERE p.pharmaName LIKE %:searchTerm% OR p.location.area LIKE %:searchTerm%")
	List<Pharmacy> searchPharmacies(@Param("searchTerm") String searchTerm);



}
