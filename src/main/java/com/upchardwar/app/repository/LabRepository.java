package com.upchardwar.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.lab.Lab;


public interface LabRepository extends JpaRepository<Lab, Long> {
	public Optional<Lab> findByLabName(String labName);

	@Query("select l from Lab l where l.isApproved=:b and l.id=:id")
	public Optional<Lab> findByIdAndIsApproved(boolean b, Long id);

	public Page<Lab> findByIsApproved(boolean b, PageRequest page);

	public Page<Lab> findByIsApprovedAndIsDeleted(boolean b, PageRequest page, boolean c);

	public Optional<Lab> findByEmail(String email);

	public Lab findByUserId(Long userId);

	

	public Page<Lab> findByIsDeleted(Pageable pageable, boolean b);

	public Page<Lab> findByIsDeletedAndIsApproved(Pageable pageable, boolean b, boolean c);
	
    @Query("SELECT lab FROM Lab lab WHERE lab.labName LIKE %:keyword% OR lab.email LIKE %:keyword%")
    List<Lab> searchLabs(String keyword);

	

}
