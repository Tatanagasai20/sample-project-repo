# HR System - Full Stack Application

A comprehensive Human Resources management system built with Java Spring Boot backend and modern frontend.

## 🚀 Quick Start

### Backend Setup (Java 21 + Spring Boot)

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Install dependencies and run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Application will start on:** `http://localhost:8080`

### Database Access

- **H2 Console:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:hrdb`
- **Username:** `sa`
- **Password:** (empty)

### API Documentation

- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **API Docs:** `http://localhost:8080/api-docs`

## 📊 Database Schema

The system includes the following entities with sample data:

### Core Entities
- **Users** - System authentication
- **Employees** - Employee information
- **Departments** - Organizational departments
- **Positions** - Job positions
- **Addresses** - Employee addresses
- **Salaries** - Salary information
- **Bank Details** - Employee banking information
- **Leaves** - Leave requests and approvals
- **Attendances** - Daily attendance tracking

### Sample Data Included
- 5 Departments (IT, HR, Finance, Marketing, Operations)
- 8 Positions (Developer, Manager, Accountant, etc.)
- 5 Sample employees with complete profiles
- Leave records and attendance data

## 🔧 API Endpoints

### Test & Health Check APIs
```
GET /api/test/health          - Application health check
GET /api/test/db-status       - Database connectivity status
```

### Employee Management APIs
```
GET /api/simple/employees              - Get all employees
GET /api/simple/employees/{id}         - Get employee by ID
GET /api/simple/employees/count        - Get employee count
GET /api/simple/employees/search?employeeId={id} - Search by employee ID
```

### Department Management APIs
```
GET /api/simple/departments            - Get all departments
GET /api/simple/departments/{id}       - Get department by ID
GET /api/simple/departments/count      - Get department count
```

### Authentication APIs (When fixed)
```
POST /api/auth/login                   - User login
POST /api/auth/signup                  - User registration
```

### Advanced APIs (When compilation issues are resolved)
```
GET /api/employees                     - Full employee management
GET /api/leaves                        - Leave management
GET /api/attendances                   - Attendance tracking
GET /api/positions                     - Position management
```

## 🔑 Test Credentials

### Sample Users (When auth is working)
```
Admin User:
- Username: admin
- Email: admin@priacc.com
- Password: password123

HR Manager:
- Username: hr_manager  
- Email: hr@priacc.com
- Password: password123

Employee:
- Username: john_doe
- Email: john.doe@priacc.com
- Password: password123
```

## 📝 API Response Format

All simplified APIs return responses in this format:

```json
{
  "success": true,
  "count": 5,
  "data": [...]
}
```

Error responses:
```json
{
  "success": false,
  "error": "Error message"
}
```

## 🛠️ Current Status

### ✅ Working Features
- H2 Database with sample data
- Basic Employee CRUD operations
- Department management
- Database health checks
- Swagger documentation
- CORS configuration

### 🔧 Known Issues (Being Fixed)
- Lombok annotation processing needs refinement
- Some complex DTOs have compilation issues
- Authentication endpoints need debugging
- Leave and Attendance APIs need DTO fixes

### 🚧 In Progress
- Fixing Lombok configuration for all DTOs
- Implementing proper authentication
- Adding comprehensive leave management
- Attendance tracking system
- Role-based access control

## 🏗️ Architecture

```
├── backend/
│   ├── src/main/java/com/priacc/hrsystem/
│   │   ├── controller/     # REST Controllers
│   │   ├── service/        # Business Logic
│   │   ├── repository/     # Data Access
│   │   ├── model/          # JPA Entities
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── security/       # Security Configuration
│   │   └── config/         # Application Configuration
│   └── src/main/resources/
│       ├── application.properties  # Configuration
│       └── data.sql               # Sample data
```

## 🔗 Database Relationships

- **User** ↔ **Employee** (One-to-One)
- **Department** ↔ **Employee** (One-to-Many)
- **Position** ↔ **Employee** (One-to-Many)
- **Employee** ↔ **Leave** (One-to-Many)
- **Employee** ↔ **Attendance** (One-to-Many)
- **Employee** ↔ **Address** (One-to-One)
- **Employee** ↔ **Salary** (One-to-One)
- **Employee** ↔ **BankDetails** (One-to-One)

## 🚀 Next Steps

1. **Fix Lombok Issues** - Resolve DTO compilation problems
2. **Complete Authentication** - Implement JWT-based auth
3. **Add Leave Management** - Complete leave request/approval flow
4. **Implement Attendance** - Time tracking and reporting
5. **Add Validation** - Input validation and error handling
6. **Frontend Integration** - Connect with React/Angular frontend
7. **Production Setup** - PostgreSQL configuration

## 📞 API Testing

Use the following curl commands to test the APIs:

```bash
# Health check
curl http://localhost:8080/api/test/health

# Get all employees
curl http://localhost:8080/api/simple/employees

# Get employee by ID
curl http://localhost:8080/api/simple/employees/1

# Get all departments
curl http://localhost:8080/api/simple/departments
```

## 🔧 Configuration

### Development (Current)
- Database: H2 in-memory
- Port: 8080
- CORS: Enabled for localhost:3000 and localhost:5173

### Production (Ready to configure)
- Database: PostgreSQL (commented in application.properties)
- Security: JWT with configurable secret
- Logging: Configurable levels

This HR system provides a solid foundation for employee management with room for extensive customization and feature additions.