package com.api.school.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.school.dao.StudentRepository;
import com.api.school.entities.Student;
import com.api.school.io.StudentIO;

@Service
@Component
public class StudentService {
	@Autowired
	private StudentRepository studentrepository;

	@Autowired
	private ModelMapper modelMapper;

	// get all students
	public List<Student> getAllStudents() {
		List<Student> list = (List<Student>) studentrepository.findAll();
		return list;
	}

	// get single student by id
	public Optional<Student> getStudentById(int id) {
		Optional<Student> student = null;

		student = studentrepository.findById(id);
		return student;

	}

	// add student
	public Student addStudent(Student sobj) {
		Student addedStudent = studentrepository.save(sobj);

		return addedStudent;
	}

	// delete student
	public void deleteStudent(int sid) {
		studentrepository.deleteById(sid);
	}

	// update student
	public void updateStudent(Student student, int sid) {
		student.setStudentID(sid);
		studentrepository.save(student);
	}

	// get list of students according to name in alphabetical order
	public List<Student> sortbyName() {
		return (List<Student>) studentrepository.findByOrderByFirstNameAndLastName();
	}

	// get student by email
	public List<Student> getStudentByEmail(String email) {
		List<Student> student = null;
		student = studentrepository.findByEmail(email);
		return student;
	}

	// IO - DTO related Code
	public List<StudentIO> getAllStudentsUnderFaculties() {
		return studentrepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
	}

	private StudentIO convertEntityToDTO(Student student) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		StudentIO studentIO = new StudentIO();
		studentIO = modelMapper.map(student, StudentIO.class);
		return studentIO;
	}
}
