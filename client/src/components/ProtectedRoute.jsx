import React from "react";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }) {
  const token = localStorage.getItem("token"); //TODO change on verification from context
  if (!token) {
    return <Navigate to="/registration" replace />;
  }
  return children;
}

export default ProtectedRoute;
