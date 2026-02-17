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

import com.codecamp.payroll_management_system.dto.PayrollDTO;
import com.codecamp.payroll_management_system.model.Payroll;
import com.codecamp.payroll_management_system.services.PayrollService;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "http://localhost:5173")

public class PayrollController {
	private final PayrollService service;

    public PayrollController(PayrollService service) {
        this.service = service;
    }

    // Generate Payroll
    @PostMapping("/generate/{employeeId}")
    public Payroll generate(@PathVariable Long employeeId,
                            @RequestBody PayrollDTO dto) {
        return service.generatePayroll(employeeId, dto);
    }

    // Bulk Payroll
    @PostMapping("/bulk")
    public List<Payroll> generateBulk(@RequestBody List<PayrollDTO> dtos) {
        return service.generateBulkPayroll(dtos);
    }

    // Get Payroll by Employee
    @GetMapping("/employee/{employeeId}")
    public List<Payroll> getByEmployee(@PathVariable Long employeeId) {
        return service.getPayrollByEmployee(employeeId);
    }

    // Get Payroll by Month & Year
    @GetMapping("/month")
    public List<Payroll> getByMonth(
            @RequestParam String month,
            @RequestParam Integer year) {
        return service.getByMonthAndYear(month, year);
    }

    // Get Payroll by Year
    @GetMapping("/year/{year}")
    public List<Payroll> getByYear(@PathVariable Integer year) {
        return service.getByYear(year);
    }

    // Delete Payroll
    @DeleteMapping("/{id}")
    public String deletePayroll(@PathVariable Long id) {
        service.deletePayroll(id);
        return "Payroll deleted successfully";
    }

    // Update Payroll
    @PutMapping("/{id}")
    public Payroll updatePayroll(@PathVariable Long id,
                                 @RequestBody PayrollDTO dto) {
        return service.updatePayroll(id, dto);
    }
}
