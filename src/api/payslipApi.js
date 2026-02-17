import axiosInstance from "./axiosInstance";

// Single Payslip PDF
export const downloadPayslip = (id) =>
  axiosInstance.get(`/payslip/${id}`, {
    responseType: "blob",
  });

// Bulk Payroll PDF
export const downloadBulkPayslip = (month, year) =>
  axiosInstance.get(`/payslip/bulk?month=${month}&year=${year}`, {
    responseType: "blob",
  });
