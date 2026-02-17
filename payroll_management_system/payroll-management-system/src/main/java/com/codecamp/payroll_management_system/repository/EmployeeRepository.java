package com.codecamp.payroll_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecamp.payroll_management_system.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByDepartment(String department);

	List<Employee> findByBaseSalaryBetween(Double min, Double max);

	Employee findTopByOrderByBaseSalaryDesc();


}
