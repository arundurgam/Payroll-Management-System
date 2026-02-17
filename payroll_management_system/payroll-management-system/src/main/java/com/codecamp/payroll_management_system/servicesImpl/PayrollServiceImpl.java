package com.codecamp.payroll_management_system.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codecamp.payroll_management_system.dto.PayrollDTO;
import com.codecamp.payroll_management_system.model.Employee;
import com.codecamp.payroll_management_system.model.Payroll;
import com.codecamp.payroll_management_system.repository.EmployeeRepository;
import com.codecamp.payroll_management_system.repository.PayrollRepository;
import com.codecamp.payroll_management_system.services.PayrollService;

@Service
public class PayrollServiceImpl implements PayrollService {

	private final PayrollRepository payrollRepository;
	private final EmployeeRepository employeeRepository;

	public PayrollServiceImpl(PayrollRepository payrollRepository, EmployeeRepository employeeRepository) {
		this.payrollRepository = payrollRepository;
		this.employeeRepository = employeeRepository;
	}

	// ================== CREATE ==================

	@Override
	public Payroll generatePayroll(Long employeeId, PayrollDTO dto) {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

		double bonus = dto.getBonus() == null ? 0 : dto.getBonus();
		double deductions = dto.getDeductions() == null ? 0 : dto.getDeductions();

		double netSalary = employee.getBaseSalary() + bonus - deductions;

		Payroll payroll = new Payroll();
		payroll.setMonth(dto.getMonth());
		payroll.setYear(dto.getYear());
		payroll.setBonus(bonus);
		payroll.setDeductions(deductions);
		payroll.setNetSalary(netSalary);
		payroll.setEmployee(employee);

		return payrollRepository.save(payroll);
	}

	@Override
	public List<Payroll> generateBulkPayroll(List<PayrollDTO> dtos) {

		List<Payroll> payrollList = new ArrayList<>();

		for (PayrollDTO dto : dtos) {

			Employee employee = employeeRepository.findById(dto.getEmployeeId())
					.orElseThrow(() -> new RuntimeException("Employee not found with id: " + dto.getEmployeeId()));

			double bonus = dto.getBonus() == null ? 0 : dto.getBonus();
			double deductions = dto.getDeductions() == null ? 0 : dto.getDeductions();

			double netSalary = employee.getBaseSalary() + bonus - deductions;

			Payroll payroll = new Payroll();
			payroll.setMonth(dto.getMonth());
			payroll.setYear(dto.getYear());
			payroll.setBonus(bonus);
			payroll.setDeductions(deductions);
			payroll.setNetSalary(netSalary);
			payroll.setEmployee(employee);

			payrollList.add(payroll);
		}

		return payrollRepository.saveAll(payrollList);
	}

	// ================== READ ==================

	@Override
	public List<Payroll> getPayrollByEmployee(Long employeeId) {
		return payrollRepository.findByEmployeeId(employeeId);
	}

	@Override
	public Payroll getPayrollById(Long payrollId) {
		return payrollRepository.findById(payrollId)
				.orElseThrow(() -> new RuntimeException("Payroll not found with id: " + payrollId));
	}

	@Override
	public List<Payroll> getAllPayrolls() {
		return payrollRepository.findAll();
	}


	@Override
	public Payroll updatePayroll(Long payrollId, PayrollDTO dto) {

		Payroll payroll = payrollRepository.findById(payrollId)
				.orElseThrow(() -> new RuntimeException("Payroll not found with id: " + payrollId));

		double bonus = dto.getBonus() == null ? 0 : dto.getBonus();
		double deductions = dto.getDeductions() == null ? 0 : dto.getDeductions();

		double netSalary = payroll.getEmployee().getBaseSalary() + bonus - deductions;

		payroll.setMonth(dto.getMonth());
		payroll.setYear(dto.getYear());
		payroll.setBonus(bonus);
		payroll.setDeductions(deductions);
		payroll.setNetSalary(netSalary);

		return payrollRepository.save(payroll);
	}

	// ================== DELETE ==================

	@Override
	public void deletePayroll(Long payrollId) {

		if (!payrollRepository.existsById(payrollId)) {
			throw new RuntimeException("Payroll not found with id: " + payrollId);
		}

		payrollRepository.deleteById(payrollId);
	}

	// ================== FILTERS ==================

	@Override
	public List<Payroll> getByMonthAndYear(String month, Integer year) {
		return payrollRepository.findByMonthAndYear(month, year);
	}

	@Override
	public List<Payroll> getByYear(Integer year) {
		return payrollRepository.findByYear(year);
	}

	// ================== ANALYTICS ==================

	@Override
	public Double getTotalSalaryPaidByYear(Integer year) {

		return payrollRepository.findByYear(year).stream().mapToDouble(Payroll::getNetSalary).sum();
	}

	@Override
	public Payroll getHighestSalaryRecord() {
		return payrollRepository.findTopByOrderByNetSalaryDesc();

	}
}
