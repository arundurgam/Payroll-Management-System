package com.codecamp.payroll_management_system.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codecamp.payroll_management_system.model.Employee;
import com.codecamp.payroll_management_system.model.Payroll;
import com.codecamp.payroll_management_system.repository.PayrollRepository;
import com.codecamp.payroll_management_system.services.PayslipService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PayslipServiceImpl implements PayslipService {

	private final PayrollRepository payrollRepository;

//	1️ Creates PayrollRepository bean
//	2️ Creates PayslipServiceImpl bean
//	3️ Injects repository through constructor
	public PayslipServiceImpl(PayrollRepository payrollRepository) {
		this.payrollRepository = payrollRepository;
	}

	@Override
	public byte[] generatePayslip(Long payrollId) throws Exception {

	    Payroll payroll = payrollRepository.findById(payrollId)
	            .orElseThrow(() -> new RuntimeException("Payroll not found"));

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    PdfWriter writer = new PdfWriter(outputStream);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    Employee emp = payroll.getEmployee();

	    document.add(new Paragraph("PAYSLIP")
	            .setBold()
	            .setFontSize(18));

	    document.add(new Paragraph(" "));
	    document.add(new Paragraph("Employee Name: " + emp.getEmpName()));
	    document.add(new Paragraph("Email: " + emp.getEmpEmail()));
	    document.add(new Paragraph("Department: " + emp.getDepartment()));
	    document.add(new Paragraph("Designation: " + emp.getDesignation()));
	    document.add(new Paragraph(" "));
	    document.add(new Paragraph("Month: " + payroll.getMonth()));
	    document.add(new Paragraph("Year: " + payroll.getYear()));
	    document.add(new Paragraph("Base Salary: ₹ " + emp.getBaseSalary()));
	    document.add(new Paragraph("Bonus: ₹ " + payroll.getBonus()));
	    document.add(new Paragraph("Deductions: ₹ " + payroll.getDeductions()));
	    document.add(new Paragraph(" "));
	    document.add(new Paragraph("Net Salary: ₹ " + payroll.getNetSalary())
	            .setBold());

	    document.close();

	    return outputStream.toByteArray();
	}

	@Override
	public byte[] generateBulkPayslip(String month, Integer year) throws Exception {

	    List<Payroll> payrolls = payrollRepository
	            .findByMonthAndYear(month, year);

	    if (payrolls.isEmpty()) {
	        throw new RuntimeException("No payroll found for given month/year");
	    }

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    PdfWriter writer = new PdfWriter(outputStream);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    document.add(new Paragraph("Payroll Report - " + month + " " + year)
	            .setBold()
	            .setFontSize(16));

	    document.add(new Paragraph(" "));

	    for (Payroll payroll : payrolls) {

	        Employee emp = payroll.getEmployee();

	        document.add(new Paragraph("Employee: " + emp.getEmpName()));
	        document.add(new Paragraph("Department: " + emp.getDepartment()));
	        document.add(new Paragraph("Base Salary: " + emp.getBaseSalary()));
	        document.add(new Paragraph("Bonus: " + payroll.getBonus()));
	        document.add(new Paragraph("Deductions: " + payroll.getDeductions()));
	        document.add(new Paragraph("Net Salary: " + payroll.getNetSalary()));
	        document.add(new Paragraph("--------------------------------------"));
	        document.add(new Paragraph(" "));
	    }

	    document.close();

	    return outputStream.toByteArray();
	}

}
