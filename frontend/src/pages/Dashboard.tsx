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
  useTheme
} from '@mui/material';
import {
  AccessTime as ClockIcon,
  EventNote as CalendarIcon,
  People as PeopleIcon,
  Support as SupportIcon,
  Notifications as NotificationsIcon,
  CheckCircle as CheckCircleIcon,
  Pending as PendingIcon,
  Cancel as CancelIcon,
  ArrowUpward as ArrowUpwardIcon,
  ArrowDownward as ArrowDownwardIcon
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
    title: "Leave Balance",
    value: "12 days",
    icon: CalendarIcon,
    color: "#3b82f6", // blue
    change: "+2",
    changeDirection: "up"
  },
  {
    title: "Attendance Rate",
    value: "98.5%",
    icon: ClockIcon,
    color: "#22c55e", // green
    change: "+0.5%",
    changeDirection: "up"
  },
  {
    title: "Team Size",
    value: "24",
    icon: PeopleIcon,
    color: "#a855f7", // purple
    change: "+3",
    changeDirection: "up"
  },
  {
    title: "Support Tickets",
    value: "2",
    icon: SupportIcon,
    color: "#f97316", // orange
    change: "-1",
    changeDirection: "down"
  }
];

const quickActions = [
  {
    title: "Apply for Leave",
    description: "Request time off for vacation, sick leave, or personal reasons",
    icon: CalendarIcon,
    color: "#3b82f6", // blue
    link: "/leave"
  },
  {
    title: "Mark Attendance",
    description: "Check-in or check-out for your daily work hours",
    icon: ClockIcon,
    color: "#22c55e", // green
    link: "/attendance"
  },
  {
    title: "HR Operations",
    description: "Access HR modules for employee management and more",
    icon: PeopleIcon,
    color: "#a855f7", // purple
    link: "/hr"
  },
  {
    title: "Contact Support",
    description: "Get help with any issues or questions you may have",
    icon: SupportIcon,
    color: "#f97316", // orange
    link: "/"
  }
];

const recentActivities = [
  {
    id: 1,
    type: "leave",
    title: "Leave Request Approved",
    description: "Your leave request for June 15-18 has been approved",
    time: "2 hours ago",
    status: "approved",
    icon: CheckCircleIcon
  },
  {
    id: 2,
    type: "attendance",
    title: "Checked In",
    description: "You checked in at 9:05 AM today",
    time: "5 hours ago",
    status: "info",
    icon: ClockIcon
  },
  {
    id: 3,
    type: "hr",
    title: "Performance Review",
    description: "Your quarterly performance review is scheduled for next week",
    time: "1 day ago",
    status: "pending",
    icon: PendingIcon
  },
  {
    id: 4,
    type: "leave",
    title: "Leave Request Rejected",
    description: "Your leave request for May 30 has been rejected",
    time: "3 days ago",
    status: "rejected",
    icon: CancelIcon
  },
  {
    id: 5,
    type: "notification",
    title: "Holiday Announcement",
    description: "Office will be closed on May 25 for Memorial Day",
    time: "1 week ago",
    status: "info",
    icon: NotificationsIcon
  }
];

const getStatusColor = (status: string) => {
  switch (status) {
    case 'approved':
      return '#22c55e'; // green
    case 'rejected':
      return '#ef4444'; // red
    case 'pending':
      return '#f59e0b'; // amber
    default:
      return '#3b82f6'; // blue
  }
};

const DashboardPage: React.FC = () => {
  const theme = useTheme();
  const userName = "John Doe";

  return (
    <Box sx={{ minHeight: '100vh', bgcolor: 'grey.50' }}>
      <Header />
      
      <Container maxWidth="lg" sx={{ py: 4 }}>
        {/* Welcome Section */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
        >
          <Box sx={{ mb: 4 }}>
            <Typography 
              variant="h4" 
              component="h1" 
              gutterBottom
              sx={{ fontWeight: 700 }}
            >
              Welcome back, {userName}!
            </Typography>
            <Typography variant="body1" color="textSecondary">
              Here's an overview of your HR dashboard for today, {new Date().toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}
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

        {/* Quick Actions and Recent Activities */}
        <Box sx={{ mt: 6 }}>
          <Grid container spacing={4}>
            {/* Quick Actions */}
            <Grid item xs={12} md={6}>
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
                  <ClockIcon fontSize="small" color="primary" />
                  Quick Actions
                </Typography>
                
                <Grid container spacing={2}>
                  {quickActions.map((action, index) => (
                    <Grid item xs={12} sm={6} key={index}>
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
                          to={action.link}
                          sx={{ height: '100%', p: 1 }}
                        >
                          <CardContent>
                            <Box sx={{ display: 'flex', alignItems: 'center', mb: 1.5 }}>
                              <Avatar 
                                sx={{ 
                                  bgcolor: action.color,
                                  width: 40,
                                  height: 40,
                                  mr: 2
                                }}
                              >
                                <action.icon fontSize="small" />
                              </Avatar>
                              <Typography variant="h6" component="div">
                                {action.title}
                              </Typography>
                            </Box>
                            <Typography variant="body2" color="textSecondary">
                              {action.description}
                            </Typography>
                          </CardContent>
                        </CardActionArea>
                      </Card>
                    </Grid>
                  ))}
                </Grid>
              </motion.div>
            </Grid>
            
            {/* Recent Activities */}
            <Grid item xs={12} md={6}>
              <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5, delay: 0.3 }}
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
                  Recent Activities
                </Typography>
                
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
            </Grid>
          </Grid>
        </Box>
      </Container>

      {/* Chatbot */}
      <ChatBot />
    </Box>
  );
};

export default DashboardPage;