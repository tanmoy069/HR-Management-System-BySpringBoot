package com.tanmoy.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tanmoy.hrms.model.SalaryStatus;

public interface SalaryStatusRepo extends JpaRepository<SalaryStatus, Integer> {
	
	SalaryStatus findByEmpId(int id);

}
