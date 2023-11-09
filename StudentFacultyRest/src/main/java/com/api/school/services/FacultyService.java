package com.api.school.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.school.dao.FacultyRepository;
import com.api.school.entities.Faculty;
import com.api.school.io.FacultyIO;

@Service
@Component
public class FacultyService {

	// interface reference
	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private ModelMapper modelMapper;

	// get all faculties
	public List<Faculty> getFaculties() {
		List<Faculty> faculties = (List<Faculty>) facultyRepository.findAll();
		return faculties;
	}

	// get faculty by ID
	public Optional<Faculty> getFacultyByID(int facultyId) {
		Optional<Faculty> fetchedFacultyById = null;
		fetchedFacultyById = facultyRepository.findById(facultyId);
		return fetchedFacultyById;
	}

	// add faculty
	public Faculty addFaculty(Faculty inputObj) {
		Faculty result = facultyRepository.save(inputObj);
		return result;
	}

	// update faculty
	public void updateFaculty(Faculty obj, int facultyId) {
		obj.setFacultyID(facultyId);
		facultyRepository.save(obj);
	}

	// delete faculty
	public void deleteFaculty(Faculty obj, int facultyId) {
		facultyRepository.deleteById(facultyId);
	}

	// get faculty by ID
	public Optional<List<Faculty>> getFacultyByIDRange(int iStart, int iEnd) {
		Optional<List<Faculty>> fetchedFacultyById = null;
		fetchedFacultyById = facultyRepository.findFacultyInRange(iStart, iEnd);
		return fetchedFacultyById;
	}

	// get List of Faculties by their respective specialization
	public List<Faculty> findFacultyBySpecialization(String specialization) {
		return facultyRepository.findBySpecialization(specialization);
	}

	// IO - DTO Codes
	public List<FacultyIO> getAllFacultiesAndUnderStudents() {
		return facultyRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
	}

	private FacultyIO convertEntityToDTO(Faculty faculty) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		FacultyIO facultyIO = new FacultyIO();
		facultyIO = modelMapper.map(faculty, FacultyIO.class);
		return facultyIO;
	}

}
