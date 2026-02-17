package com.codecamp.payroll_management_system.dto;

import lombok.Data;

@Data
public class PayrollDTO {
	 private Long employeeId;
	    private String month;
	    private Integer year;
	    private Double bonus;
	    private Double deductions;
}
