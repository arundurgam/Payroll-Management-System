package com.codecamp.payroll_management_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payroll")
@Data
//Employee exists first
//Payroll depends on Employee
//Foreign key = employee_id in payroll table
public class Payroll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String month;
	private Integer year;

	private Double bonus;
	private Double deductions;
	private Double netSalary;

	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

}
