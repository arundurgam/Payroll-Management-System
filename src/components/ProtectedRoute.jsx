import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children, allowedRoles }) {
  const { token, role } = useSelector((state) => state.auth);

  if (!token) return <Navigate to="/login" />;

  if (allowedRoles && !allowedRoles.includes(role))
    return <Navigate to="/dashboard" />;

  return children;
}

export default ProtectedRoute;
