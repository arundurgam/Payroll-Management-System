package com.codecamp.payroll_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecamp.payroll_management_system.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    // Get payrolls by employeeId
    List<Payroll> findByEmployeeId(Long employeeId);

    // Filter by month and year
    List<Payroll> findByMonthAndYear(String month, Integer year);

    // Filter by year
    List<Payroll> findByYear(Integer year);

    // Highest salary record
    Payroll findTopByOrderByNetSalaryDesc();

}
