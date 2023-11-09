package com.api.school.io;

import java.util.List;

import com.api.school.entities.Faculty;
import com.api.school.entities.Student;
public class FacultyIO {
	Faculty faculty;
	List<Student> students;
	public Faculty getFaculty() {
		return faculty;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public FacultyIO(Faculty faculty, List<Student> students) {
		super();
		this.faculty = faculty;
		this.students = students;
	}
	public FacultyIO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
