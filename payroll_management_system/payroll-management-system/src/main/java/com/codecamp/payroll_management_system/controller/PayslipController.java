package com.codecamp.payroll_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codecamp.payroll_management_system.services.PayslipService;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/payslip")
@CrossOrigin(origins = "http://localhost:5173") //
public class PayslipController {

	private final PayslipService service;

	public PayslipController(PayslipService service) {
		this.service = service;
	}

	//Single Payslip Download
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> download(@PathVariable Long id) throws Exception {

		byte[] pdf = service.generatePayslip(id);

		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=Payslip_" + id + ".pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

	// Bulk Payslip Download
	@GetMapping("/bulk")
	public ResponseEntity<byte[]> downloadBulkPayslip(@RequestParam String month, @RequestParam Integer year)
			throws Exception {

		byte[] pdf = service.generateBulkPayslip(month, year);

		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=Payroll_" + month + "_" + year + ".pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}
}
