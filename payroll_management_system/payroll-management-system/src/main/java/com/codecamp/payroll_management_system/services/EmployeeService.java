package com.codecamp.payroll_management_system.services;

import java.util.List;

import com.codecamp.payroll_management_system.model.Employee;

public interface EmployeeService {
	   // Create
    Employee createEmployee(Employee employee);

    // Bulk Insert
    List<Employee> saveAllEmployees(List<Employee> employees);

    // Read
    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    // Update
    Employee updateEmployee(Long id, Employee employee);

    // Delete
    void deleteEmployee(Long id);

    // Search By Department
    List<Employee> getByDepartment(String department);

    // Salary Range Filter
    List<Employee> getBySalaryRange(Double min, Double max);

    // Dashboard Methods
    Long countEmployees();

    Employee getHighestPaidEmployee();


}
