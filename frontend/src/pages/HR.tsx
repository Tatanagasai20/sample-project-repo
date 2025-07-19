import React from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import {
  Box,
  Typography,
  Container,
  Grid,
  Card,
  CardContent,
  CardActionArea,
  Avatar,
  List,
  ListItem,
  ListItemText,
  ListItemAvatar,
  Divider,
  Paper,
  Button,
  useTheme
} from '@mui/material';
import {
  People as PeopleIcon,
  PersonAdd as PersonAddIcon,
  Assessment as AssessmentIcon,
  Payments as PaymentsIcon,
  BarChart as BarChartIcon,
  Settings as SettingsIcon,
  Notifications as NotificationsIcon,
  CheckCircle as CheckCircleIcon,
  Pending as PendingIcon,
  Cancel as CancelIcon,
  ArrowUpward as ArrowUpwardIcon,
  ArrowDownward as ArrowDownwardIcon,
  EventNote as EventNoteIcon
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

const quickStats = [
  {
    title: "Total Employees",
    value: "248",
    icon: PeopleIcon,
    color: "#3b82f6", // blue
    change: "+12",
    changeDirection: "up"
  },
  {
    title: "Open Positions",
    value: "15",
    icon: PersonAddIcon,
    color: "#22c55e", // green
    change: "+3",
    changeDirection: "up"
  },
  {
    title: "Pending Reviews",
    value: "32",
    icon: AssessmentIcon,
    color: "#a855f7", // purple
    change: "-5",
    changeDirection: "down"
  },
  {
    title: "Payroll Status",
    value: "Processed",
    icon: PaymentsIcon,
    color: "#f97316", // orange
    change: "On Time",
    changeDirection: "up"
  }
];

const hrModules = [
  {
    title: "Employee Management",
    description: "Manage employee profiles, departments, and organizational structure",
    icon: PeopleIcon,
    color: "#3b82f6", // blue
    link: "/"
  },
  {
    title: "Recruitment",
    description: "Track job openings, applications, interviews, and hiring processes",
    icon: PersonAddIcon,
    color: "#22c55e", // green
    link: "/"
  },
  {
    title: "Performance",
    description: "Conduct reviews, set goals, and track employee performance metrics",
    icon: AssessmentIcon,
    color: "#a855f7", // purple
    link: "/"
  },
  {
    title: "Payroll & Benefits",
    description: "Manage compensation, benefits, and payroll processing",
    icon: PaymentsIcon,
    color: "#f97316", // orange
    link: "/"
  },
  {
    title: "Reports & Analytics",
    description: "Generate insights with comprehensive HR reports and dashboards",
    icon: BarChartIcon,
    color: "#ec4899", // pink
    link: "/"
  },
  {
    title: "HR Settings",
    description: "Configure company policies, workflows, and system preferences",
    icon: SettingsIcon,
    color: "#64748b", // slate
    link: "/"
  }
];

const recentActivities = [
  {
    id: 1,
    type: "employee",
    title: "New Employee Onboarded",
    description: "Sarah Johnson has completed the onboarding process",
    time: "2 hours ago",
    status: "completed",
    icon: CheckCircleIcon
  },
  {
    id: 2,
    type: "recruitment",
    title: "Interview Scheduled",
    description: "Interview for Senior Developer position scheduled for June 15",
    time: "5 hours ago",
    status: "pending",
    icon: PendingIcon
  },
  {
    id: 3,
    type: "performance",
    title: "Performance Review Cycle",
    description: "Q2 performance review cycle has been initiated",
    time: "1 day ago",
    status: "in-progress",
    icon: PendingIcon
  },
  {
    id: 4,
    type: "payroll",
    title: "Payroll Processed",
    description: "May 2024 payroll has been processed successfully",
    time: "3 days ago",
    status: "completed",
    icon: CheckCircleIcon
  },
  {
    id: 5,
    type: "employee",
    title: "Resignation Submitted",
    description: "Michael Chen has submitted his resignation",
    time: "1 week ago",
    status: "pending",
    icon: PendingIcon
  }
];

const getStatusColor = (status: string) => {
  switch (status) {
    case 'completed':
      return '#22c55e'; // green
    case 'rejected':
    case 'cancelled':
      return '#ef4444'; // red
    case 'pending':
    case 'in-progress':
      return '#f59e0b'; // amber
    default:
      return '#3b82f6'; // blue
  }
};

const HRPage: React.FC = () => {
  const theme = useTheme();

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
            <PeopleIcon color="primary" fontSize="large" />
            <Typography 
              variant="h4" 
              component="h1" 
              gutterBottom
              sx={{ fontWeight: 700 }}
            >
              HR Management
            </Typography>
          </Box>
        </motion.div>

        {/* Quick Stats */}
        <motion.div
          variants={staggerContainer}
          initial="initial"
          animate="animate"
        >
          <Typography 
            variant="h6" 
            sx={{ 
              mb: 2, 
              fontWeight: 600,
              display: 'flex',
              alignItems: 'center',
              gap: 1
            }}
          >
            <NotificationsIcon fontSize="small" color="primary" />
            Quick Statistics
          </Typography>
          
          <Grid container spacing={3}>
            {quickStats.map((stat, index) => (
              <Grid item xs={12} sm={6} md={3} key={index}>
                <motion.div variants={fadeInUp}>
                  <Card 
                    sx={{ 
                      borderRadius: 2,
                      boxShadow: 2,
                      height: '100%',
                      transition: 'transform 0.3s, box-shadow 0.3s',
                      '&:hover': {
                        transform: 'translateY(-5px)',
                        boxShadow: 4
                      }
                    }}
                  >
                    <CardContent>
                      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                        <Box>
                          <Typography variant="body2" color="textSecondary" gutterBottom>
                            {stat.title}
                          </Typography>
                          <Typography variant="h5" component="div" sx={{ fontWeight: 700 }}>
                            {stat.value}
                          </Typography>
                          <Box sx={{ 
                            display: 'flex', 
                            alignItems: 'center', 
                            mt: 1,
                            color: stat.changeDirection === 'up' ? 'success.main' : 'error.main'
                          }}>
                            {stat.changeDirection === 'up' ? 
                              <ArrowUpwardIcon fontSize="small" /> : 
                              <ArrowDownwardIcon fontSize="small" />
                            }
                            <Typography variant="caption" sx={{ ml: 0.5 }}>
                              {stat.change}
                            </Typography>
                          </Box>
                        </Box>
                        <Avatar 
                          sx={{ 
                            bgcolor: stat.color,
                            width: 48,
                            height: 48
                          }}
                        >
                          <stat.icon />
                        </Avatar>
                      </Box>
                    </CardContent>
                  </Card>
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </motion.div>

        {/* HR Modules */}
        <Box sx={{ mt: 6 }}>
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.2 }}
          >
            <Typography 
              variant="h6" 
              sx={{ 
                mb: 2, 
                fontWeight: 600,
                display: 'flex',
                alignItems: 'center',
                gap: 1
              }}
            >
              <SettingsIcon fontSize="small" color="primary" />
              HR Modules
            </Typography>
            
            <Grid container spacing={3}>
              {hrModules.map((module, index) => (
                <Grid item xs={12} sm={6} md={4} key={index}>
                  <Card 
                    component={motion.div}
                    whileHover={{ 
                      scale: 1.03,
                      boxShadow: '0 10px 30px rgba(0,0,0,0.1)'
                    }}
                    sx={{ 
                      borderRadius: 2,
                      boxShadow: 2,
                      height: '100%'
                    }}
                  >
                    <CardActionArea 
                      component={Link} 
                      to={module.link}
                      sx={{ height: '100%', p: 1 }}
                    >
                      <CardContent>
                        <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                          <Avatar 
                            sx={{ 
                              bgcolor: module.color,
                              width: 48,
                              height: 48,
                              mr: 2
                            }}
                          >
                            <module.icon />
                          </Avatar>
                          <Typography variant="h6" component="div">
                            {module.title}
                          </Typography>
                        </Box>
                        <Typography variant="body2" color="textSecondary">
                          {module.description}
                        </Typography>
                      </CardContent>
                    </CardActionArea>
                  </Card>
                </Grid>
              ))}
            </Grid>
          </motion.div>
        </Box>

        {/* Recent HR Activities */}
        <Box sx={{ mt: 6 }}>
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
              <Typography 
                variant="h6" 
                sx={{ 
                  fontWeight: 600,
                  display: 'flex',
                  alignItems: 'center',
                  gap: 1
                }}
              >
                <NotificationsIcon fontSize="small" color="primary" />
                Recent HR Activities
              </Typography>
              <Button 
                startIcon={<EventNoteIcon />}
                variant="outlined"
                sx={{ borderRadius: 2 }}
              >
                View All Activities
              </Button>
            </Box>
            
            <Paper 
              sx={{ 
                borderRadius: 2,
                boxShadow: 2,
                overflow: 'hidden'
              }}
            >
              <List sx={{ p: 0 }}>
                {recentActivities.map((activity, index) => (
                  <React.Fragment key={activity.id}>
                    <ListItem 
                      alignItems="flex-start"
                      sx={{ 
                        py: 2,
                        transition: 'background-color 0.3s',
                        '&:hover': { bgcolor: 'rgba(0, 0, 0, 0.03)' }
                      }}
                    >
                      <ListItemAvatar>
                        <Avatar sx={{ bgcolor: getStatusColor(activity.status) }}>
                          <activity.icon />
                        </Avatar>
                      </ListItemAvatar>
                      <ListItemText
                        primary={
                          <Typography variant="subtitle1" component="div" sx={{ fontWeight: 600 }}>
                            {activity.title}
                          </Typography>
                        }
                        secondary={
                          <>
                            <Typography variant="body2" component="span" color="textPrimary">
                              {activity.description}
                            </Typography>
                            <Typography variant="caption" component="div" color="textSecondary" sx={{ mt: 0.5 }}>
                              {activity.time}
                            </Typography>
                          </>
                        }
                      />
                    </ListItem>
                    {index < recentActivities.length - 1 && <Divider variant="inset" component="li" />}
                  </React.Fragment>
                ))}
              </List>
            </Paper>
          </motion.div>
        </Box>
      </Container>

      {/* Chatbot */}
      <ChatBot />
    </Box>
  );
};

export default HRPage;