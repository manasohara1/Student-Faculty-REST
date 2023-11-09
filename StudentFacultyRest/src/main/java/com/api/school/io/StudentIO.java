package com.api.school.io;

import java.util.List;

import com.api.school.entities.Student;
import com.api.school.entities.Faculty;
public class StudentIO {

	Student student;
	List<Faculty>faculties;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<Faculty> getFaculties() {
		return faculties;
	}
	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}
	public StudentIO(Student student, List<Faculty> faculties) {
		super();
		this.student = student;
		this.faculties = faculties;
	}
	public StudentIO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
