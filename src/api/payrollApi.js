import axiosInstance from "./axiosInstance";

export const generatePayroll = (employeeId, data) =>
  axiosInstance.post(`/payroll/generate/${employeeId}`, data);

export const getPayrollByMonth = (month, year) =>
  axiosInstance.get(`/payroll/month?month=${month}&year=${year}`);
