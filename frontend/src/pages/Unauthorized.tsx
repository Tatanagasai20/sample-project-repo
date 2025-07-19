import { Box, Button, Container, Typography, Paper } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { LockOutlined } from '@mui/icons-material';
import { useAuth } from '../contexts/AuthContext';

const Unauthorized = () => {
  const { user } = useAuth();

  return (
    <Container maxWidth="md">
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: '100vh',
          py: 5,
        }}
      >
        <Paper
          elevation={0}
          sx={{
            p: 5,
            borderRadius: 2,
            textAlign: 'center',
            border: '1px solid',
            borderColor: 'warning.light',
            bgcolor: 'warning.lighter',
            maxWidth: 600,
            width: '100%',
          }}
        >
          <LockOutlined sx={{ fontSize: 80, color: 'warning.main', mb: 3 }} />
          <Typography variant="h4" component="h1" gutterBottom>
            Access Denied
          </Typography>
          <Typography variant="body1" color="text.secondary" paragraph>
            You don't have permission to access this page. This area requires higher privileges than your current role
            {user?.role && <strong> ({user.role})</strong>} provides.
          </Typography>
          <Box sx={{ mt: 4, display: 'flex', justifyContent: 'center', gap: 2 }}>
            <Button
              component={RouterLink}
              to="/dashboard"
              variant="contained"
              color="primary"
              size="large"
            >
              Go to Dashboard
            </Button>
            <Button
              component={RouterLink}
              to="/"
              variant="outlined"
              size="large"
            >
              Go to Home
            </Button>
          </Box>
        </Paper>
      </Box>
    </Container>
  );
};

export default Unauthorized;