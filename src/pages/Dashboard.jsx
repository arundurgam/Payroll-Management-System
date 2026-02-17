import { useEffect, useState } from "react";
import {
  Box,
  Grid,
  Card,
  CardContent,
  Typography,
  CircularProgress,
} from "@mui/material";
import Sidebar from "../components/Sidebar";
import Navbar from "../components/Navbar";
import { getDashboardSummary } from "../api/dashboardApi";

function Dashboard() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  const currentYear = new Date().getFullYear();

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        const res = await getDashboardSummary(currentYear);
        setData(res.data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, []);

  if (loading) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", mt: 20 }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ display: "flex" }}>
      <Sidebar />

      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Navbar />

        <Box sx={{ mt: 10 }}>
          <Typography variant="h4" gutterBottom>
            Dashboard Overview ({currentYear})
          </Typography>

          <Grid container spacing={3}>
            
            {/* Total Employees */}
            <Grid item xs={12} md={3}>
              <Card sx={{ backgroundColor: "#1976d2", color: "white" }}>
                <CardContent>
                  <Typography variant="h6">Total Employees</Typography>
                  <Typography variant="h4">
                    {data?.totalEmployees}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            {/* Total Salary Paid */}
            <Grid item xs={12} md={3}>
              <Card sx={{ backgroundColor: "#2e7d32", color: "white" }}>
                <CardContent>
                  <Typography variant="h6">
                    Total Salary Paid
                  </Typography>
                  <Typography variant="h5">
                    ₹ {data?.totalSalary?.toLocaleString()}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            {/* Highest Paid Employee */}
            <Grid item xs={12} md={3}>
              <Card sx={{ backgroundColor: "#ed6c02", color: "white" }}>
                <CardContent>
                  <Typography variant="h6">
                    Highest Paid Employee
                  </Typography>
                  <Typography variant="h6">
                    {data?.highestEmployee?.empName}
                  </Typography>
                  <Typography variant="body2">
                    ₹ {data?.highestEmployee?.baseSalary}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            {/* Highest Payroll Record */}
            <Grid item xs={12} md={3}>
              <Card sx={{ backgroundColor: "#9c27b0", color: "white" }}>
                <CardContent>
                  <Typography variant="h6">
                    Highest Payroll Record
                  </Typography>
                  <Typography variant="body1">
                    ₹ {data?.highestPayroll?.netSalary}
                  </Typography>
                  <Typography variant="body2">
                    {data?.highestPayroll?.month}{" "}
                    {data?.highestPayroll?.year}
                  </Typography>
                  <Typography variant="body2">
                    {data?.highestPayroll?.employee?.empName}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

          </Grid>
        </Box>
      </Box>
    </Box>
  );
}

export default Dashboard;
