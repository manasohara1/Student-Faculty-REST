package com.api.school.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.school.dao.FacultyRepository;
import com.api.school.dao.StudentRepository;
import com.api.school.entities.Faculty;
import com.api.school.entities.Student;

import jakarta.validation.Valid;

@RestController
//@RequestMapping("/student/faculty")
public class StudentFacultyController {
	private StudentRepository studentRepository;
	private FacultyRepository facultyRepository;

	public StudentFacultyController(StudentRepository studentRepository, FacultyRepository facultyRepository) {
		this.studentRepository = studentRepository;
		this.facultyRepository = facultyRepository;
	}

	/*
	 * //Method Description : This method adds student+faculty JSON object via
	 * postman to database
	 * 
	 * @PostMapping(value = "/student/faculty", consumes = { "application/json" })
	 * public Student saveStudentWithFaculty(@RequestBody @Valid Student student) {
	 * Student student2 = studentRepository.save(student); //
	 * System.out.println(student2); return student2; }
	 */

	/*
	 * // Method Description : This method adds student+faculty JSON object via
	 * postman // to database // Validation of JSON Object has been done
	 * 
	 * @PostMapping(value = "/student/faculty", consumes = { "application/json" })
	 * public ResponseEntity<?> saveStudentWithFaculty(@RequestBody @Valid Student
	 * student, BindingResult bindingResult) { if (bindingResult.hasErrors()) { //
	 * Handle validation errors
	 * 
	 * List<String> errorMessages = new ArrayList<>();
	 * 
	 * for (FieldError error : bindingResult.getFieldErrors()) {
	 * errorMessages.add(error.getField() + ": " + error.getDefaultMessage()); }
	 * 
	 * return ResponseEntity.badRequest().body(errorMessages); }
	 * 
	 * Student savedStudent = studentRepository.save(student); return
	 * ResponseEntity.ok(savedStudent); }
	 */

	// Method Description : Add student but check faculty exists or not
	@PostMapping(value = "/student/with/faculty", consumes = { "application/json" })
	public ResponseEntity<?> saveStudentWithFaculty(@RequestBody @Valid Student student, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {

			// Handle validation errors
			List<String> errorMessages = new ArrayList<>();

			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMessages.add(error.getField() + ": " + error.getDefaultMessage());
			}

			return ResponseEntity.badRequest().body(errorMessages);
		} else {
			try {

				// Taking all faculties associated with Student
				List<Faculty> faculties = student.getFaculties();

				// Conversion to ArrayList for traversal
				List<Faculty> existingFaculties = new ArrayList<>();

				// Traversing faculty
				for (Faculty faculty : faculties) {
					Faculty existingFaculty = facultyRepository.findByEmail(faculty.getEmail());

					// if no existing faculty found create a faculty
					if (existingFaculty == null) {
						existingFaculty = new Faculty();
						existingFaculty.setFirst_name(faculty.getFirst_name());
						existingFaculty.setLast_name(faculty.getLast_name());
						existingFaculty.setEmail(faculty.getEmail());
						existingFaculty.setPhone_number(faculty.getPhone_number());
						existingFaculty.setSpecialization(faculty.getSpecialization());
						existingFaculty = facultyRepository.save(existingFaculty);
					}
					// common statement
					existingFaculties.add(existingFaculty);
				}

				student.setFaculties(existingFaculties);
				Student savedStudent = studentRepository.save(student);
				return ResponseEntity.ok(savedStudent);
			} catch (Exception e) {
				// Handle exception
				return ResponseEntity.badRequest().body("Error while processing request.");
			}
		}
	}

	// Method Description : This method finds gives list of all students with
	// faculties
	// Works same as : http://localhost:8080/student/faculty
	@GetMapping(value = "/students/faculty")
	public List<Student> findAllStudents() {
		return studentRepository.findAll();
	}

	// Method Description : This method gives List of Students under X FacultyID
	@GetMapping("/students/by-faculty/{facultyId}")
	public List<Student> findStudentUnderFaculty(@PathVariable("facultyId") int facultyId) {
		List<Object[]> queryResults = studentRepository.findStudentUnderFaculty(facultyId);

		List<Student> students = new ArrayList<>();
		for (Object[] row : queryResults) {
			Student student = new Student();
			student.setStudentID((Integer) row[4]);
			student.setEmail((String) row[2]);
			student.setFirst_name((String) row[0]);
			student.setLast_name((String) row[1]);
			student.setPhone_number((String) row[3]);

			students.add(student);
		}
		return students;
	}

}
