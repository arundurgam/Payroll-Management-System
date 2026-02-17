package com.codecamp.payroll_management_system.services;

import java.util.List;

import com.codecamp.payroll_management_system.dto.PayrollDTO;
import com.codecamp.payroll_management_system.model.Payroll;

public interface PayrollService {
	  // Create
    Payroll generatePayroll(Long employeeId, PayrollDTO dto);

    List<Payroll> generateBulkPayroll(List<PayrollDTO> dtos);

    // Read
    List<Payroll> getPayrollByEmployee(Long employeeId);

    Payroll getPayrollById(Long payrollId);

    List<Payroll> getAllPayrolls();

    // Update
    Payroll updatePayroll(Long payrollId, PayrollDTO dto);

    // Delete
    void deletePayroll(Long payrollId);

    // Filters
    List<Payroll> getByMonthAndYear(String month, Integer year);

    List<Payroll> getByYear(Integer year);

    // Analytics
    Double getTotalSalaryPaidByYear(Integer year);

    Payroll getHighestSalaryRecord();
}
