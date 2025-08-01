import { Box, Button, Container, Typography } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { SentimentVeryDissatisfied } from '@mui/icons-material';

const NotFound = () => {
  return (
    <Container maxWidth="md">
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: '100vh',
          textAlign: 'center',
          py: 5,
        }}
      >
        <SentimentVeryDissatisfied sx={{ fontSize: 100, color: 'text.secondary', mb: 4 }} />
        <Typography variant="h1" component="h1" gutterBottom>
          404
        </Typography>
        <Typography variant="h4" component="h2" gutterBottom>
          Page Not Found
        </Typography>
        <Typography variant="body1" color="text.secondary" paragraph sx={{ maxWidth: 480 }}>
          The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.
        </Typography>
        <Box sx={{ mt: 4, display: 'flex', gap: 2 }}>
          <Button
            component={RouterLink}
            to="/"
            variant="contained"
            color="primary"
            size="large"
          >
            Go to Home
          </Button>
          <Button
            component={RouterLink}
            to="/dashboard"
            variant="outlined"
            size="large"
          >
            Go to Dashboard
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default NotFound;