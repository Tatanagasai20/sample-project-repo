import { useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  Container,
  Divider,
  FormControlLabel,
  Grid,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Paper,
  Switch,
  Typography,
  Alert,
  Snackbar,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  SelectChangeEvent,
} from '@mui/material';
import {
  Notifications as NotificationsIcon,
  Palette as PaletteIcon,
  Language as LanguageIcon,
  Visibility as VisibilityIcon,
  Security as SecurityIcon,
  Save as SaveIcon,
} from '@mui/icons-material';

const Settings = () => {
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' as 'success' | 'error' });
  
  // Notification settings
  const [notificationSettings, setNotificationSettings] = useState({
    emailNotifications: true,
    pushNotifications: true,
    leaveRequestUpdates: true,
    attendanceReminders: true,
    payrollNotifications: true,
    systemAnnouncements: true,
  });

  // Appearance settings
  const [appearanceSettings, setAppearanceSettings] = useState({
    darkMode: false,
    highContrast: false,
    fontSize: 'medium',
  });

  // Language settings
  const [languageSettings, setLanguageSettings] = useState({
    language: 'english',
    dateFormat: 'MM/DD/YYYY',
    timeFormat: '12h',
  });

  // Privacy settings
  const [privacySettings, setPrivacySettings] = useState({
    showProfileToColleagues: true,
    shareActivityStatus: true,
    allowDataCollection: true,
  });

  // Security settings
  const [securitySettings, setSecuritySettings] = useState({
    twoFactorAuth: false,
    sessionTimeout: '30min',
    loginNotifications: true,
  });

  const handleNotificationChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNotificationSettings({
      ...notificationSettings,
      [event.target.name]: event.target.checked,
    });
  };

  const handleAppearanceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setAppearanceSettings({
      ...appearanceSettings,
      [event.target.name]: event.target.checked,
    });
  };

  const handleFontSizeChange = (event: SelectChangeEvent) => {
    setAppearanceSettings({
      ...appearanceSettings,
      fontSize: event.target.value,
    });
  };

  const handleLanguageChange = (event: SelectChangeEvent) => {
    setLanguageSettings({
      ...languageSettings,
      language: event.target.value,
    });
  };

  const handleDateFormatChange = (event: SelectChangeEvent) => {
    setLanguageSettings({
      ...languageSettings,
      dateFormat: event.target.value,
    });
  };

  const handleTimeFormatChange = (event: SelectChangeEvent) => {
    setLanguageSettings({
      ...languageSettings,
      timeFormat: event.target.value,
    });
  };

  const handlePrivacyChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPrivacySettings({
      ...privacySettings,
      [event.target.name]: event.target.checked,
    });
  };

  const handleSecurityChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSecuritySettings({
      ...securitySettings,
      [event.target.name]: event.target.checked,
    });
  };

  const handleSessionTimeoutChange = (event: SelectChangeEvent) => {
    setSecuritySettings({
      ...securitySettings,
      sessionTimeout: event.target.value,
    });
  };

  const handleSaveSettings = () => {
    // In a real app, this would call an API to save the settings
    setSnackbar({
      open: true,
      message: 'Settings saved successfully',
      severity: 'success',
    });
  };

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  return (
    <Container maxWidth="lg">
      <Paper elevation={0} sx={{ p: 3, mb: 4, borderRadius: 2 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 3 }}>
          <Typography variant="h4" component="h1" gutterBottom>
            Settings
          </Typography>
          <Box sx={{ flexGrow: 1 }} />
          <Button
            variant="contained"
            color="primary"
            startIcon={<SaveIcon />}
            onClick={handleSaveSettings}
          >
            Save All Settings
          </Button>
        </Box>

        <Grid container spacing={4}>
          <Grid item xs={12} md={3}>
            <Card sx={{ borderRadius: 2, position: 'sticky', top: 20 }}>
              <List component="nav" aria-label="settings navigation">
                <ListItem button component="a" href="#notifications">
                  <ListItemIcon>
                    <NotificationsIcon />
                  </ListItemIcon>
                  <ListItemText primary="Notifications" />
                </ListItem>
                <ListItem button component="a" href="#appearance">
                  <ListItemIcon>
                    <PaletteIcon />
                  </ListItemIcon>
                  <ListItemText primary="Appearance" />
                </ListItem>
                <ListItem button component="a" href="#language">
                  <ListItemIcon>
                    <LanguageIcon />
                  </ListItemIcon>
                  <ListItemText primary="Language & Region" />
                </ListItem>
                <ListItem button component="a" href="#privacy">
                  <ListItemIcon>
                    <VisibilityIcon />
                  </ListItemIcon>
                  <ListItemText primary="Privacy" />
                </ListItem>
                <ListItem button component="a" href="#security">
                  <ListItemIcon>
                    <SecurityIcon />
                  </ListItemIcon>
                  <ListItemText primary="Security" />
                </ListItem>
              </List>
            </Card>
          </Grid>

          <Grid item xs={12} md={9}>
            <Card sx={{ borderRadius: 2, mb: 4 }} id="notifications">
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Notification Settings
                </Typography>
                <Divider sx={{ mb: 3 }} />
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={notificationSettings.emailNotifications}
                          onChange={handleNotificationChange}
                          name="emailNotifications"
                          color="primary"
                        />
                      }
                      label="Email Notifications"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={notificationSettings.pushNotifications}
                          onChange={handleNotificationChange}
                          name="pushNotifications"
                          color="primary"
                        />
                      }
                      label="Push Notifications"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Typography variant="subtitle2" gutterBottom sx={{ mt: 2 }}>
                      Notification Types
                    </Typography>
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={notificationSettings.leaveRequestUpdates}
                          onChange={handleNotificationChange}
                          name="leaveRequestUpdates"
                          color="primary"
                        />
                      }
                      label="Leave Request Updates"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={notificationSettings.attendanceReminders}
                          onChange={handleNotificationChange}
                          name="attendanceReminders"
                          color="primary"
                        />
                      }
                      label="Attendance Reminders"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={notificationSettings.payrollNotifications}
                          onChange={handleNotificationChange}
                          name="payrollNotifications"
                          color="primary"
                        />
                      }
                      label="Payroll Notifications"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={notificationSettings.systemAnnouncements}
                          onChange={handleNotificationChange}
                          name="systemAnnouncements"
                          color="primary"
                        />
                      }
                      label="System Announcements"
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>

            <Card sx={{ borderRadius: 2, mb: 4 }} id="appearance">
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Appearance Settings
                </Typography>
                <Divider sx={{ mb: 3 }} />
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={appearanceSettings.darkMode}
                          onChange={handleAppearanceChange}
                          name="darkMode"
                          color="primary"
                        />
                      }
                      label="Dark Mode"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={appearanceSettings.highContrast}
                          onChange={handleAppearanceChange}
                          name="highContrast"
                          color="primary"
                        />
                      }
                      label="High Contrast"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControl fullWidth variant="outlined" sx={{ mt: 2 }}>
                      <InputLabel id="font-size-label">Font Size</InputLabel>
                      <Select
                        labelId="font-size-label"
                        id="font-size"
                        value={appearanceSettings.fontSize}
                        onChange={handleFontSizeChange}
                        label="Font Size"
                      >
                        <MenuItem value="small">Small</MenuItem>
                        <MenuItem value="medium">Medium</MenuItem>
                        <MenuItem value="large">Large</MenuItem>
                      </Select>
                    </FormControl>
                  </Grid>
                </Grid>
              </CardContent>
            </Card>

            <Card sx={{ borderRadius: 2, mb: 4 }} id="language">
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Language & Region Settings
                </Typography>
                <Divider sx={{ mb: 3 }} />
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={6}>
                    <FormControl fullWidth variant="outlined">
                      <InputLabel id="language-label">Language</InputLabel>
                      <Select
                        labelId="language-label"
                        id="language"
                        value={languageSettings.language}
                        onChange={handleLanguageChange}
                        label="Language"
                      >
                        <MenuItem value="english">English</MenuItem>
                        <MenuItem value="spanish">Spanish</MenuItem>
                        <MenuItem value="french">French</MenuItem>
                        <MenuItem value="german">German</MenuItem>
                        <MenuItem value="chinese">Chinese</MenuItem>
                      </Select>
                    </FormControl>
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControl fullWidth variant="outlined">
                      <InputLabel id="date-format-label">Date Format</InputLabel>
                      <Select
                        labelId="date-format-label"
                        id="date-format"
                        value={languageSettings.dateFormat}
                        onChange={handleDateFormatChange}
                        label="Date Format"
                      >
                        <MenuItem value="MM/DD/YYYY">MM/DD/YYYY</MenuItem>
                        <MenuItem value="DD/MM/YYYY">DD/MM/YYYY</MenuItem>
                        <MenuItem value="YYYY-MM-DD">YYYY-MM-DD</MenuItem>
                      </Select>
                    </FormControl>
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControl fullWidth variant="outlined">
                      <InputLabel id="time-format-label">Time Format</InputLabel>
                      <Select
                        labelId="time-format-label"
                        id="time-format"
                        value={languageSettings.timeFormat}
                        onChange={handleTimeFormatChange}
                        label="Time Format"
                      >
                        <MenuItem value="12h">12-hour (AM/PM)</MenuItem>
                        <MenuItem value="24h">24-hour</MenuItem>
                      </Select>
                    </FormControl>
                  </Grid>
                </Grid>
              </CardContent>
            </Card>

            <Card sx={{ borderRadius: 2, mb: 4 }} id="privacy">
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Privacy Settings
                </Typography>
                <Divider sx={{ mb: 3 }} />
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={privacySettings.showProfileToColleagues}
                          onChange={handlePrivacyChange}
                          name="showProfileToColleagues"
                          color="primary"
                        />
                      }
                      label="Show my profile to colleagues"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={privacySettings.shareActivityStatus}
                          onChange={handlePrivacyChange}
                          name="shareActivityStatus"
                          color="primary"
                        />
                      }
                      label="Share my activity status"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={privacySettings.allowDataCollection}
                          onChange={handlePrivacyChange}
                          name="allowDataCollection"
                          color="primary"
                        />
                      }
                      label="Allow anonymous data collection for system improvement"
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>

            <Card sx={{ borderRadius: 2 }} id="security">
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Security Settings
                </Typography>
                <Divider sx={{ mb: 3 }} />
                <Alert severity="info" sx={{ mb: 3 }}>
                  Some security settings can only be changed by contacting the HR department.
                </Alert>
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={securitySettings.twoFactorAuth}
                          onChange={handleSecurityChange}
                          name="twoFactorAuth"
                          color="primary"
                        />
                      }
                      label="Enable Two-Factor Authentication"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <FormControl fullWidth variant="outlined" sx={{ mt: 2 }}>
                      <InputLabel id="session-timeout-label">Session Timeout</InputLabel>
                      <Select
                        labelId="session-timeout-label"
                        id="session-timeout"
                        value={securitySettings.sessionTimeout}
                        onChange={handleSessionTimeoutChange}
                        label="Session Timeout"
                      >
                        <MenuItem value="15min">15 minutes</MenuItem>
                        <MenuItem value="30min">30 minutes</MenuItem>
                        <MenuItem value="1hour">1 hour</MenuItem>
                        <MenuItem value="4hours">4 hours</MenuItem>
                      </Select>
                    </FormControl>
                  </Grid>
                  <Grid item xs={12}>
                    <FormControlLabel
                      control={
                        <Switch
                          checked={securitySettings.loginNotifications}
                          onChange={handleSecurityChange}
                          name="loginNotifications"
                          color="primary"
                        />
                      }
                      label="Receive notifications for new login attempts"
                    />
                  </Grid>
                  <Grid item xs={12} sx={{ mt: 2 }}>
                    <Button variant="outlined" color="primary">
                      Change Password
                    </Button>
                  </Grid>
                </Grid>
              </CardContent>
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

export default Settings;