package com.tanmoy.hrms;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tanmoy.hrms.dao.AttendanceRepo;
import com.tanmoy.hrms.dao.PayrollRepo;
import com.tanmoy.hrms.model.Payroll;
import com.tanmoy.hrms.model.Performance;
import com.tanmoy.hrms.service.HrmService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HrmApplicationTests {
	
	@Autowired private HrmService hrmSer;
	
	@Autowired private AttendanceRepo attnRepo;
	
	@Autowired private PayrollRepo payRepo;
	
	@Test
	public void test() {
		System.out.println(hrmSer.getPaySalary(1009, 7));
	}
	
	@Test
	public void test1() {
		LocalDate date = LocalDate.now();
		System.out.println(date.toString());
//		String firstDate = date.getYear() + "-01" +  "-01";
//		String lastDate = date.getYear() + "-0"+ date.getMonthValue() + "-30";
		System.out.println(hrmSer.getFirstDayDateOfMonth());
		System.out.println(hrmSer.getLastDayDateOfMonth());
		System.out.println(attnRepo.findTotalWorkDay(hrmSer.getFirstDayDateOfMonth(), hrmSer.getLastDayDateOfMonth()));
		System.out.println(attnRepo.findTotalWorkDayById("2020-01-01", "2020-01-31", 1009));
	}
	
	@Test
	public void testEmpPerformances() {
		for (Performance per : hrmSer.getEmployeePerformances()) {
			System.out.println(per.getEmpId() + " " + per.getAttnPercentage());
		}
	}
	
	@Test
	public void findAllByPayMonth() {
		System.out.println(payRepo.findAllByPayMonth("Feb").size());
	}
	
	@Test
	public void getPayAbleSalry() {
		LocalDate date = LocalDate.now();
		String firstDate = hrmSer.getFirstDayDateOfMonth();
		String lastDate = hrmSer.getLastDayDateOfMonth();
		System.out.println(firstDate);
		System.out.println(lastDate);
		System.out.println("Total Work Day of this month: " + attnRepo.findTotalWorkDay(firstDate, lastDate));
		List<Payroll> payList = hrmSer.getPayAbleSalary(date.getMonthValue() - 1, attnRepo.findTotalWorkDay(firstDate, lastDate));
		for (Payroll payroll : payList) {
			System.out.println(payroll.toString());
		}
	}
	
	

}
