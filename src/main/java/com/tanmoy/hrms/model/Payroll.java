package com.tanmoy.hrms.model;

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
@Table(name = "Payroll")
public class Payroll {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "pay_id", updatable = false)
	private int payId;
	
	@Column(name="pay_month")
	private String payMonth;
	
	@Column(name="emp_id")
	private int empId;
	
	@Column(name="pay_salary")
	private int paySalary;

}
