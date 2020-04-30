package com.tanmoy.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Performance {
	
	int empId;
	String empName;
	double attnPercentage;

}
