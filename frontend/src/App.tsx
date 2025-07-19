import { Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { Suspense, lazy } from 'react';
import { Box, CircularProgress } from '@mui/material';

// Components
import ErrorBoundary from './components/ErrorBoundary';
import MainLayout from './components/layout/MainLayout';
import ProtectedRoute from './components/ProtectedRoute';

// Context Providers
import { AuthProvider } from './contexts/AuthContext';

// Lazy-loaded Pages
const HomePage = lazy(() => import('./pages/Home'));
const LoginPage = lazy(() => import('./pages/Login'));
const DashboardPage = lazy(() => import('./pages/Dashboard'));
const LeavePage = lazy(() => import('./pages/Leave'));
const AttendancePage = lazy(() => import('./pages/Attendance'));
const HRPage = lazy(() => import('./pages/HR'));
const ProfilePage = lazy(() => import('./pages/Profile'));
const SettingsPage = lazy(() => import('./pages/Settings'));
const UnauthorizedPage = lazy(() => import('./pages/Unauthorized'));
const NotFoundPage = lazy(() => import('./pages/NotFound'));

// Loading component for suspense fallback
const LoadingFallback = () => (
  <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
    <CircularProgress />
  </Box>
);

// Create a theme instance
const theme = createTheme({
  palette: {
    primary: {
      main: '#3b82f6', // blue-600
      dark: '#2563eb', // blue-700
      light: '#60a5fa', // blue-400
    },
    secondary: {
      main: '#4f46e5', // indigo-600
      dark: '#4338ca', // indigo-700
      light: '#818cf8', // indigo-400
    },
    background: {
      default: '#f8fafc', // slate-50
      paper: '#ffffff',
    },
    text: {
      primary: '#1e293b', // slate-800
      secondary: '#64748b', // slate-500
    },
  },
  typography: {
    fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',
    h1: {
      fontWeight: 700,
    },
    h2: {
      fontWeight: 700,
    },
    h3: {
      fontWeight: 600,
    },
    h4: {
      fontWeight: 600,
    },
    h5: {
      fontWeight: 500,
    },
    h6: {
      fontWeight: 500,
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          textTransform: 'none',
          fontWeight: 500,
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
        },
      },
    },
  },
});

function App() {
  return (
    <ErrorBoundary>
      <AuthProvider>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <Suspense fallback={<LoadingFallback />}>
            <Routes>
              {/* Public routes */}
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/unauthorized" element={<UnauthorizedPage />} />
              
              {/* Protected routes with layout */}
              <Route path="/dashboard" element={
                <ProtectedRoute>
                  <MainLayout>
                    <DashboardPage />
                  </MainLayout>
                </ProtectedRoute>
              } />
              
              <Route path="/leave" element={
                <ProtectedRoute>
                  <MainLayout>
                    <LeavePage />
                  </MainLayout>
                </ProtectedRoute>
              } />
              
              <Route path="/attendance" element={
                <ProtectedRoute>
                  <MainLayout>
                    <AttendancePage />
                  </MainLayout>
                </ProtectedRoute>
              } />
              
              <Route path="/hr" element={
                <ProtectedRoute requiredRoles={['ADMIN', 'HR_ADMIN']}>
                  <MainLayout>
                    <HRPage />
                  </MainLayout>
                </ProtectedRoute>
              } />
              
              <Route path="/profile" element={
                <ProtectedRoute>
                  <MainLayout>
                    <ProfilePage />
                  </MainLayout>
                </ProtectedRoute>
              } />
              
              <Route path="/settings" element={
                <ProtectedRoute>
                  <MainLayout>
                    <SettingsPage />
                  </MainLayout>
                </ProtectedRoute>
              } />
              
              {/* Catch all route */}
              <Route path="*" element={<NotFoundPage />} />
            </Routes>
          </Suspense>
        </ThemeProvider>
      </AuthProvider>
    </ErrorBoundary>
  );
}

export default App;