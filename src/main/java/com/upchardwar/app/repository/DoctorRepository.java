package com.upchardwar.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.payload.DoctorResponse;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	public Optional<Doctor> findByUserName(String drName);

	@Query("select d from Doctor d where d.status=:b and d.id=:id")
	public Optional<Doctor> findByIdAndStatus(String b, long id);

	Page<Doctor> findByStatus(String status, Pageable pageable);

	public Optional<Doctor> findByEmail(String email);

	@Query("SELECT new com.upchardwar.app.entity.payload.DoctorResponse("
			+ "d.id, d.userName, d.name, d.DOB, d.gender, d.phone, d.password, d.email, "
			+ "d.biography, d.address, d.city, d.state, d.country, d.postalcode, d.rate, "
			+ "d.userid, d.status, d.isRejected, d.expierenceFrom, d.expierenceTo, "
			+ "d.awards, d.documentType, d.imageName, d.speciality, d.qualifications, d.doctorDocuments) "
			+ "FROM Doctor d WHERE d.userid = :userid")
	public Optional<DoctorResponse> findDoctorResponseByUserid(@Param("userid") Long userid);

//    public DoctorResponse findByUserid(Long userid);

	Doctor findByUserid(Long userid);
	
    @Query("SELECT d FROM Doctor d WHERE d.name LIKE %:searchTerm% OR d.speciality LIKE %:searchTerm% OR d.city LIKE %:searchTerm%")
    List<Doctor> searchDoctors(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT d FROM Doctor d " +
            "WHERE d.speciality.spName LIKE %:keyword% " +
            "OR d.name LIKE %:keyword% " +
            "OR d.address LIKE %:keyword% " +
            "OR d.city LIKE %:keyword%")
     public List<Doctor> filterDoctorsByKeyword(@Param("keyword")String keyword);
}
