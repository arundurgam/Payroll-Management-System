import axiosInstance from "./axiosInstance";

export const getDashboardSummary = (year) =>
  axiosInstance.get(`/dashboard/summary/${year}`);
export const getTotalEmployees = () =>
  axiosInstance.get("/dashboard/total-employees");

export const getHighestPaidEmployee = () =>
  axiosInstance.get("/dashboard/highest-paid-employee");

export const getHighestPayroll = () =>
  axiosInstance.get("/dashboard/highest-payroll");
