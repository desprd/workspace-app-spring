import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/ContextProvider";

function ProtectedRoute({ children }) {
  const { username } = useAuth();
  if (username === null) {
    return <Navigate to="/registration" replace />;
  }
  return children;
}

export default ProtectedRoute;
