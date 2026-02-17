package com.codecamp.payroll_management_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codecamp.payroll_management_system.model.Employee;
import com.codecamp.payroll_management_system.services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins ="http://localhost:5173")

public class EmployeeController {
	 private final EmployeeService service;

	    public EmployeeController(EmployeeService service) {
	        this.service = service;
	    }

	    // Create Employee
	    @PostMapping
	    public Employee create(@RequestBody Employee employee) {
	        return service.createEmployee(employee);
	    }

	    // Bulk Insert
	    @PostMapping("/bulk")
	    public List<Employee> saveAllEmployees(@RequestBody List<Employee> employees) {
	        return service.saveAllEmployees(employees);
	    }

	    // Get All
	    @GetMapping
	    public List<Employee> getAll() {
	        return service.getAllEmployees();
	    }

	    // Get By ID
	    @GetMapping("/{id}")
	    public Employee getById(@PathVariable Long id) {
	        return service.getEmployeeById(id);
	    }

	    // Update Employee
	    @PutMapping("/{id}")
	    public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
	        return service.updateEmployee(id, employee);
	    }

	    // Delete Employee
	    @DeleteMapping("/{id}")
	    public String delete(@PathVariable Long id) {
	        service.deleteEmployee(id);
	        return "Employee deleted successfully";
	    }
	   


	    // Search By Department
	    @GetMapping("/department/{dept}")
	    public List<Employee> getByDepartment(@PathVariable String dept) {
	        return service.getByDepartment(dept);
	    }

	    // Salary Range Filter
	    @GetMapping("/salary")
	    public List<Employee> getBySalaryRange(
	            @RequestParam Double min,
	            @RequestParam Double max) {
	        return service.getBySalaryRange(min, max);
	    }
}
