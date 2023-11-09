package com.api.school.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.school.entities.Student;
import com.api.school.io.StudentIO;
import com.api.school.services.StudentService;
import com.api.school.validator.MyValidator;

import jakarta.validation.Valid;

@RestController
public class StudentController implements MyValidator {

	@Autowired
	private StudentService studentService; // Service reference

	// This method fetches list of all students
	@GetMapping(value = "/students/fetchAll")
	public ResponseEntity<List<Student>> getStudents() {
		List<Student> students = studentService.getAllStudents();
		if (students.isEmpty()) {
			System.out.println("No Student Objects found");
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}

	// This method fetches student with particular id
	@GetMapping(value = "/student/fetchByID/{sid}")
	public ResponseEntity<Optional<Student>> getStudent(@PathVariable("sid") int sid) {
		Optional<Student> fetchedStudent = studentService.getStudentById(sid);

		if (fetchedStudent == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} else {
			// student with given sid is found
			return ResponseEntity.of(Optional.of(fetchedStudent));
		}
	}

	// Get List of students alphabetically name-wise
	@GetMapping(value = "/students/fetchByName")
	public ResponseEntity<List<Student>> getSortedStudents() {
		try {
			List<Student> students = studentService.sortbyName();
			return ResponseEntity.ok(students);
		} catch (Exception e) {
			System.out.println("Failed to get name-wise list of students");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Get List of Students by their mail id
	@GetMapping("student/fetchByEmail/{email}")
	public List<Student> findStudentsByMail(@PathVariable("email") String emailId) {
		return studentService.getStudentByEmail(emailId);
	}

	// new Student handler
	@PostMapping(value = "/student/addStudent")
	public ResponseEntity<Student> addStudent(@RequestBody @Valid Student inputStudobj) {
		Student result = null;
		try {
			if ((isValidPhoneNumber(inputStudobj.getPhone_number())) && (isValidName(inputStudobj.getFirst_name()))
					&& (isValidName(inputStudobj.getLast_name()) && (isValidEmail(inputStudobj.getEmail())))) {
				result = studentService.addStudent(inputStudobj);
				return ResponseEntity.of(Optional.of(result));
			} else {
				System.out.println("Student input was not valid");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Delete Student Handler
	@DeleteMapping(value = "/student/deleteByID/{sid}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("sid") int sid) {
		try {
			studentService.deleteStudent(sid);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Update Student Handler
	@PutMapping(value = "/student/UpdateByID/{sid}")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable("sid") int sid) {
		try {
			studentService.updateStudent(student, sid);
			return ResponseEntity.ok().body(student);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failed to update student");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// DTO - IO related Mappings
	@GetMapping(value = "/students-with-faculties")
	public List<StudentIO> getAllFacultiesStudent() {
		return studentService.getAllStudentsUnderFaculties();
	}

}
