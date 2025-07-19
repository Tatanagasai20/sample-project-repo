import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import {
  Box,
  Typography,
  Container,
  Grid,
  Card,
  CardContent,
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  Divider,
  LinearProgress,
  useTheme,
  Snackbar,
  Alert
} from '@mui/material';
import {
  AccessTime as ClockIcon,
  Login as LoginIcon,
  Logout as LogoutIcon,
  CalendarMonth as CalendarIcon,
  CheckCircle as CheckCircleIcon,
  Cancel as CancelIcon,
  Timeline as TimelineIcon,
  TrendingUp as TrendingUpIcon,
  EventNote as EventNoteIcon
} from '@mui/icons-material';
import { 
  BarChart, 
  Bar, 
  XAxis, 
  YAxis, 
  CartesianGrid, 
  Tooltip, 
  ResponsiveContainer,
  Cell
} from 'recharts';
import Header from '../components/Header';
import ChatBot from '../components/ChatBot';

const fadeInUp = {
  initial: { opacity: 0, y: 60 },
  animate: { opacity: 1, y: 0 },
  transition: { duration: 0.6 }
};

const staggerContainer = {
  animate: {
    transition: {
      staggerChildren: 0.1
    }
  }
};

interface AttendanceRecord {
  id: number;
  date: string;
  checkIn: string;
  checkOut: string | null;
  workHours: string | null;
  status: 'present' | 'late' | 'absent' | 'half-day' | 'weekend' | 'holiday';
}

interface WeeklyData {
  day: string;
  hours: number;
  target: number;
}

const weeklyData: WeeklyData[] = [
  { day: 'Mon', hours: 8.5, target: 8 },
  { day: 'Tue', hours: 8.2, target: 8 },
  { day: 'Wed', hours: 7.8, target: 8 },
  { day: 'Thu', hours: 8.0, target: 8 },
  { day: 'Fri', hours: 7.5, target: 8 },
  { day: 'Sat', hours: 0, target: 0 },
  { day: 'Sun', hours: 0, target: 0 }
];

const attendanceRecords: AttendanceRecord[] = [
  {
    id: 1,
    date: '2024-06-10',
    checkIn: '09:05:23',
    checkOut: '17:30:45',
    workHours: '8:25',
    status: 'present'
  },
  {
    id: 2,
    date: '2024-06-09',
    checkIn: '09:15:10',
    checkOut: '17:45:32',
    workHours: '8:30',
    status: 'late'
  },
  {
    id: 3,
    date: '2024-06-08',
    checkIn: null,
    checkOut: null,
    workHours: null,
    status: 'weekend'
  },
  {
    id: 4,
    date: '2024-06-07',
    checkIn: '08:55:42',
    checkOut: '17:10:18',
    workHours: '8:15',
    status: 'present'
  },
  {
    id: 5,
    date: '2024-06-06',
    checkIn: '09:02:33',
    checkOut: '16:30:21',
    workHours: '7:28',
    status: 'half-day'
  },
  {
    id: 6,
    date: '2024-06-05',
    checkIn: null,
    checkOut: null,
    workHours: null,
    status: 'holiday'
  },
  {
    id: 7,
    date: '2024-06-04',
    checkIn: null,
    checkOut: null,
    workHours: null,
    status: 'absent'
  }
];

const getStatusChip = (status: string) => {
  switch (status) {
    case 'present':
      return <Chip icon={<CheckCircleIcon />} label="Present" color="success" size="small" />;
    case 'late':
      return <Chip icon={<ClockIcon />} label="Late" color="warning" size="small" />;
    case 'absent':
      return <Chip icon={<CancelIcon />} label="Absent" color="error" size="small" />;
    case 'half-day':
      return <Chip icon={<TimelineIcon />} label="Half Day" color="info" size="small" />;
    case 'weekend':
      return <Chip label="Weekend" size="small" color="default" />;
    case 'holiday':
      return <Chip icon={<CalendarIcon />} label="Holiday" size="small" color="secondary" />;
    default:
      return <Chip label={status} size="small" />;
  }
};

const AttendancePage: React.FC = () => {
  const theme = useTheme();
  const [currentTime, setCurrentTime] = useState(new Date());
  const [checkedIn, setCheckedIn] = useState(false);
  const [checkInTime, setCheckInTime] = useState<Date | null>(null);
  const [checkOutTime, setCheckOutTime] = useState<Date | null>(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error' | 'info' | 'warning'>('success');

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  const handleCheckIn = () => {
    const now = new Date();
    setCheckInTime(now);
    setCheckedIn(true);
    setSnackbarMessage(`Successfully checked in at ${now.toLocaleTimeString()}`);
    setSnackbarSeverity('success');
    setSnackbarOpen(true);
  };

  const handleCheckOut = () => {
    const now = new Date();
    setCheckOutTime(now);
    setCheckedIn(false);
    
    // Calculate work hours
    if (checkInTime) {
      const diffMs = now.getTime() - checkInTime.getTime();
      const diffHrs = Math.round(diffMs / 3600000 * 10) / 10; // Round to 1 decimal place
      setSnackbarMessage(`Successfully checked out at ${now.toLocaleTimeString()}. You worked for ${diffHrs} hours today.`);
    } else {
      setSnackbarMessage(`Successfully checked out at ${now.toLocaleTimeString()}.`);
    }
    
    setSnackbarSeverity('success');
    setSnackbarOpen(true);
  };

  const handleCloseSnackbar = () => {
    setSnackbarOpen(false);
  };

  // Calculate weekly statistics
  const totalHours = weeklyData.reduce((sum, day) => sum + day.hours, 0);
  const targetHours = weeklyData.reduce((sum, day) => sum + day.target, 0);
  const attendanceRate = (totalHours / targetHours) * 100;

  return (
    <Box sx={{ minHeight: '100vh', bgcolor: 'grey.50' }}>
      <Header />
      
      <Container maxWidth="lg" sx={{ py: 4 }}>
        {/* Page Title */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
        >
          <Box sx={{ mb: 4, display: 'flex', alignItems: 'center', gap: 1 }}>
            <ClockIcon color="primary" fontSize="large" />
            <Typography 
              variant="h4" 
              component="h1" 
              gutterBottom
              sx={{ fontWeight: 700 }}
            >
              Attendance Management
            </Typography>
          </Box>
        </motion.div>

        <Grid container spacing={4}>
          {/* Check In/Out Section */}
          <Grid item xs={12} md={6}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.1 }}
            >
              <Card 
                sx={{ 
                  borderRadius: 2, 
                  boxShadow: 3,
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column'
                }}
              >
                <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                  <Box sx={{ textAlign: 'center', mb: 3 }}>
                    <Typography variant="h6" gutterBottom sx={{ fontWeight: 600 }}>
                      Today's Attendance
                    </Typography>
                    <Typography variant="h3" sx={{ fontWeight: 700, my: 2 }}>
                      {currentTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' })}
                    </Typography>
                    <Typography variant="body1" color="textSecondary" gutterBottom>
                      {currentTime.toLocaleDateString([], { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}
                    </Typography>
                  </Box>
                  
                  <Divider sx={{ my: 2 }} />
                  
                  <Box sx={{ flexGrow: 1 }}>
                    <Grid container spacing={2}>
                      <Grid item xs={6}>
                        <Box sx={{ textAlign: 'center' }}>
                          <Typography variant="subtitle2" color="textSecondary" gutterBottom>
                            Check In
                          </Typography>
                          <Typography variant="body1" sx={{ fontWeight: 600 }}>
                            {checkInTime ? checkInTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : '--:--'}
                          </Typography>
                        </Box>
                      </Grid>
                      <Grid item xs={6}>
                        <Box sx={{ textAlign: 'center' }}>
                          <Typography variant="subtitle2" color="textSecondary" gutterBottom>
                            Check Out
                          </Typography>
                          <Typography variant="body1" sx={{ fontWeight: 600 }}>
                            {checkOutTime ? checkOutTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : '--:--'}
                          </Typography>
                        </Box>
                      </Grid>
                    </Grid>
                  </Box>
                  
                  <Box sx={{ display: 'flex', justifyContent: 'center', gap: 2, mt: 4 }}>
                    <Button
                      variant="contained"
                      color="primary"
                      size="large"
                      startIcon={<LoginIcon />}
                      onClick={handleCheckIn}
                      disabled={checkedIn}
                      sx={{ 
                        px: 3,
                        py: 1.5,
                        borderRadius: 2,
                        background: !checkedIn ? 'linear-gradient(to right, #3b82f6, #4f46e5)' : undefined,
                        '&:not(:disabled):hover': {
                          background: 'linear-gradient(to right, #2563eb, #4338ca)',
                        }
                      }}
                    >
                      Check In
                    </Button>
                    <Button
                      variant="contained"
                      color="secondary"
                      size="large"
                      startIcon={<LogoutIcon />}
                      onClick={handleCheckOut}
                      disabled={!checkedIn || !!checkOutTime}
                      sx={{ 
                        px: 3,
                        py: 1.5,
                        borderRadius: 2
                      }}
                    >
                      Check Out
                    </Button>
                  </Box>
                </CardContent>
              </Card>
            </motion.div>
          </Grid>

          {/* Weekly Statistics */}
          <Grid item xs={12} md={6}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.2 }}
            >
              <Card 
                sx={{ 
                  borderRadius: 2, 
                  boxShadow: 3,
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column'
                }}
              >
                <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                  <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                    <Typography variant="h6" sx={{ fontWeight: 600 }}>
                      Weekly Statistics
                    </Typography>
                    <TrendingUpIcon color="primary" />
                  </Box>
                  
                  <Box sx={{ height: 250, mt: 2 }}>
                    <ResponsiveContainer width="100%" height="100%">
                      <BarChart
                        data={weeklyData}
                        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                      >
                        <CartesianGrid strokeDasharray="3 3" vertical={false} />
                        <XAxis dataKey="day" />
                        <YAxis />
                        <Tooltip />
                        <Bar dataKey="hours" fill="#3b82f6" radius={[4, 4, 0, 0]}>
                          {weeklyData.map((entry, index) => (
                            <Cell 
                              key={`cell-${index}`} 
                              fill={entry.hours >= entry.target ? '#22c55e' : (entry.hours > 0 ? '#3b82f6' : '#e5e7eb')} 
                            />
                          ))}
                        </Bar>
                      </BarChart>
                    </ResponsiveContainer>
                  </Box>
                  
                  <Box sx={{ mt: 'auto', pt: 2 }}>
                    <Grid container spacing={2}>
                      <Grid item xs={4}>
                        <Box sx={{ textAlign: 'center' }}>
                          <Typography variant="subtitle2" color="textSecondary" gutterBottom>
                            Total Hours
                          </Typography>
                          <Typography variant="h6" sx={{ fontWeight: 700 }}>
                            {totalHours.toFixed(1)}h
                          </Typography>
                        </Box>
                      </Grid>
                      <Grid item xs={4}>
                        <Box sx={{ textAlign: 'center' }}>
                          <Typography variant="subtitle2" color="textSecondary" gutterBottom>
                            Target
                          </Typography>
                          <Typography variant="h6" sx={{ fontWeight: 700 }}>
                            {targetHours.toFixed(1)}h
                          </Typography>
                        </Box>
                      </Grid>
                      <Grid item xs={4}>
                        <Box sx={{ textAlign: 'center' }}>
                          <Typography variant="subtitle2" color="textSecondary" gutterBottom>
                            Rate
                          </Typography>
                          <Typography 
                            variant="h6" 
                            sx={{ 
                              fontWeight: 700,
                              color: attendanceRate >= 100 ? 'success.main' : 
                                     attendanceRate >= 90 ? 'primary.main' : 'warning.main'
                            }}
                          >
                            {attendanceRate.toFixed(1)}%
                          </Typography>
                        </Box>
                      </Grid>
                    </Grid>
                    
                    <Box sx={{ mt: 2 }}>
                      <LinearProgress 
                        variant="determinate" 
                        value={Math.min(attendanceRate, 100)} 
                        sx={{ 
                          height: 8, 
                          borderRadius: 4,
                          bgcolor: 'grey.200',
                          '& .MuiLinearProgress-bar': {
                            bgcolor: attendanceRate >= 100 ? 'success.main' : 
                                    attendanceRate >= 90 ? 'primary.main' : 'warning.main'
                          }
                        }}
                      />
                    </Box>
                  </Box>
                </CardContent>
              </Card>
            </motion.div>
          </Grid>

          {/* Attendance History */}
          <Grid item xs={12}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.3 }}
            >
              <Box sx={{ 
                display: 'flex', 
                justifyContent: 'space-between', 
                alignItems: 'center',
                mb: 2 
              }}>
                <Typography variant="h6" sx={{ fontWeight: 600 }}>
                  Attendance History
                </Typography>
                <Button 
                  startIcon={<EventNoteIcon />}
                  variant="outlined"
                  sx={{ borderRadius: 2 }}
                >
                  View Full History
                </Button>
              </Box>
              
              <TableContainer component={Paper} sx={{ borderRadius: 2, boxShadow: 2 }}>
                <Table>
                  <TableHead sx={{ bgcolor: 'grey.100' }}>
                    <TableRow>
                      <TableCell>Date</TableCell>
                      <TableCell>Check In</TableCell>
                      <TableCell>Check Out</TableCell>
                      <TableCell>Work Hours</TableCell>
                      <TableCell>Status</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {attendanceRecords.map((record) => (
                      <TableRow 
                        key={record.id}
                        sx={{ 
                          '&:last-child td, &:last-child th': { border: 0 },
                          transition: 'background-color 0.3s',
                          '&:hover': { bgcolor: 'rgba(0, 0, 0, 0.03)' },
                          bgcolor: record.status === 'weekend' || record.status === 'holiday' ? 'rgba(0, 0, 0, 0.02)' : 'inherit'
                        }}
                      >
                        <TableCell>
                          {new Date(record.date).toLocaleDateString([], { weekday: 'short', month: 'short', day: 'numeric' })}
                        </TableCell>
                        <TableCell>{record.checkIn || '-'}</TableCell>
                        <TableCell>{record.checkOut || '-'}</TableCell>
                        <TableCell>{record.workHours || '-'}</TableCell>
                        <TableCell>{getStatusChip(record.status)}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </motion.div>
          </Grid>
        </Grid>
      </Container>

      {/* Snackbar for notifications */}
      <Snackbar 
        open={snackbarOpen} 
        autoHideDuration={6000} 
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert 
          onClose={handleCloseSnackbar} 
          severity={snackbarSeverity} 
          variant="filled"
          sx={{ width: '100%' }}
        >
          {snackbarMessage}
        </Alert>
      </Snackbar>

      {/* Chatbot */}
      <ChatBot />
    </Box>
  );
};

export default AttendancePage;