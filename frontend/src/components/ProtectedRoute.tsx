import { ReactNode, useEffect, useState } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { Box, CircularProgress } from '@mui/material';
import { useAuth } from '../contexts/AuthContext';

interface ProtectedRouteProps {
  children: ReactNode;
  requiredRoles?: string[];
}

const ProtectedRoute = ({ children, requiredRoles = [] }: ProtectedRouteProps) => {
  const { isAuthenticated, isLoading, user, checkAuth } = useAuth();
  const location = useLocation();
  const [checking, setChecking] = useState(true);

  useEffect(() => {
    const verifyAuth = async () => {
      await checkAuth();
      setChecking(false);
    };

    verifyAuth();
  }, [checkAuth]);

  if (isLoading || checking) {
    return (
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          height: '100vh',
        }}
      >
        <CircularProgress />
      </Box>
    );
  }

  if (!isAuthenticated) {
    // Redirect to login page with return URL
    return <Navigate to="/login" state={{ from: location.pathname }} replace />;
  }

  // Check for required roles if specified
  if (requiredRoles.length > 0 && user) {
    const hasRequiredRole = requiredRoles.includes(user.role);
    if (!hasRequiredRole) {
      // User doesn't have the required role, redirect to unauthorized page
      return <Navigate to="/unauthorized" replace />;
    }
  }

  return <>{children}</>;
};

export default ProtectedRoute;