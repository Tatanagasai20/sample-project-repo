# Backend and Database Design for Priacc HR System

## Overview

This document outlines the backend architecture and database schema for the Priacc HR System to complement the existing React frontend application.

## Backend Architecture

### Technology Stack

- **Node.js**: JavaScript runtime for the backend
- **Express.js**: Web framework for building the API
- **MongoDB**: NoSQL database for storing HR data
- **Mongoose**: ODM (Object Data Modeling) library for MongoDB
- **JWT**: JSON Web Tokens for authentication
- **bcrypt**: For password hashing
- **Multer**: For handling file uploads (employee documents, profile pictures)

### API Structure

```
/api
├── /auth              # Authentication endpoints
│   ├── /login         # User login
│   ├── /register      # User registration (admin only)
│   ├── /refresh       # Refresh token
│   └── /logout        # User logout
├── /users             # User management
│   ├── /profile       # Get/update user profile
│   └── /:id           # CRUD operations for users
├── /employees         # Employee management
│   ├── /              # List all employees
│   ├── /:id           # CRUD operations for employees
│   └── /search        # Search employees
├── /leave             # Leave management
│   ├── /              # List all leave requests
│   ├── /apply         # Apply for leave
│   ├── /:id           # Get/update leave request
│   └── /approve/:id   # Approve/reject leave request
├── /attendance        # Attendance tracking
│   ├── /              # List attendance records
│   ├── /check-in      # Record check-in
│   ├── /check-out     # Record check-out
│   └── /report        # Generate attendance reports
└── /hr                # HR operations
    ├── /departments   # Department management
    ├── /positions     # Position management
    ├── /recruitment   # Recruitment process
    ├── /performance   # Performance reviews
    └── /payroll       # Payroll management
```

## Database Schema

### User Collection

```javascript
const UserSchema = new Schema({
  username: { type: String, required: true, unique: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  role: { type: String, enum: ['admin', 'hr', 'manager', 'employee'], default: 'employee' },
  isActive: { type: Boolean, default: true },
  lastLogin: { type: Date },
  employeeId: { type: Schema.Types.ObjectId, ref: 'Employee' },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Employee Collection

```javascript
const EmployeeSchema = new Schema({
  firstName: { type: String, required: true },
  lastName: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  phone: { type: String },
  address: {
    street: { type: String },
    city: { type: String },
    state: { type: String },
    zipCode: { type: String },
    country: { type: String }
  },
  dateOfBirth: { type: Date },
  gender: { type: String, enum: ['male', 'female', 'other'] },
  department: { type: Schema.Types.ObjectId, ref: 'Department' },
  position: { type: Schema.Types.ObjectId, ref: 'Position' },
  manager: { type: Schema.Types.ObjectId, ref: 'Employee' },
  employmentType: { type: String, enum: ['full-time', 'part-time', 'contract', 'intern'] },
  joiningDate: { type: Date, required: true },
  terminationDate: { type: Date },
  isActive: { type: Boolean, default: true },
  emergencyContact: {
    name: { type: String },
    relationship: { type: String },
    phone: { type: String }
  },
  documents: [{
    name: { type: String },
    type: { type: String },
    url: { type: String },
    uploadedAt: { type: Date, default: Date.now }
  }],
  profilePicture: { type: String },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Department Collection

```javascript
const DepartmentSchema = new Schema({
  name: { type: String, required: true },
  description: { type: String },
  manager: { type: Schema.Types.ObjectId, ref: 'Employee' },
  parentDepartment: { type: Schema.Types.ObjectId, ref: 'Department' },
  isActive: { type: Boolean, default: true },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Position Collection

```javascript
const PositionSchema = new Schema({
  title: { type: String, required: true },
  description: { type: String },
  department: { type: Schema.Types.ObjectId, ref: 'Department' },
  responsibilities: [{ type: String }],
  requirements: [{ type: String }],
  salaryRange: {
    min: { type: Number },
    max: { type: Number }
  },
  isActive: { type: Boolean, default: true },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Leave Collection

```javascript
const LeaveSchema = new Schema({
  employee: { type: Schema.Types.ObjectId, ref: 'Employee', required: true },
  leaveType: { 
    type: String, 
    enum: ['annual', 'sick', 'family', 'training', 'unpaid', 'other'], 
    required: true 
  },
  startDate: { type: Date, required: true },
  endDate: { type: Date, required: true },
  days: { type: Number, required: true },
  reason: { type: String, required: true },
  status: { 
    type: String, 
    enum: ['pending', 'approved', 'rejected', 'cancelled'], 
    default: 'pending' 
  },
  approvedBy: { type: Schema.Types.ObjectId, ref: 'Employee' },
  approvalDate: { type: Date },
  comments: { type: String },
  attachments: [{
    name: { type: String },
    url: { type: String },
    uploadedAt: { type: Date, default: Date.now }
  }],
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Leave Balance Collection

```javascript
const LeaveBalanceSchema = new Schema({
  employee: { type: Schema.Types.ObjectId, ref: 'Employee', required: true },
  year: { type: Number, required: true },
  balances: [{
    leaveType: { 
      type: String, 
      enum: ['annual', 'sick', 'family', 'training', 'unpaid', 'other']
    },
    total: { type: Number, default: 0 },
    used: { type: Number, default: 0 },
    remaining: { type: Number, default: 0 }
  }],
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Attendance Collection

```javascript
const AttendanceSchema = new Schema({
  employee: { type: Schema.Types.ObjectId, ref: 'Employee', required: true },
  date: { type: Date, required: true },
  checkIn: { type: Date },
  checkOut: { type: Date },
  workHours: { type: Number },
  status: { 
    type: String, 
    enum: ['present', 'absent', 'late', 'half-day', 'weekend', 'holiday'], 
    default: 'present' 
  },
  location: {
    latitude: { type: Number },
    longitude: { type: Number },
    address: { type: String }
  },
  notes: { type: String },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Recruitment Collection

```javascript
const RecruitmentSchema = new Schema({
  position: { type: Schema.Types.ObjectId, ref: 'Position', required: true },
  department: { type: Schema.Types.ObjectId, ref: 'Department', required: true },
  status: { 
    type: String, 
    enum: ['open', 'in-progress', 'on-hold', 'closed'], 
    default: 'open' 
  },
  openDate: { type: Date, required: true },
  closeDate: { type: Date },
  hiringManager: { type: Schema.Types.ObjectId, ref: 'Employee' },
  description: { type: String },
  requirements: [{ type: String }],
  responsibilities: [{ type: String }],
  applicants: [{
    name: { type: String },
    email: { type: String },
    phone: { type: String },
    resumeUrl: { type: String },
    status: { 
      type: String, 
      enum: ['applied', 'screening', 'interview', 'offer', 'hired', 'rejected'], 
      default: 'applied' 
    },
    appliedDate: { type: Date, default: Date.now },
    notes: { type: String }
  }],
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Performance Review Collection

```javascript
const PerformanceReviewSchema = new Schema({
  employee: { type: Schema.Types.ObjectId, ref: 'Employee', required: true },
  reviewer: { type: Schema.Types.ObjectId, ref: 'Employee', required: true },
  reviewPeriod: {
    startDate: { type: Date, required: true },
    endDate: { type: Date, required: true }
  },
  reviewDate: { type: Date, required: true },
  status: { 
    type: String, 
    enum: ['pending', 'in-progress', 'completed'], 
    default: 'pending' 
  },
  ratings: [{
    category: { type: String, required: true },
    score: { type: Number, min: 1, max: 5, required: true },
    comments: { type: String }
  }],
  overallRating: { type: Number, min: 1, max: 5 },
  strengths: [{ type: String }],
  areasForImprovement: [{ type: String }],
  goals: [{
    description: { type: String },
    targetDate: { type: Date },
    status: { 
      type: String, 
      enum: ['not-started', 'in-progress', 'completed'], 
      default: 'not-started' 
    }
  }],
  employeeComments: { type: String },
  managerComments: { type: String },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

### Payroll Collection

```javascript
const PayrollSchema = new Schema({
  employee: { type: Schema.Types.ObjectId, ref: 'Employee', required: true },
  payPeriod: {
    startDate: { type: Date, required: true },
    endDate: { type: Date, required: true }
  },
  processDate: { type: Date, required: true },
  status: { 
    type: String, 
    enum: ['pending', 'processed', 'paid'], 
    default: 'pending' 
  },
  basicSalary: { type: Number, required: true },
  earnings: [{
    type: { type: String, required: true },
    amount: { type: Number, required: true },
    description: { type: String }
  }],
  deductions: [{
    type: { type: String, required: true },
    amount: { type: Number, required: true },
    description: { type: String }
  }],
  taxes: [{
    type: { type: String, required: true },
    amount: { type: Number, required: true },
    description: { type: String }
  }],
  netPay: { type: Number, required: true },
  paymentMethod: { 
    type: String, 
    enum: ['bank-transfer', 'check', 'cash'], 
    default: 'bank-transfer' 
  },
  bankDetails: {
    accountName: { type: String },
    accountNumber: { type: String },
    bankName: { type: String },
    branchCode: { type: String }
  },
  notes: { type: String },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now }
});
```

## Implementation Steps

1. **Set up the Node.js project**
   - Initialize a new Node.js project
   - Install required dependencies
   - Configure environment variables

2. **Create the Express server**
   - Set up middleware (CORS, body-parser, etc.)
   - Configure routes
   - Implement error handling

3. **Set up MongoDB connection**
   - Configure Mongoose
   - Implement database connection

4. **Implement authentication**
   - Create user registration and login endpoints
   - Implement JWT authentication
   - Set up middleware for protected routes

5. **Implement API endpoints**
   - Create controllers for each module
   - Implement CRUD operations
   - Add validation

6. **Connect with frontend**
   - Update frontend API service to connect with the backend
   - Implement authentication in the frontend
   - Test the integration

7. **Deploy the application**
   - Set up production environment
   - Deploy the backend and frontend
   - Configure database for production

## Security Considerations

- Implement proper authentication and authorization
- Use HTTPS for all communications
- Hash passwords using bcrypt
- Implement rate limiting to prevent brute force attacks
- Validate and sanitize all user inputs
- Use environment variables for sensitive information
- Implement CORS to restrict access to the API
- Regular security audits and updates

## Scalability Considerations

- Use connection pooling for database connections
- Implement caching for frequently accessed data
- Consider using a load balancer for high traffic
- Implement database indexing for faster queries
- Use pagination for large data sets
- Consider microservices architecture for scaling individual components
- Implement logging and monitoring for performance optimization