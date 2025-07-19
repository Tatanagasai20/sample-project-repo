import { Component, ErrorInfo, ReactNode } from 'react';
import { Box, Button, Container, Typography, Paper } from '@mui/material';
import { ErrorOutline } from '@mui/icons-material';

interface Props {
  children: ReactNode;
  fallback?: ReactNode;
}

interface State {
  hasError: boolean;
  error: Error | null;
  errorInfo: ErrorInfo | null;
}

class ErrorBoundary extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      hasError: false,
      error: null,
      errorInfo: null,
    };
  }

  static getDerivedStateFromError(error: Error): State {
    // Update state so the next render will show the fallback UI.
    return { hasError: true, error, errorInfo: null };
  }

  componentDidCatch(error: Error, errorInfo: ErrorInfo): void {
    // You can also log the error to an error reporting service
    console.error('Error caught by ErrorBoundary:', error, errorInfo);
    this.setState({ errorInfo });
  }

  handleReset = (): void => {
    this.setState({ hasError: false, error: null, errorInfo: null });
  };

  render(): ReactNode {
    if (this.state.hasError) {
      if (this.props.fallback) {
        return this.props.fallback;
      }

      // Default fallback UI
      return (
        <Container maxWidth="md" sx={{ py: 8 }}>
          <Paper
            elevation={0}
            sx={{
              p: 4,
              borderRadius: 2,
              textAlign: 'center',
              border: '1px solid',
              borderColor: 'error.light',
              bgcolor: 'error.lighter',
            }}
          >
            <ErrorOutline color="error" sx={{ fontSize: 60, mb: 2 }} />
            <Typography variant="h4" color="error" gutterBottom>
              Something went wrong
            </Typography>
            <Typography variant="body1" color="text.secondary" paragraph>
              The application encountered an unexpected error. Please try again or contact support if the problem persists.
            </Typography>

            {process.env.NODE_ENV === 'development' && this.state.error && (
              <Box sx={{ mt: 4, textAlign: 'left' }}>
                <Typography variant="subtitle2" gutterBottom>
                  Error details (visible in development mode only):
                </Typography>
                <Box
                  component="pre"
                  sx={{
                    p: 2,
                    borderRadius: 1,
                    bgcolor: 'background.paper',
                    color: 'error.main',
                    overflowX: 'auto',
                    fontSize: '0.875rem',
                    border: '1px solid',
                    borderColor: 'divider',
                  }}
                >
                  {this.state.error.toString()}
                  {this.state.errorInfo && this.state.errorInfo.componentStack}
                </Box>
              </Box>
            )}

            <Box sx={{ mt: 4, display: 'flex', justifyContent: 'center', gap: 2 }}>
              <Button
                variant="contained"
                color="primary"
                onClick={() => window.location.reload()}
              >
                Reload Page
              </Button>
              <Button variant="outlined" onClick={this.handleReset}>
                Try Again
              </Button>
            </Box>
          </Paper>
        </Container>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;