package com.tanmoy.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tanmoy.hrms.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
	
	Employee findByEmpId(int id);
}
