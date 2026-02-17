import { useEffect, useState } from "react";
import {
  Box,
  Typography,
  Button,
  Card,
  CardContent,
  Grid,
  TextField,
  MenuItem,
  Divider,
  Snackbar,
  Alert,
} from "@mui/material";
import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import {
  getEmployees,
  createEmployee,
  updateEmployee,
  deleteEmployee,
  searchByDepartment,
  filterBySalary,
} from "../api/employeeApi";

const drawerWidth = 240;

function Employees() {
  const [employees, setEmployees] = useState([]);
  const [selectedId, setSelectedId] = useState("");

  const [form, setForm] = useState({
    empName: "",
    empEmail: "",
    department: "",
    designation: "",
    baseSalary: "",
  });

  const [searchDept, setSearchDept] = useState("");
  const [minSalary, setMinSalary] = useState("");
  const [maxSalary, setMaxSalary] = useState("");

  const [message, setMessage] = useState("");
  const [openSnack, setOpenSnack] = useState(false);

  // ================= FETCH =================
  const fetchEmployees = async () => {
    try {
      const res = await getEmployees();
      setEmployees(res.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  // ================= HANDLE CHANGE =================
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // ================= AUTO FILL =================
  useEffect(() => {
    const emp = employees.find((e) => e.id === selectedId);
    if (emp) setForm(emp);
  }, [selectedId, employees]);

  // ================= CREATE =================
  const handleAdd = async () => {
    try {
      await createEmployee({
        ...form,
        baseSalary: Number(form.baseSalary),
      });
      showMessage("Employee Added Successfully");
      resetForm();
      fetchEmployees();
    } catch {
      showMessage("Error adding employee");
    }
  };

  // ================= UPDATE =================
  const handleUpdate = async () => {
    if (!selectedId) return showMessage("Select employee");

    try {
      await updateEmployee(selectedId, {
        ...form,
        baseSalary: Number(form.baseSalary),
      });
      showMessage("Employee Updated Successfully");
      fetchEmployees();
    } catch {
      showMessage("Error updating employee");
    }
  };

  // ================= DELETE =================
  const handleDelete = async () => {
    if (!selectedId) return showMessage("Select employee");

    try {
      await deleteEmployee(selectedId);
      showMessage("Employee Deleted Successfully");
      resetForm();
      setSelectedId("");
      fetchEmployees();
    } catch {
      showMessage("Error deleting employee");
    }
  };

  // ================= SEARCH =================
  const handleSearch = async () => {
    if (!searchDept) return fetchEmployees();
    const res = await searchByDepartment(searchDept);
    setEmployees(res.data);
  };

  const handleFilter = async () => {
    if (!minSalary || !maxSalary) return fetchEmployees();
    const res = await filterBySalary(minSalary, maxSalary);
    setEmployees(res.data);
  };

  // ================= HELPERS =================
  const resetForm = () => {
    setForm({
      empName: "",
      empEmail: "",
      department: "",
      designation: "",
      baseSalary: "",
    });
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setOpenSnack(true);
  };

  return (
    <Box sx={{ display: "flex" }}>
      <Sidebar />

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          ml: `${drawerWidth}px`,
          minHeight: "100vh",
          backgroundColor: "#f4f6f8",
        }}
      >
        <Navbar />

        <Box sx={{ mt: 10, px: 4, pb: 4 }}>
          <Typography variant="h4" gutterBottom>
            Employee Management
          </Typography>

          <Grid container spacing={3}>
            {/* ================= ADD CARD ================= */}
            <Grid item xs={12} md={6}>
              <Card sx={{ borderRadius: 3 }}>
                <CardContent>
                  <Typography variant="h6">Add Employee</Typography>
                  <Divider sx={{ my: 2 }} />

                  <TextField fullWidth label="Name" name="empName" value={form.empName} onChange={handleChange} sx={{ mb: 2 }} />
                  <TextField fullWidth label="Email" name="empEmail" value={form.empEmail} onChange={handleChange} sx={{ mb: 2 }} />
                  <TextField fullWidth label="Department" name="department" value={form.department} onChange={handleChange} sx={{ mb: 2 }} />
                  <TextField fullWidth label="Designation" name="designation" value={form.designation} onChange={handleChange} sx={{ mb: 2 }} />
                  <TextField fullWidth label="Salary" type="number" name="baseSalary" value={form.baseSalary} onChange={handleChange} sx={{ mb: 2 }} />

                  <Button variant="contained" fullWidth size="large" onClick={handleAdd}>
                    Add Employee
                  </Button>
                </CardContent>
              </Card>
            </Grid>

            {/* ================= UPDATE & DELETE ================= */}
            <Grid item xs={12} md={6}>
              <Card sx={{ borderRadius: 3 }}>
                <CardContent>
                  <Typography variant="h6">Update / Delete</Typography>
                  <Divider sx={{ my: 2 }} />

                  <TextField
                    select
                    fullWidth
                    label="Select Employee"
                    value={selectedId}
                    onChange={(e) => setSelectedId(e.target.value)}
                    sx={{ mb: 2 }}
                  >
                    {employees.map((emp) => (
                      <MenuItem key={emp.id} value={emp.id}>
                        {emp.empName}
                      </MenuItem>
                    ))}
                  </TextField>

                  <Button variant="contained" fullWidth sx={{ mb: 2 }} onClick={handleUpdate}>
                    Update Employee
                  </Button>

                  <Button variant="contained" color="error" fullWidth onClick={handleDelete}>
                    Delete Employee
                  </Button>
                </CardContent>
              </Card>
            </Grid>

            {/* ================= SEARCH ================= */}
            <Grid item xs={12}>
              <Card sx={{ borderRadius: 3 }}>
                <CardContent>
                  <Typography variant="h6">Search & Filter</Typography>
                  <Divider sx={{ my: 2 }} />

                  <Grid container spacing={2}>
                    <Grid item xs={12} md={4}>
                      <TextField fullWidth label="Department" value={searchDept} onChange={(e) => setSearchDept(e.target.value)} />
                    </Grid>

                    <Grid item xs={12} md={2}>
                      <Button fullWidth variant="contained" onClick={handleSearch}>
                        Search
                      </Button>
                    </Grid>

                    <Grid item xs={12} md={3}>
                      <TextField fullWidth label="Min Salary" type="number" value={minSalary} onChange={(e) => setMinSalary(e.target.value)} />
                    </Grid>

                    <Grid item xs={12} md={3}>
                      <TextField fullWidth label="Max Salary" type="number" value={maxSalary} onChange={(e) => setMaxSalary(e.target.value)} />
                    </Grid>

                    <Grid item xs={12}>
                      <Button fullWidth variant="outlined" onClick={handleFilter}>
                        Filter by Salary
                      </Button>
                    </Grid>
                  </Grid>
                </CardContent>
              </Card>
            </Grid>

            {/* ================= EMPLOYEE LIST ================= */}
            <Grid item xs={12}>
              <Card sx={{ borderRadius: 3 }}>
                <CardContent>
                  <Typography variant="h6" sx={{ mb: 2 }}>
                    Employee List
                  </Typography>

                  <Grid container spacing={2}>
                    {employees.map((emp) => (
                      <Grid item xs={12} md={6} lg={4} key={emp.id}>
                        <Card sx={{ p: 2, boxShadow: 3 }}>
                          <Typography fontWeight="bold">{emp.empName}</Typography>
                          <Typography>{emp.empEmail}</Typography>
                          <Typography>{emp.department} • {emp.designation}</Typography>
                          <Typography>₹ {emp.baseSalary}</Typography>
                        </Card>
                      </Grid>
                    ))}
                  </Grid>
                </CardContent>
              </Card>
            </Grid>

          </Grid>

          <Snackbar
            open={openSnack}
            autoHideDuration={3000}
            onClose={() => setOpenSnack(false)}
          >
            <Alert severity="success">{message}</Alert>
          </Snackbar>
        </Box>
      </Box>
    </Box>
  );
}

export default Employees;
