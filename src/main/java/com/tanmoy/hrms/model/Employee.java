package com.tanmoy.hrms.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Emp_Details")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "emp_id", updatable = false)
	private int empId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private long phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "marital_status")
	private String maritalStatus;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "date_of_joining")
	private Date dateOfJoining;
	
	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "religious")
	private String religious;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "national_id")
	private long nationalId;
	
	@Column(name = "skills")
	private String skills;
	
	@Column(name = "grad_varsity")
	private String gradVarsity;
	
	@Column(name = "grad_year")
	private int gradYear;
	
	public String getFullName() {
		return getFirstName() + " "+ getLastName();
	}

}
