package com.api.school.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Faculty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int facultyID;

	@NotBlank(message = "faculty first name should not be blnak")
	private String first_name;

	@NotBlank(message = "faculty last name should not be blank")
	private String last_name;

	@Email(message = "Invalid faculty email")
	private String email;

	@NotBlank(message = "faculty phone number should not be blank")
	private String phone_number;

	@NotBlank(message = "faculty specialization should not be blank")
	private String specialization;

	@ManyToMany(mappedBy = "faculties", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Student> students;

	@Override
	public String toString() {
		return "Faculty [facultyID=" + facultyID + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", email=" + email + ", phone_number=" + phone_number + ", specialization=" + specialization + "]";
	}

	// Constructors
	public Faculty(int facultyID, String first_name, String last_name, String email, String phone_number,
			String specialization) {
		super();
		this.facultyID = facultyID;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.phone_number = phone_number;
		this.specialization = specialization;
	}

	public Faculty() {
		super();
		// TODO Auto-generated constructor stub
	}

	// getter and setters
	public int getFacultyID() {
		return facultyID;
	}

	public void setFacultyID(int facultyID) {
		this.facultyID = facultyID;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
}
