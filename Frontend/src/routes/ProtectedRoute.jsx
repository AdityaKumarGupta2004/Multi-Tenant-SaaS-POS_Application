import React from "react";
import { useSelector } from "react-redux";
import { Navigate, useLocation } from "react-router";

const ProtectedRoute = ({ children, roles }) => {

  const { userProfile, loading } = useSelector((state) => state.user);
  const location = useLocation();

  const jwt = localStorage.getItem("jwt");

  console.log("ProtectedRoute:", userProfile, loading);

  // ⛔ WAIT until auth check finishes
  if (loading) {
    return <div>Loading...</div>;
  }

  // ⛔ If no token at all → go login
  if (!jwt) {
    return <Navigate to="/auth/login" replace />;
  }

  // ⛔ If token exists but userProfile still null
  if (!userProfile) {
    return null;
  }

  const userHasRequiredRole = roles?.includes(userProfile.role);

  if (!userHasRequiredRole) {
    return <Navigate to="/" replace />;
  }

  return children;
};

export default ProtectedRoute;