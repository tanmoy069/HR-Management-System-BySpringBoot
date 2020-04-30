package com.tanmoy.hrms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tanmoy.hrms.model.Payroll;

public interface PayrollRepo extends JpaRepository<Payroll, Integer>{
	
	List<Payroll> findAllByPayMonth(String month);

}
