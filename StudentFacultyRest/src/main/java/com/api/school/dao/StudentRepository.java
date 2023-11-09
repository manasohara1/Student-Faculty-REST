package com.api.school.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.school.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
//	List<Student> findByFirst_name(String first_name);
	List<Student> findByEmail(String email);

	// Query : Show the list of all students under certain faculty id
	String  findStudentsByFacultyIdQuery = "SELECT S.first_name, S.last_name, S.email,S.phone_number, sf.faculty_id" + " FROM student AS S"
			+ " INNER JOIN student_faculty AS sf ON S.studentid = sf.student_id" + " WHERE sf.faculty_id = :facultyId";

	@Query(value =  findStudentsByFacultyIdQuery, nativeQuery = true)
	List<Object[]> findStudentUnderFaculty(@Param("facultyId") int facultyId);

	String findAllStudentsSortedByNameQuery = "Select S.studentid , S.first_name , S.last_name , S.phone_number , S.email from student AS S order by S.first_name , S.last_name";

	@Query(value =findAllStudentsSortedByNameQuery, nativeQuery = true)
	List<Student> findByOrderByFirstNameAndLastName();

}
