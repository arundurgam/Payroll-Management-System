import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Employees from "./pages/Employees";
import Payroll from "./pages/Payroll";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* ========== PUBLIC ROUTES ========== */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* ========== DASHBOARD (ADMIN + HR) ========== */}
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute allowedRoles={["ADMIN", "HR"]}>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        {/* ========== EMPLOYEES (ADMIN + HR) ========== */}
        <Route
          path="/employees"
          element={
            <ProtectedRoute allowedRoles={["ADMIN", "HR"]}>
              <Employees />
            </ProtectedRoute>
          }
        />

        {/* ========== PAYROLL (ADMIN + HR) ========== */}
        <Route
          path="/payroll"
          element={
            <ProtectedRoute allowedRoles={["ADMIN", "HR"]}>
              <Payroll />
            </ProtectedRoute>
          }
        />

        {/* ========== DEFAULT ROUTE ========== */}
        <Route path="*" element={<Navigate to="/login" />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
