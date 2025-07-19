import React, { useState } from 'react';
import { motion } from 'framer-motion';
import {
  Box,
  Typography,
  Container,
  Grid,
  Card,
  CardContent,
  TextField,
  Button,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  Divider,
  useTheme,
  SelectChangeEvent
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import {
  CalendarMonth as CalendarIcon,
  AccessTime as ClockIcon,
  CheckCircle as CheckCircleIcon,
  Cancel as CancelIcon,
  Pending as PendingIcon,
  EventNote as EventNoteIcon,
  Sick as SickIcon,
  BeachAccess as VacationIcon,
  FamilyRestroom as FamilyIcon,
  School as TrainingIcon
} from '@mui/icons-material';
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

interface LeaveBalance {
  type: string;
  icon: React.ElementType;
  color: string;
  total: number;
  used: number;
  remaining: number;
}

interface LeaveApplication {
  id: number;
  type: string;
  startDate: string;
  endDate: string;
  days: number;
  reason: string;
  status: 'approved' | 'pending' | 'rejected';
  appliedOn: string;
}

const leaveBalances: LeaveBalance[] = [
  {
    type: 'Annual Leave',
    icon: VacationIcon,
    color: '#3b82f6', // blue
    total: 20,
    used: 8,
    remaining: 12
  },
  {
    type: 'Sick Leave',
    icon: SickIcon,
    color: '#ef4444', // red
    total: 10,
    used: 2,
    remaining: 8
  },
  {
    type: 'Family Care',
    icon: FamilyIcon,
    color: '#a855f7', // purple
    total: 5,
    used: 0,
    remaining: 5
  },
  {
    type: 'Training',
    icon: TrainingIcon,
    color: '#f97316', // orange
    total: 5,
    used: 1,
    remaining: 4
  }
];

const leaveApplications: LeaveApplication[] = [
  {
    id: 1,
    type: 'Annual Leave',
    startDate: '2024-06-15',
    endDate: '2024-06-18',
    days: 4,
    reason: 'Family vacation',
    status: 'approved',
    appliedOn: '2024-05-20'
  },
  {
    id: 2,
    type: 'Sick Leave',
    startDate: '2024-05-10',
    endDate: '2024-05-10',
    days: 1,
    reason: 'Doctor appointment',
    status: 'approved',
    appliedOn: '2024-05-09'
  },
  {
    id: 3,
    type: 'Annual Leave',
    startDate: '2024-07-01',
    endDate: '2024-07-05',
    days: 5,
    reason: 'Summer break',
    status: 'pending',
    appliedOn: '2024-06-01'
  },
  {
    id: 4,
    type: 'Training',
    startDate: '2024-05-30',
    endDate: '2024-05-30',
    days: 1,
    reason: 'React conference',
    status: 'rejected',
    appliedOn: '2024-05-15'
  }
];

const getStatusChip = (status: string) => {
  switch (status) {
    case 'approved':
      return <Chip icon={<CheckCircleIcon />} label="Approved" color="success" size="small" />;
    case 'rejected':
      return <Chip icon={<CancelIcon />} label="Rejected" color="error" size="small" />;
    case 'pending':
      return <Chip icon={<PendingIcon />} label="Pending" color="warning" size="small" />;
    default:
      return <Chip label={status} size="small" />;
  }
};

const LeavePage: React.FC = () => {
  const theme = useTheme();
  const [leaveType, setLeaveType] = useState('');
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [reason, setReason] = useState('');

  const handleLeaveTypeChange = (event: SelectChangeEvent) => {
    setLeaveType(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    // In a real app, this would submit the leave application to a backend
    console.log({
      leaveType,
      startDate,
      endDate,
      reason
    });
    
    // Reset form
    setLeaveType('');
    setStartDate(null);
    setEndDate(null);
    setReason('');
    
    // Show success message (in a real app, you'd use a snackbar or toast)
    alert('Leave application submitted successfully!');
  };

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
            <CalendarIcon color="primary" fontSize="large" />
            <Typography 
              variant="h4" 
              component="h1" 
              gutterBottom
              sx={{ fontWeight: 700 }}
            >
              Leave Management
            </Typography>
          </Box>
        </motion.div>

        <Grid container spacing={4}>
          {/* Leave Balance Section */}
          <Grid item xs={12} md={4}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.1 }}
            >
              <Typography variant="h6" sx={{ mb: 2, fontWeight: 600 }}>
                Your Leave Balance
              </Typography>
              
              <Card sx={{ borderRadius: 2, boxShadow: 2 }}>
                <CardContent>
                  <Grid container spacing={2}>
                    {leaveBalances.map((balance, index) => (
                      <Grid item xs={12} key={index}>
                        <Box sx={{ 
                          display: 'flex', 
                          alignItems: 'center', 
                          mb: 1.5,
                          pb: index < leaveBalances.length - 1 ? 1.5 : 0,
                          borderBottom: index < leaveBalances.length - 1 ? '1px solid rgba(0, 0, 0, 0.12)' : 'none'
                        }}>
                          <Box 
                            sx={{ 
                              bgcolor: balance.color,
                              width: 40,
                              height: 40,
                              borderRadius: 1,
                              display: 'flex',
                              alignItems: 'center',
                              justifyContent: 'center',
                              color: 'white',
                              mr: 2
                            }}
                          >
                            <balance.icon />
                          </Box>
                          <Box sx={{ flexGrow: 1 }}>
                            <Typography variant="subtitle1" sx={{ fontWeight: 600 }}>
                              {balance.type}
                            </Typography>
                            <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 0.5 }}>
                              <Typography variant="body2" color="textSecondary">
                                Used: {balance.used}/{balance.total}
                              </Typography>
                              <Typography 
                                variant="body2" 
                                sx={{ 
                                  fontWeight: 600,
                                  color: theme.palette.primary.main
                                }}
                              >
                                Remaining: {balance.remaining}
                              </Typography>
                            </Box>
                          </Box>
                        </Box>
                      </Grid>
                    ))}
                  </Grid>
                </CardContent>
              </Card>
            </motion.div>
          </Grid>

          {/* Leave Application Form */}
          <Grid item xs={12} md={8}>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.2 }}
            >
              <Typography variant="h6" sx={{ mb: 2, fontWeight: 600 }}>
                Apply for Leave
              </Typography>
              
              <Card sx={{ borderRadius: 2, boxShadow: 2 }}>
                <CardContent>
                  <form onSubmit={handleSubmit}>
                    <Grid container spacing={3}>
                      <Grid item xs={12}>
                        <FormControl fullWidth required>
                          <InputLabel id="leave-type-label">Leave Type</InputLabel>
                          <Select
                            labelId="leave-type-label"
                            id="leave-type"
                            value={leaveType}
                            label="Leave Type"
                            onChange={handleLeaveTypeChange}
                          >
                            <MenuItem value="Annual Leave">Annual Leave</MenuItem>
                            <MenuItem value="Sick Leave">Sick Leave</MenuItem>
                            <MenuItem value="Family Care">Family Care</MenuItem>
                            <MenuItem value="Training">Training</MenuItem>
                          </Select>
                        </FormControl>
                      </Grid>
                      
                      <Grid item xs={12} sm={6}>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                          <DatePicker
                            label="Start Date"
                            value={startDate}
                            onChange={(newValue) => setStartDate(newValue)}
                            slotProps={{ textField: { fullWidth: true, required: true } }}
                          />
                        </LocalizationProvider>
                      </Grid>
                      
                      <Grid item xs={12} sm={6}>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                          <DatePicker
                            label="End Date"
                            value={endDate}
                            onChange={(newValue) => setEndDate(newValue)}
                            slotProps={{ textField: { fullWidth: true, required: true } }}
                            minDate={startDate || undefined}
                          />
                        </LocalizationProvider>
                      </Grid>
                      
                      <Grid item xs={12}>
                        <TextField
                          id="reason"
                          label="Reason for Leave"
                          multiline
                          rows={4}
                          fullWidth
                          required
                          value={reason}
                          onChange={(e) => setReason(e.target.value)}
                        />
                      </Grid>
                      
                      <Grid item xs={12}>
                        <Button 
                          type="submit" 
                          variant="contained" 
                          color="primary"
                          size="large"
                          sx={{ 
                            mt: 2,
                            px: 4,
                            py: 1.5,
                            borderRadius: 2,
                            background: 'linear-gradient(to right, #3b82f6, #4f46e5)',
                            '&:hover': {
                              background: 'linear-gradient(to right, #2563eb, #4338ca)',
                            }
                          }}
                        >
                          Submit Application
                        </Button>
                      </Grid>
                    </Grid>
                  </form>
                </CardContent>
              </Card>
            </motion.div>
          </Grid>

          {/* Leave History */}
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
                  Leave History
                </Typography>
                <Button 
                  startIcon={<EventNoteIcon />}
                  variant="outlined"
                  sx={{ borderRadius: 2 }}
                >
                  View All
                </Button>
              </Box>
              
              <TableContainer component={Paper} sx={{ borderRadius: 2, boxShadow: 2 }}>
                <Table>
                  <TableHead sx={{ bgcolor: 'grey.100' }}>
                    <TableRow>
                      <TableCell>Type</TableCell>
                      <TableCell>Period</TableCell>
                      <TableCell>Days</TableCell>
                      <TableCell>Reason</TableCell>
                      <TableCell>Applied On</TableCell>
                      <TableCell>Status</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {leaveApplications.map((application) => (
                      <TableRow 
                        key={application.id}
                        sx={{ 
                          '&:last-child td, &:last-child th': { border: 0 },
                          transition: 'background-color 0.3s',
                          '&:hover': { bgcolor: 'rgba(0, 0, 0, 0.03)' }
                        }}
                      >
                        <TableCell>{application.type}</TableCell>
                        <TableCell>
                          {new Date(application.startDate).toLocaleDateString()}
                          {application.startDate !== application.endDate && 
                            ` - ${new Date(application.endDate).toLocaleDateString()}`}
                        </TableCell>
                        <TableCell>{application.days}</TableCell>
                        <TableCell>{application.reason}</TableCell>
                        <TableCell>{new Date(application.appliedOn).toLocaleDateString()}</TableCell>
                        <TableCell>{getStatusChip(application.status)}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </motion.div>
          </Grid>
        </Grid>
      </Container>

      {/* Chatbot */}
      <ChatBot />
    </Box>
  );
};

export default LeavePage;