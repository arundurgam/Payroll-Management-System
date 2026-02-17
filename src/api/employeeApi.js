import axiosInstance from "./axiosInstance";

export const getEmployees = () =>
  axiosInstance.get("/employees");

export const createEmployee = (data) =>
  axiosInstance.post("/employees", data);

export const updateEmployee = (id, data) =>
  axiosInstance.put(`/employees/${id}`, data);

export const deleteEmployee = (id) =>
  axiosInstance.delete(`/employees/${id}`);

export const searchByDepartment = (department) =>
  axiosInstance.get(`/employees/department/${department}`);

export const filterBySalary = (min, max) =>
  axiosInstance.get(`/employees/salary?min=${min}&max=${max}`);
