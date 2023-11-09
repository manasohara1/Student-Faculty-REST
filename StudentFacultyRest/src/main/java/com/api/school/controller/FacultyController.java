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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.school.entities.Faculty;
import com.api.school.exception.FacultyInvalidException;
import com.api.school.io.FacultyIO;
import com.api.school.services.FacultyService;
import com.api.school.validator.MyValidator;

@RestController
@RequestMapping("/faculty")
public class FacultyController implements MyValidator {

//	reference of service
	@Autowired
	private FacultyService facultyService;

	// All Faculty Handler
	@GetMapping(value = "/fetchAll")
	public ResponseEntity<List<Faculty>> getAllFaculty() {
		List<Faculty> list = null;
		list = facultyService.getFaculties();

		if (list.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.of(Optional.of(list));
		}
	}

	// get single faculty
	@GetMapping(value = "/fetchByID/{facultyId}")
	public ResponseEntity<Optional<Faculty>> getFacultybyId(@PathVariable("facultyId") int facultyId) {
		Optional<Faculty> faculty = null;
		faculty = facultyService.getFacultyByID(facultyId);
		System.out.println("postman faculty object received \n" + faculty);
		if (faculty.isPresent()) {
			return ResponseEntity.of(Optional.of(faculty));
		} else {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			throw new FacultyInvalidException();
		}
	}

	// add faculty
	@PostMapping(value = "/addFaculty")
	public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty inputObj) {
		Faculty addedObj = null;
		addedObj = facultyService.addFaculty(inputObj);
		if (addedObj != null) {
			return ResponseEntity.of(Optional.of(addedObj));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// delete faculty
	@DeleteMapping(value = "/faculty/deleteByID/{facultyID}")
	public ResponseEntity<Void> deleteFaculty(@RequestBody Faculty facultyObj,
			@PathVariable("facultyID") int facultyID) {
		try {
			facultyService.deleteFaculty(facultyObj, facultyID);
			System.out.println("Deleted faculty object via postman");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Failure in deleting faculty");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// update faculty
	@PutMapping(value = "/UpdateByID/{facultyId}")
	public ResponseEntity<Void> updateFaculty(@RequestBody Faculty facultyOj,
			@PathVariable("facultyId") int facultyId) {
		try {
			facultyService.updateFaculty(facultyOj, facultyId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			System.out.println("Failed to update faculty");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// get faculties list from range of faculties
	@GetMapping(value = "/range/{startRange}/{endRange}")
	public ResponseEntity<Optional<List<Faculty>>> getFacultybyIdRange(@PathVariable("startRange") int iStart,
			@PathVariable("endRange") int iEnd) {
		Optional<List<Faculty>> faculty = null;
		faculty = facultyService.getFacultyByIDRange(iStart, iEnd);
		System.out.println("postman faculty object received \n" + faculty);
		if (faculty != null) {
			return ResponseEntity.of(Optional.of(faculty));
		} else {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return ResponseEntity.ok().build();
		}
	}

	// get faculties list w.r.t. specialization
	@GetMapping("faculty/searchBySpecialization/{specialization}")
	public ResponseEntity<List<Faculty>> getFacultyBySpecialization(
			@PathVariable("specialization") String specialization) {
		List<Faculty> faculties = null;
		faculties = facultyService.findFacultyBySpecialization(specialization);
		if (faculties != null) {
			return ResponseEntity.of(Optional.of(faculties));
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	// DTO - IO related mapping API
	@GetMapping("/faculties-with-students")
	public List<FacultyIO> getAllFacultiesStudent() {
		return facultyService.getAllFacultiesAndUnderStudents();
	}
}
