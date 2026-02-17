package com.codecamp.payroll_management_system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.codecamp.payroll_management_system.model.Employee;
import com.codecamp.payroll_management_system.model.Payroll;
import com.codecamp.payroll_management_system.services.EmployeeService;
import com.codecamp.payroll_management_system.services.PayrollService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	private final EmployeeService employeeService;
	private final PayrollService payrollService;

	public DashboardController(EmployeeService employeeService, PayrollService payrollService) {
		this.employeeService = employeeService;
		this.payrollService = payrollService;
	}

	@GetMapping("/summary/{year}")
	public Map<String, Object> getSummary(@PathVariable Integer year) {

		Long totalEmployees = employeeService.countEmployees();
		Double totalSalary = payrollService.getTotalSalaryPaidByYear(year);
		Payroll highestPayroll = payrollService.getHighestSalaryRecord();
		Employee highestEmployee = employeeService.getHighestPaidEmployee();

		Map<String, Object> response = new HashMap<>();
		response.put("totalEmployees", totalEmployees);
		response.put("totalSalary", totalSalary);
		response.put("highestPayroll", highestPayroll);
		response.put("highestEmployee", highestEmployee);

		return response;
	}
}
