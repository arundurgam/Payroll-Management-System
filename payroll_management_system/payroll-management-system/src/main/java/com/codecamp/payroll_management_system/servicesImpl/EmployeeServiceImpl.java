package com.codecamp.payroll_management_system.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codecamp.payroll_management_system.model.Employee;
import com.codecamp.payroll_management_system.repository.EmployeeRepository;
import com.codecamp.payroll_management_system.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private final EmployeeRepository repository;

	public EmployeeServiceImpl(EmployeeRepository repository) {
		this.repository = repository;
	}

	@Override
	public Employee createEmployee(Employee employee) {
		if (employee.getBaseSalary() < 0) {
			throw new RuntimeException("Salary must be positive");
		}
		return repository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	@Override
	public List<Employee> saveAllEmployees(List<Employee> employees) {
		return repository.saveAll(employees);
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		Employee existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		existing.setEmpName(employee.getEmpName());
		existing.setEmpEmail(employee.getEmpEmail());
		existing.setDepartment(employee.getDepartment());
		existing.setDesignation(employee.getDesignation());
		existing.setBaseSalary(employee.getBaseSalary());

		return repository.save(existing);
	}

	@Override
	public void deleteEmployee(Long id) {

	    Employee employee = repository.findById(id)
	            .orElseThrow(() -> 
	                new RuntimeException("Employee not found with id: " + id));

	    repository.delete(employee);
	}





	@Override
	public List<Employee> getByDepartment(String department) {
		return repository.findByDepartment(department);
	}

	@Override
	public List<Employee> getBySalaryRange(Double min, Double max) {
		return repository.findByBaseSalaryBetween(min, max);
	}

	@Override
	public Long countEmployees() {
		return repository.count();
	}

	@Override
	public Employee getHighestPaidEmployee() {
		return repository.findTopByOrderByBaseSalaryDesc();
	}

}
