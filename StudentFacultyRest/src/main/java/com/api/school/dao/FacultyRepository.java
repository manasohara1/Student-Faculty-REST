package com.api.school.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.school.entities.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
	List<Faculty> findBySpecialization(String specialization);

	Faculty findByEmail(String email);
	
	@Query(value = "select * from faculty where facultyid between :iStart and :iEnd",nativeQuery = true)
	Optional<List<Faculty>> findFacultyInRange(@Param("iStart") int iStart , @Param("iEnd") int iEnd);


}
