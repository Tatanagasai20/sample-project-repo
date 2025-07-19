import { useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  Container,
  Divider,
  Grid,
  TextField,
  Typography,
  Avatar,
  Paper,
  Tabs,
  Tab,
  IconButton,
  Alert,
  Snackbar,
} from '@mui/material';
import { Edit as EditIcon, Save as SaveIcon, Cancel as CancelIcon } from '@mui/icons-material';
import { useAuth } from '../contexts/AuthContext';

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`profile-tabpanel-${index}`}
      aria-labelledby={`profile-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ py: 3 }}>{children}</Box>}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `profile-tab-${index}`,
    'aria-controls': `profile-tabpanel-${index}`,
  };
}

const Profile = () => {
  const { user } = useAuth();
  const [tabValue, setTabValue] = useState(0);
  const [editMode, setEditMode] = useState(false);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' as 'success' | 'error' });

  // Mock user data - in a real app, this would come from the API
  const [userData, setUserData] = useState({
    name: user?.name || 'John Doe',
    email: user?.email || 'john.doe@example.com',
    phone: '+1 (555) 123-4567',
    department: 'Engineering',
    position: 'Senior Developer',
    joinDate: '2020-01-15',
    address: '123 Main St, Anytown, USA',
    bio: 'Experienced software developer with a passion for creating efficient and user-friendly applications.',
  });

  const handleTabChange = (_event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  const handleEditToggle = () => {
    setEditMode(!editMode);
  };

  const handleSave = () => {
    // In a real app, this would call an API to update the user profile
    setEditMode(false);
    setSnackbar({
      open: true,
      message: 'Profile updated successfully',
      severity: 'success',
    });
  };

  const handleCancel = () => {
    // Reset to original data
    setEditMode(false);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUserData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return (
    <Container maxWidth="lg">
      <Paper elevation={0} sx={{ p: 3, mb: 4, borderRadius: 2 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 3 }}>
          <Typography variant="h4" component="h1" gutterBottom>
            My Profile
          </Typography>
          <Box sx={{ flexGrow: 1 }} />
          {!editMode ? (
            <Button
              variant="outlined"
              startIcon={<EditIcon />}
              onClick={handleEditToggle}
            >
              Edit Profile
            </Button>
          ) : (
            <Box sx={{ display: 'flex', gap: 1 }}>
              <Button
                variant="outlined"
                color="error"
                startIcon={<CancelIcon />}
                onClick={handleCancel}
              >
                Cancel
              </Button>
              <Button
                variant="contained"
                color="primary"
                startIcon={<SaveIcon />}
                onClick={handleSave}
              >
                Save Changes
              </Button>
            </Box>
          )}
        </Box>

        <Grid container spacing={4}>
          <Grid item xs={12} md={4}>
            <Card sx={{ height: '100%', borderRadius: 2 }}>
              <CardContent sx={{ textAlign: 'center', py: 4 }}>
                <Avatar
                  sx={{
                    width: 120,
                    height: 120,
                    mx: 'auto',
                    mb: 2,
                    bgcolor: 'primary.main',
                    fontSize: '3rem',
                  }}
                >
                  {userData.name.charAt(0)}
                </Avatar>
                <Typography variant="h5" gutterBottom>
                  {userData.name}
                </Typography>
                <Typography variant="body1" color="text.secondary" gutterBottom>
                  {userData.position}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {userData.department}
                </Typography>
                <Divider sx={{ my: 2 }} />
                <Box sx={{ textAlign: 'left' }}>
                  <Typography variant="subtitle2" gutterBottom>
                    Email
                  </Typography>
                  <Typography variant="body2" color="text.secondary" gutterBottom>
                    {userData.email}
                  </Typography>
                  <Typography variant="subtitle2" gutterBottom sx={{ mt: 2 }}>
                    Phone
                  </Typography>
                  <Typography variant="body2" color="text.secondary" gutterBottom>
                    {userData.phone}
                  </Typography>
                  <Typography variant="subtitle2" gutterBottom sx={{ mt: 2 }}>
                    Joined
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {new Date(userData.joinDate).toLocaleDateString('en-US', {
                      year: 'numeric',
                      month: 'long',
                      day: 'numeric',
                    })}
                  </Typography>
                </Box>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} md={8}>
            <Card sx={{ borderRadius: 2 }}>
              <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs
                  value={tabValue}
                  onChange={handleTabChange}
                  aria-label="profile tabs"
                  sx={{ px: 2 }}
                >
                  <Tab label="Personal Information" {...a11yProps(0)} />
                  <Tab label="Security" {...a11yProps(1)} />
                  <Tab label="Preferences" {...a11yProps(2)} />
                </Tabs>
              </Box>

              <TabPanel value={tabValue} index={0}>
                <Grid container spacing={3}>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      fullWidth
                      label="Full Name"
                      name="name"
                      value={userData.name}
                      onChange={handleChange}
                      disabled={!editMode}
                      variant="outlined"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      fullWidth
                      label="Email Address"
                      name="email"
                      value={userData.email}
                      onChange={handleChange}
                      disabled={!editMode}
                      variant="outlined"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      fullWidth
                      label="Phone Number"
                      name="phone"
                      value={userData.phone}
                      onChange={handleChange}
                      disabled={!editMode}
                      variant="outlined"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      fullWidth
                      label="Address"
                      name="address"
                      value={userData.address}
                      onChange={handleChange}
                      disabled={!editMode}
                      variant="outlined"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      fullWidth
                      label="Bio"
                      name="bio"
                      value={userData.bio}
                      onChange={handleChange}
                      disabled={!editMode}
                      variant="outlined"
                      multiline
                      rows={4}
                    />
                  </Grid>
                </Grid>
              </TabPanel>

              <TabPanel value={tabValue} index={1}>
                <Alert severity="info" sx={{ mb: 3 }}>
                  Password changes and security settings are managed through the HR department for security reasons.
                </Alert>
                <Typography variant="subtitle1" gutterBottom>
                  Last Password Change
                </Typography>
                <Typography variant="body2" color="text.secondary" paragraph>
                  2023-05-15
                </Typography>
                <Typography variant="subtitle1" gutterBottom>
                  Two-Factor Authentication
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Not enabled
                </Typography>
              </TabPanel>

              <TabPanel value={tabValue} index={2}>
                <Typography variant="subtitle1" gutterBottom>
                  Notification Preferences
                </Typography>
                <Typography variant="body2" color="text.secondary" paragraph>
                  Manage your notification settings through the Settings page.
                </Typography>
                <Button variant="outlined" color="primary">
                  Go to Settings
                </Button>
              </TabPanel>
            </Card>
          </Grid>
        </Grid>
      </Paper>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbar.severity} sx={{ width: '100%' }}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default Profile;