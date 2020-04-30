package com.tanmoy.hrms.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Emp_Attendance")
public class Attendance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "attnd_id", updatable = false)
	private int attndId;
	
	@Column(name="work_day_date")
	private Date workDayDate;
	
	@Column(name="emp_id")
	private int empId;
	
	@Column(name="in_time")
	@DateTimeFormat(pattern = "hh:mm")
	private Time inTime;
	
	@Column(name="out_time")
	@DateTimeFormat(pattern = "hh:mm")
	private Time outTime;

}
