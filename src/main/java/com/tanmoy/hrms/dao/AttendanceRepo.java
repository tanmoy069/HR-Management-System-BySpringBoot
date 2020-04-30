package com.tanmoy.hrms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tanmoy.hrms.model.Attendance;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer>{
	
	List<Attendance> findByEmpId(int id);
	
	@Query(value = "select * from Emp_Attendance where emp_id = ?1 and  work_day_date between ?2 and ?3", nativeQuery = true)
	List<Attendance> findByEmpIdForMonth(int id, String firstDateOfMonth, String lastDateOfMonth);
	
	@Query(value = "SELECT count (distinct work_day_date) FROM Emp_Attendance where work_day_date between ?1 and ?2", nativeQuery = true)
	Integer findTotalWorkDay(String firstDateOfMonth, String lastDateOfMonth);
	
	@Query(value = "SELECT count (distinct work_day_date) FROM Emp_Attendance where emp_id = ?3 and work_day_date between ?1 and ?2", nativeQuery = true)
	Integer findTotalWorkDayById(String firstDateOfMonth, String lastDateOfMonth, Integer id);

}
