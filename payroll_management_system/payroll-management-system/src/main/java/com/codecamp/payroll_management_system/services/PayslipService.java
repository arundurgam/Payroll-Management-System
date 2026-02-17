package com.codecamp.payroll_management_system.services;

public interface PayslipService {
	byte[] generatePayslip(Long payrollId) throws Exception;
	byte[] generateBulkPayslip(String month, Integer year) throws Exception;


}
