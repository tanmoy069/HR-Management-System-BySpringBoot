package com.tanmoy.hrms.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tanmoy.hrms.dao.AttendanceRepo;
import com.tanmoy.hrms.dao.EmployeeRepo;
import com.tanmoy.hrms.dao.PayrollRepo;
import com.tanmoy.hrms.dao.SalaryStatusRepo;
import com.tanmoy.hrms.model.Attendance;
import com.tanmoy.hrms.model.Employee;
import com.tanmoy.hrms.model.Payroll;
import com.tanmoy.hrms.model.SalaryStatus;
import com.tanmoy.hrms.model.TakeInOutTime;
import com.tanmoy.hrms.service.HrmService;

@Controller
public class HrmController {
	
	private List<Payroll> payList;	
	@Autowired private EmployeeRepo empRepo;	
	@Autowired private AttendanceRepo attnRepo;	
	@Autowired private SalaryStatusRepo salaryRepo;	
	@Autowired private PayrollRepo payRepo;
	@Autowired private HrmService hrmSer;
			
	@GetMapping(value="/login")
	public String getLogIn() {
		return "login";
	}
	
	@GetMapping(value="/home")
	public String getHome() {
		return "HRM";
	}
	
	@GetMapping(value="/hrm/empdetails")
	public String getEmpDetails(Model model) {
		model.addAttribute("empObj", empRepo.findAll());
		return "HRM - EmpDetails";
	}
	
	@GetMapping(value="/hrm/payroll")
	public String getEmpPayroll(Model model) {
		if(payList != null) payList.clear();
		LocalDate date = LocalDate.now();
		String firstDate = hrmSer.getFirstDayDateOfMonth();
		String lastDate = hrmSer.getLastDayDateOfMonth();
		payList = hrmSer.getPayAbleSalary(date.getMonthValue() - 1, attnRepo.findTotalWorkDay(firstDate, lastDate));
		model.addAttribute("empPayableSalary", payList);
		for (Payroll payroll : payList) {
			System.out.println(payroll.toString());
		}
		model.addAttribute("empPayroll", payRepo.findAll());
		if(payRepo.findAllByPayMonth(hrmSer.getMonth(date.getMonthValue() - 1)).size() != 0) {
			model.addAttribute("viewAddPayrollButton", false);
		} else {
			model.addAttribute("viewAddPayrollButton", true);
		}
		return "HRM - EmpPayroll";
	}
	
	@GetMapping(value="/hrm/performance")
	public String getEmpPerformance(Model model) {
		model.addAttribute("empPerformances", hrmSer.getEmployeePerformances());
		return "HRM - EmpPerformance";
	}
	
	@GetMapping(value="/hrm/registration")
	public String getEmpRegistration() {
		return "HRM - EmpRegistration";
	}
	
	@GetMapping(value="/hrm/resume")
	public String getEmpResume() {
		return "HRM - EmpResume";
	}
	
	@GetMapping(value="/hrm/addattendance")
	public String getAddAttendance() {
		return "AddAttendance";
	}
	
	@RequestMapping(value="/hrm/registration", method=RequestMethod.POST)
	public String addEmployee(@ModelAttribute(name="addEmployeeForm") Employee ob, Model model) {
		try {
			empRepo.save(ob);
			model.addAttribute("validCredentials", true);
		} catch (Exception e) {
			model.addAttribute("inValidCredentials", true);
			System.out.println(e.getMessage());
		}
		return "HRM - EmpRegistration";
	}
	
	@RequestMapping(value="/hrm/resume", method=RequestMethod.POST)
	public String getEmployeeResume(@ModelAttribute(name="findEmpForm") Employee ob, Model model) {
		try {
			Employee emp = empRepo.findByEmpId(ob.getEmpId());
			model.addAttribute("empObj", emp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "HRM - EmpResume";
	}
	
	@RequestMapping(value="/hrm/addattendance", method=RequestMethod.POST)
	public String addAttendance(@ModelAttribute(name="addAttendanceForm") Attendance ob, TakeInOutTime takeOb, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
		try {
			Attendance attn = new Attendance();
			attn.setEmpId(ob.getEmpId());
			attn.setWorkDayDate(ob.getWorkDayDate());
			long msIn = sdf.parse(takeOb.getTakeInTime()).getTime();
			long msOut = sdf.parse(takeOb.getTakeOutTime()).getTime();
			Time inTime = new Time(msIn);
			Time outTime = new Time(msOut);
			attn.setInTime(inTime);
			attn.setOutTime(outTime);
			if(empRepo.findByEmpId(ob.getEmpId()).getFirstName() == null) throw new Exception("Not valid emp id");
			attnRepo.save(attn);
			model.addAttribute("validInput", true);
		} catch (Exception e) {
			model.addAttribute("invalidInput", true);
			System.out.println(e.getMessage());
		}
		return "AddAttendance";
	}
	
	@RequestMapping(value="/hrm/payroll", method=RequestMethod.POST)
	public String addSalary(@ModelAttribute(name="addSalaryForm") SalaryStatus ss, Model model) {
		if (ss.getBasicSalary() != 0 && ss.getEmpId() != 0) {
			try {
				if(empRepo.findByEmpId(ss.getEmpId()).getFirstName() == null) throw new Exception();
				salaryRepo.save(hrmSer.setSalary(ss.getEmpId(), ss.getBasicSalary()));
				model.addAttribute("validInput", true);
			} catch (Exception e) {
				model.addAttribute("invalidInput", true);
				System.out.println(e.getMessage());
			}			
		}
		else if(ss.getBasicSalary() == 0 && ss.getEmpId() == 0) {
			for (Payroll pay : payList) {
				try {
					payRepo.save(pay);
				} catch (Exception e) {
					model.addAttribute("invalidInputForPay", true);
					System.out.println("Failed to add salary of " + pay.getEmpId());
				}
			}
			model.addAttribute("validInputForPay", true);
		}
		return "HRM - EmpPayroll";
	}
}
