import { useEffect, useState } from "react";
import {
  Box,
  Typography,
  Toolbar,
  TextField,
  Button,
  Card,
  CardContent,
  Grid,
  MenuItem,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@mui/material";
import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import { getEmployees } from "../api/employeeApi";
import { generatePayroll, getPayrollByMonth } from "../api/payrollApi";
import { downloadPayslip, downloadBulkPayslip } from "../api/payslipApi";

const drawerWidth = 240;

function Payroll() {
  const [employees, setEmployees] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");
  const [bonus, setBonus] = useState("");
  const [deductions, setDeductions] = useState("");
  const [payrollList, setPayrollList] = useState([]);

  // ================= FETCH EMPLOYEES =================
  useEffect(() => {
    getEmployees().then((res) => setEmployees(res.data));
  }, []);

  // ================= GENERATE PAYROLL =================
  const handleGenerate = async () => {
    if (!selectedEmployee || !month || !year) {
      alert("Please fill required fields");
      return;
    }

    try {
      await generatePayroll(selectedEmployee, {
        month,
        year: Number(year),
        bonus: Number(bonus) || 0,
        deductions: Number(deductions) || 0,
      });

      alert("Payroll Generated Successfully");
    } catch (error) {
      console.error(error);
      alert("Error generating payroll");
    }
  };

  // ================= SEARCH PAYROLL =================
  const handleSearch = async () => {
    if (!month || !year) {
      alert("Enter month and year");
      return;
    }

    try {
      const res = await getPayrollByMonth(month, year);
      setPayrollList(res.data);
    } catch (error) {
      console.error(error);
      alert("Error fetching payroll");
    }
  };

  // ================= DOWNLOAD SINGLE PDF =================
  const handleDownload = async (id) => {
    try {
      const res = await downloadPayslip(id);

      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");

      link.href = url;
      link.setAttribute("download", `Payslip_${id}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error(error);
      alert("PDF download failed");
    }
  };

  // ================= DOWNLOAD BULK PDF =================
  const handleBulkDownload = async () => {
    if (!month || !year) {
      alert("Enter month and year");
      return;
    }

    try {
      const res = await downloadBulkPayslip(month, year);

      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");

      link.href = url;
      link.setAttribute("download", `Payroll_${month}_${year}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error(error);
      alert("Bulk PDF download failed");
    }
  };

  return (
    <Box sx={{ display: "flex" }}>
      <Sidebar />
      <Navbar />

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          ml: `${drawerWidth}px`,
        }}
      >
        <Toolbar />

        <Typography variant="h4" gutterBottom>
          Payroll Management
        </Typography>

        {/* ================= GENERATE FORM ================= */}
        <Card sx={{ mb: 4 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Generate Payroll
            </Typography>

            <Grid container spacing={2}>
              <Grid item xs={12} md={4}>
                <TextField
                  select
                  fullWidth
                  label="Select Employee"
                  value={selectedEmployee}
                  onChange={(e) => setSelectedEmployee(e.target.value)}
                >
                  {employees.map((emp) => (
                    <MenuItem key={emp.id} value={emp.id}>
                      {emp.empName}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>

              <Grid item xs={12} md={2}>
                <TextField
                  fullWidth
                  label="Month"
                  value={month}
                  onChange={(e) => setMonth(e.target.value)}
                />
              </Grid>

              <Grid item xs={12} md={2}>
                <TextField
                  fullWidth
                  label="Year"
                  type="number"
                  value={year}
                  onChange={(e) => setYear(e.target.value)}
                />
              </Grid>

              <Grid item xs={12} md={2}>
                <TextField
                  fullWidth
                  label="Bonus"
                  type="number"
                  value={bonus}
                  onChange={(e) => setBonus(e.target.value)}
                />
              </Grid>

              <Grid item xs={12} md={2}>
                <TextField
                  fullWidth
                  label="Deductions"
                  type="number"
                  value={deductions}
                  onChange={(e) => setDeductions(e.target.value)}
                />
              </Grid>

              <Grid item xs={12}>
                <Button
                  variant="contained"
                  fullWidth
                  onClick={handleGenerate}
                >
                  Generate Payroll
                </Button>
              </Grid>
            </Grid>
          </CardContent>
        </Card>

        {/* ================= VIEW PAYROLL ================= */}
        <Card>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              View Payroll By Month
            </Typography>

            <Grid container spacing={2} sx={{ mb: 2 }}>
              <Grid item xs={12} md={4}>
                <TextField
                  fullWidth
                  label="Month"
                  value={month}
                  onChange={(e) => setMonth(e.target.value)}
                />
              </Grid>

              <Grid item xs={12} md={4}>
                <TextField
                  fullWidth
                  label="Year"
                  type="number"
                  value={year}
                  onChange={(e) => setYear(e.target.value)}
                />
              </Grid>

              <Grid item xs={12} md={4}>
                <Button
                  variant="outlined"
                  fullWidth
                  sx={{ height: "56px" }}
                  onClick={handleSearch}
                >
                  Search
                </Button>
              </Grid>
            </Grid>

            {/* BULK DOWNLOAD BUTTON */}
            <Button
              variant="contained"
              sx={{ mb: 2 }}
              onClick={handleBulkDownload}
            >
              Download Monthly Report
            </Button>

            {/* PAYROLL TABLE */}
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Employee</TableCell>
                  <TableCell>Month</TableCell>
                  <TableCell>Year</TableCell>
                  <TableCell>Bonus</TableCell>
                  <TableCell>Deductions</TableCell>
                  <TableCell>Net Salary</TableCell>
                  <TableCell>Action</TableCell>
                </TableRow>
              </TableHead>

              <TableBody>
                {payrollList.map((p) => (
                  <TableRow key={p.id}>
                    <TableCell>{p.employee?.empName}</TableCell>
                    <TableCell>{p.month}</TableCell>
                    <TableCell>{p.year}</TableCell>
                    <TableCell>{p.bonus}</TableCell>
                    <TableCell>{p.deductions}</TableCell>
                    <TableCell>₹ {p.netSalary}</TableCell>
                    <TableCell>
                      <Button
                        variant="outlined"
                        size="small"
                        onClick={() => handleDownload(p.id)}
                      >
                        PDF
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </Box>
    </Box>
  );
}

export default Payroll;
