package com.tanmoy.hrms.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanmoy.hrms.dao.AttendanceRepo;
import com.tanmoy.hrms.dao.EmployeeRepo;
import com.tanmoy.hrms.dao.SalaryStatusRepo;
import com.tanmoy.hrms.model.Employee;
import com.tanmoy.hrms.model.Payroll;
import com.tanmoy.hrms.model.Performance;
import com.tanmoy.hrms.model.SalaryStatus;

@Service
public class HrmService {
	
	private static DecimalFormat df = new DecimalFormat("#.##");

	@Autowired
	private SalaryStatusRepo salaryRepo;
	@Autowired
	private EmployeeRepo empRepo;
	@Autowired
	private AttendanceRepo attnRepo;

	public SalaryStatus setSalary(int empId, int basicSalary) {
		SalaryStatus ss = new SalaryStatus();
		ss.setBasicSalary(basicSalary);
		ss.setEmpId(empId);
		ss.setHomeAllowance((int) (basicSalary * 0.25));
		ss.setMedicalAllowance((int) (basicSalary * 0.1));
		ss.setMobileBill(500);
		ss.setTravelAllowance(3500);
		return ss;
	}

	public List<Payroll> getPayAbleSalary(int month, int totalWorkDay) {
		List<Payroll> payList = new ArrayList<Payroll>();
		for (Employee emp : empRepo.findAll()) {
			Payroll pay = new Payroll();
			pay.setEmpId(emp.getEmpId());
			pay.setPayMonth(getMonth(month));
			pay.setPaySalary(getPaySalary(emp.getEmpId(), totalWorkDay));
			payList.add(pay);
		}
		return payList;
	}

	public int getPaySalary(int empId, int totalDay) {
		try {
			int totalAttn = attnRepo.findByEmpIdForMonth(empId, getFirstDayDateOfMonth(), getLastDayDateOfMonth()).size();
			int totalSalary = salaryRepo.findByEmpId(empId).getTotalSalary();
			int perDaySalary = totalSalary / totalDay;
			int totalAbsent = totalDay - totalAttn;
			int absentCharge = totalAbsent * perDaySalary;
			return totalSalary - absentCharge;
		} catch (Exception e) {
			return 0;
		}
	}

	public String getMonth(int month) {
		return month == 0 ? "Jan"
				: month == 1 ? "Feb"
						: month == 2 ? "Mar"
								: month == 3 ? "Apr"
										: month == 4 ? "May"
												: month == 5 ? "Jun"
														: month == 6 ? "Jul"
																: month == 7 ? "Aug"
																		: month == 8 ? "Sep"
																				: month == 9 ? "Oct"
																						: month == 10 ? "Nov" : "Dec";
	}
		
	public String getFirstDayDateOfMonth() {
		LocalDate date = LocalDate.now();
		return date.getMonthValue() < 10 ? date.getYear() + "-0" + date.getMonthValue() + "-01"
				: date.getYear() + "-" + date.getMonthValue() + "-01";
	}
	
	public String getLastDayDateOfMonth() {
		LocalDate date = LocalDate.now();
		Calendar calendar = Calendar.getInstance();
		return date.getMonthValue() < 10
				? date.getYear() + "-0" + date.getMonthValue() + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
				: date.getYear() + "-" + date.getMonthValue() + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public List<Performance> getEmployeePerformances() {
		List<Performance> perList = new ArrayList<>();
		for (Employee emp : empRepo.findAll()) {
			Performance per = new Performance();
			per.setEmpId(emp.getEmpId());
			per.setEmpName(emp.getFullName());
			int totalWorkingDay = attnRepo.findTotalWorkDay(emp.getDateOfJoining().toString(),
					LocalDate.now().toString());
			int totalWorkingDayById = attnRepo.findTotalWorkDayById(emp.getDateOfJoining().toString(),
					LocalDate.now().toString(), emp.getEmpId());
			per.setAttnPercentage(Double.parseDouble(df.format((totalWorkingDayById * 100) / (float) totalWorkingDay)));
			perList.add(per);
		}
		return perList;
	}

}
