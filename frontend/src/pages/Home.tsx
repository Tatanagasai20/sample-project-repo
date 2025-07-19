import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { 
  Box, 
  Typography, 
  Button, 
  Container, 
  Grid, 
  Card, 
  CardContent, 
  CardHeader,
  Chip,
  useTheme
} from '@mui/material';
import { 
  Business as BusinessIcon,
  CalendarMonth as CalendarIcon, 
  AccessTime as ClockIcon, 
  People as UsersIcon, 
  Chat as MessageCircleIcon,
  Bolt as ZapIcon,
  Shield as ShieldIcon
} from '@mui/icons-material';
import Header from '../components/Header';
import ChatBot from '../components/ChatBot';

const fadeInUp = {
  initial: { opacity: 0, y: 60 },
  animate: { opacity: 1, y: 0 },
  transition: { duration: 0.6 },
};

const staggerContainer = {
  animate: {
    transition: {
      staggerChildren: 0.1,
    },
  },
};

const features = [
  {
    icon: CalendarIcon,
    title: "Leave Management",
    description: "Apply for leaves, track approvals, and manage your time off efficiently.",
    color: "#3b82f6", // blue-500
  },
  {
    icon: ClockIcon,
    title: "Attendance Tracking",
    description: "Monitor work hours, check-in/out times, and generate attendance reports.",
    color: "#22c55e", // green-500
  },
  {
    icon: UsersIcon,
    title: "HR Operations",
    description: "Comprehensive HR tools for employee management and organizational tasks.",
    color: "#a855f7", // purple-500
  },
  {
    icon: MessageCircleIcon,
    title: "AI Chatbot",
    description: "Get instant answers to HR queries and company policies 24/7.",
    color: "#f97316", // orange-500
  },
];

const stats = [
  { number: "500+", label: "Employees" },
  { number: "99.9%", label: "Uptime" },
  { number: "24/7", label: "Support" },
  { number: "50+", label: "Features" },
];

const HomePage: React.FC = () => {
  const theme = useTheme();

  return (
    <Box sx={{ minHeight: '100vh' }}>
      <Header />
      
      {/* Hero Section */}
      <Container maxWidth="lg" sx={{ py: 10, textAlign: 'center' }}>
        <motion.div initial={{ opacity: 0, y: 30 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 0.8 }}>
          <Chip 
            icon={<ZapIcon fontSize="small" />} 
            label="Next-Gen HR Platform" 
            color="primary" 
            variant="outlined" 
            sx={{ mb: 4 }} 
          />
          <Typography 
            variant="h2" 
            component="h1" 
            gutterBottom 
            className="gradient-text"
            sx={{ 
              fontWeight: 800, 
              mb: 3,
              fontSize: { xs: '2.5rem', md: '3.5rem' } 
            }}
          >
            Transform Your
            <br />
            <motion.span initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ delay: 0.5, duration: 0.8 }}>
              HR Operations
            </motion.span>
          </Typography>
          <Typography 
            variant="h6" 
            color="textSecondary" 
            sx={{ 
              mb: 6, 
              maxWidth: 800, 
              mx: 'auto',
              fontSize: { xs: '1rem', md: '1.25rem' }
            }}
          >
            Streamline leave management, attendance tracking, and HR operations with our intelligent platform.
            Experience the future of workplace management at Priacc Innovations.
          </Typography>
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.7, duration: 0.6 }}
            style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '1rem' }}
          >
            <Box sx={{ 
              display: 'flex', 
              flexDirection: { xs: 'column', sm: 'row' }, 
              gap: 2, 
              justifyContent: 'center' 
            }}>
              <Button 
                component={Link} 
                to="/dashboard" 
                variant="contained" 
                size="large" 
                sx={{ 
                  px: 4, 
                  py: 1.5, 
                  fontSize: '1.1rem',
                  borderRadius: 2,
                  background: 'linear-gradient(to right, #3b82f6, #4f46e5)',
                  '&:hover': {
                    background: 'linear-gradient(to right, #2563eb, #4338ca)',
                  }
                }}
              >
                Start Your Journey
              </Button>
              <Button 
                variant="outlined" 
                size="large" 
                sx={{ 
                  px: 4, 
                  py: 1.5, 
                  fontSize: '1.1rem',
                  borderRadius: 2,
                  borderColor: theme.palette.primary.main,
                  '&:hover': {
                    borderColor: theme.palette.primary.dark,
                    backgroundColor: 'rgba(59, 130, 246, 0.04)',
                  }
                }}
              >
                Watch Demo
              </Button>
            </Box>
          </motion.div>
        </motion.div>
      </Container>

      {/* Stats Section */}
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <motion.div
          variants={staggerContainer}
          initial="initial"
          whileInView="animate"
          viewport={{ once: true }}
        >
          <Grid container spacing={4}>
            {stats.map((stat, index) => (
              <Grid item xs={6} md={3} key={index}>
                <motion.div variants={fadeInUp}>
                  <Box sx={{ textAlign: 'center' }}>
                    <Typography 
                      variant="h3" 
                      color="primary" 
                      sx={{ 
                        mb: 1, 
                        fontWeight: 700,
                        fontSize: { xs: '2rem', md: '2.5rem' } 
                      }}
                    >
                      {stat.number}
                    </Typography>
                    <Typography variant="body1" color="textSecondary">
                      {stat.label}
                    </Typography>
                  </Box>
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </motion.div>
      </Container>

      {/* Features Section */}
      <Container maxWidth="lg" sx={{ py: 10 }}>
        <motion.div
          variants={staggerContainer}
          initial="initial"
          whileInView="animate"
          viewport={{ once: true }}
        >
          <motion.div variants={fadeInUp} sx={{ textAlign: 'center', mb: 8 }}>
            <Typography 
              variant="h3" 
              component="h2" 
              gutterBottom 
              sx={{ 
                fontWeight: 700,
                fontSize: { xs: '2rem', md: '2.5rem' } 
              }}
            >
              Powerful Features for Modern Teams
            </Typography>
            <Typography 
              variant="h6" 
              color="textSecondary" 
              sx={{ 
                maxWidth: 700, 
                mx: 'auto',
                fontSize: { xs: '1rem', md: '1.25rem' }
              }}
            >
              Everything you need to manage your workforce efficiently and effectively
            </Typography>
          </motion.div>

          <Grid container spacing={4}>
            {features.map((feature, index) => (
              <Grid item xs={12} sm={6} lg={3} key={index}>
                <motion.div
                  variants={fadeInUp}
                  whileHover={{ y: -10, scale: 1.02 }}
                  transition={{ type: "spring", stiffness: 300 }}
                >
                  <Card sx={{ 
                    height: '100%', 
                    borderRadius: 4, 
                    boxShadow: 3,
                    transition: 'all 0.3s ease',
                    '&:hover': {
                      boxShadow: 6,
                    },
                    bgcolor: 'rgba(255, 255, 255, 0.8)',
                    backdropFilter: 'blur(8px)',
                  }}>
                    <CardHeader
                      avatar={
                        <Box 
                          sx={{ 
                            width: 48, 
                            height: 48, 
                            borderRadius: 2, 
                            bgcolor: feature.color,
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            color: 'white',
                          }}
                        >
                          <feature.icon />
                        </Box>
                      }
                      title={
                        <Typography variant="h6" component="h3">
                          {feature.title}
                        </Typography>
                      }
                    />
                    <CardContent>
                      <Typography variant="body2" color="textSecondary">
                        {feature.description}
                      </Typography>
                    </CardContent>
                  </Card>
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </motion.div>
      </Container>

      {/* CTA Section */}
      <Container maxWidth="lg" sx={{ py: 10 }}>
        <motion.div
          initial={{ opacity: 0 }}
          whileInView={{ opacity: 1 }}
          viewport={{ once: true }}
          transition={{ duration: 0.8 }}
        >
          <Card sx={{ 
            borderRadius: 4, 
            background: 'linear-gradient(to right, #3b82f6, #4f46e5)',
            color: 'white',
            boxShadow: 3,
          }}>
            <CardContent sx={{ p: 6, textAlign: 'center' }}>
              <motion.div
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6 }}
              >
                <ShieldIcon sx={{ fontSize: 64, mb: 3, opacity: 0.8 }} />
                <Typography 
                  variant="h3" 
                  component="h2" 
                  gutterBottom
                  sx={{ 
                    fontWeight: 700,
                    fontSize: { xs: '1.75rem', md: '2.25rem' } 
                  }}
                >
                  Ready to Transform Your HR?
                </Typography>
                <Typography 
                  variant="h6" 
                  sx={{ 
                    mb: 4, 
                    opacity: 0.9, 
                    maxWidth: 700, 
                    mx: 'auto',
                    fontSize: { xs: '1rem', md: '1.25rem' }
                  }}
                >
                  Join thousands of companies that trust Priacc Innovations for their HR management needs.
                </Typography>
                <Button 
                  component={Link} 
                  to="/dashboard" 
                  variant="contained" 
                  color="secondary" 
                  size="large" 
                  sx={{ 
                    px: 4, 
                    py: 1.5, 
                    fontSize: '1.1rem',
                    bgcolor: 'white',
                    color: theme.palette.primary.main,
                    '&:hover': {
                      bgcolor: 'rgba(255, 255, 255, 0.9)',
                    }
                  }}
                >
                  Get Started Today
                </Button>
              </motion.div>
            </CardContent>
          </Card>
        </motion.div>
      </Container>

      {/* Footer */}
      <Box sx={{ bgcolor: 'grey.900', color: 'white', py: 6, mt: 4 }}>
        <Container maxWidth="lg">
          <Box sx={{ 
            display: 'flex', 
            flexDirection: { xs: 'column', md: 'row' }, 
            alignItems: 'center', 
            justifyContent: 'space-between',
            gap: 2,
          }}>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: { xs: 2, md: 0 } }}>
              <BusinessIcon />
              <Typography variant="h6" component="span">
                Priacc Innovations
              </Typography>
            </Box>
            <Typography variant="body2" color="grey.400">
              © 2024 Priacc Innovations. All rights reserved.
            </Typography>
          </Box>
        </Container>
      </Box>

      {/* Chatbot */}
      <ChatBot />
    </Box>
  );
};

export default HomePage;