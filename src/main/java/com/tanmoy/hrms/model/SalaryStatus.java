package com.tanmoy.hrms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EmpSalaryStatus")
public class SalaryStatus {

	@Id
	@Column(name = "emp_id", updatable = false)
	private int empId;
	
	@Column(name = "basic_salary")
	private int basicSalary;
	
	@Column(name = "home_allowance")
	private int homeAllowance;
	
	@Column(name = "travel_allowance")
	private int travelAllowance;
	
	@Column(name = "medical_allowance")
	private int medicalAllowance;
	
	@Column(name = "mobile_bill")
	private int mobileBill;
	
	public int getTotalSalary() {
		return getBasicSalary() + getHomeAllowance() + getTravelAllowance() + getMedicalAllowance() + getMobileBill();
	}

}
