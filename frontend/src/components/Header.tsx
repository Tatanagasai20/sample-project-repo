import { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button, Box, Chip, IconButton, Drawer, List, ListItem, ListItemText, useMediaQuery } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import { Menu as MenuIcon, Business as BusinessIcon } from '@mui/icons-material';
import { motion } from 'framer-motion';

interface HeaderProps {
  userName?: string;
}

const Header: React.FC<HeaderProps> = ({ userName = 'John Doe' }) => {
  const theme = useTheme();
  const location = useLocation();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));
  const [mobileOpen, setMobileOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 10) {
        setScrolled(true);
      } else {
        setScrolled(false);
      }
    };

    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const navItems = [
    { name: 'Dashboard', path: '/dashboard' },
    { name: 'Leave', path: '/leave' },
    { name: 'Attendance', path: '/attendance' },
    { name: 'HR', path: '/hr' },
  ];

  const drawer = (
    <Box onClick={handleDrawerToggle} sx={{ textAlign: 'center' }}>
      <Typography variant="h6" sx={{ my: 2 }}>
        <Box display="flex" alignItems="center" justifyContent="center" gap={1}>
          <BusinessIcon color="primary" />
          <span className="gradient-text">Priacc Innovations</span>
        </Box>
      </Typography>
      <List>
        {navItems.map((item) => (
          <ListItem key={item.name} component={Link} to={item.path} selected={location.pathname === item.path}>
            <ListItemText 
              primary={item.name} 
              sx={{
                color: location.pathname === item.path ? theme.palette.primary.main : theme.palette.text.primary,
                fontWeight: location.pathname === item.path ? 600 : 400,
              }}
            />
          </ListItem>
        ))}
      </List>
    </Box>
  );

  return (
    <motion.div
      initial={{ opacity: 0, y: -20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <AppBar 
        position="sticky" 
        color="transparent" 
        elevation={0}
        sx={{
          borderBottom: '1px solid',
          borderColor: 'divider',
          backdropFilter: 'blur(8px)',
          backgroundColor: scrolled ? 'rgba(255, 255, 255, 0.9)' : 'rgba(255, 255, 255, 0.8)',
          transition: 'background-color 0.3s ease',
        }}
      >
        <Toolbar className="container">
          <motion.div
            whileHover={{ scale: 1.05 }}
            transition={{ type: 'spring', stiffness: 300 }}
          >
            <Typography 
              variant="h6" 
              component={Link} 
              to="/"
              sx={{ 
                display: 'flex', 
                alignItems: 'center', 
                gap: 1, 
                textDecoration: 'none', 
                color: 'inherit',
                flexGrow: { xs: 1, md: 0 }
              }}
            >
              <BusinessIcon color="primary" fontSize="large" />
              <span className="gradient-text">Priacc Innovations</span>
            </Typography>
          </motion.div>

          {isMobile ? (
            <IconButton
              color="inherit"
              aria-label="open drawer"
              edge="end"
              onClick={handleDrawerToggle}
            >
              <MenuIcon />
            </IconButton>
          ) : (
            <>
              <Box sx={{ display: 'flex', flexGrow: 1, justifyContent: 'center', ml: 4 }}>
                {navItems.map((item) => (
                  <Button 
                    key={item.name} 
                    component={Link} 
                    to={item.path}
                    sx={{ 
                      mx: 1.5,
                      color: location.pathname === item.path ? theme.palette.primary.main : theme.palette.text.secondary,
                      fontWeight: location.pathname === item.path ? 600 : 400,
                      '&:hover': {
                        color: theme.palette.primary.main,
                        backgroundColor: 'transparent',
                      }
                    }}
                  >
                    {item.name}
                  </Button>
                ))}
              </Box>
              <Chip 
                label={userName} 
                variant="outlined" 
                color="primary"
                sx={{ 
                  borderRadius: '16px',
                  fontWeight: 500,
                  backgroundColor: theme.palette.grey[100],
                }}
              />
            </>
          )}
        </Toolbar>
      </AppBar>

      <Drawer
        anchor="right"
        open={mobileOpen}
        onClose={handleDrawerToggle}
        ModalProps={{ keepMounted: true }}
        sx={{
          display: { xs: 'block', md: 'none' },
          '& .MuiDrawer-paper': { boxSizing: 'border-box', width: 240 },
        }}
      >
        {drawer}
      </Drawer>
    </motion.div>
  );
};

export default Header;