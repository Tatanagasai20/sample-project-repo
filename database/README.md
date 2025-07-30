# HR System Database Setup

This directory contains complete database setups for the HR System with multiple database options.

## 🗄️ Available Database Options

### 1. H2 Database (Development - Default)
- **Type**: In-memory database
- **Usage**: Development and testing
- **Setup**: Automatic (no installation required)
- **Access**: H2 Console at `http://localhost:8080/h2-console`

### 2. PostgreSQL (Recommended for Production)
- **Type**: Production-ready relational database
- **Setup**: Docker Compose or manual installation
- **Access**: pgAdmin at `http://localhost:8081`

### 3. MySQL (Alternative Production Option)
- **Type**: Popular relational database
- **Setup**: Docker Compose or manual installation
- **Access**: phpMyAdmin at `http://localhost:8082`

## 🚀 Quick Start with Docker

### Option 1: Start All Databases
```bash
cd database
docker-compose up -d
```

### Option 2: Start Specific Database

**PostgreSQL only:**
```bash
docker-compose up -d postgres-hr pgadmin
```

**MySQL only:**
```bash
docker-compose up -d mysql-hr phpmyadmin
```

**With Redis caching:**
```bash
docker-compose up -d postgres-hr pgadmin redis-hr redis-commander
```

## 📊 Database Services & Ports

| Service | URL | Port | Credentials |
|---------|-----|------|-------------|
| PostgreSQL | localhost:5432 | 5432 | postgres/postgres |
| pgAdmin | http://localhost:8081 | 8081 | admin@priacc.com/admin123 |
| MySQL | localhost:3306 | 3306 | hruser/hrpassword |
| phpMyAdmin | http://localhost:8082 | 8082 | hruser/hrpassword |
| H2 Console | http://localhost:8080/h2-console | 8080 | sa/(empty) |
| Redis | localhost:6379 | 6379 | (no auth) |
| Redis Commander | http://localhost:8083 | 8083 | (no auth) |

## 🏗️ Database Schema

### Core Tables
- **departments** - Organizational departments
- **positions** - Job positions and roles
- **users** - System authentication
- **employees** - Employee information
- **addresses** - Employee addresses
- **salaries** - Salary information
- **bank_details** - Banking information
- **leaves** - Leave requests and approvals
- **attendances** - Daily attendance tracking

### Relationships
```
users (1:1) employees
departments (1:many) employees
positions (1:many) employees
employees (1:many) leaves
employees (1:many) attendances
employees (1:1) addresses
employees (1:1) salaries
employees (1:1) bank_details
```

## 📝 Sample Data Included

### Users & Employees (15 total)
- **Admin**: admin@priacc.com / password123
- **HR Manager**: hr@priacc.com / password123
- **Employees**: john.doe@priacc.com / password123
- **Managers**: jane.smith@priacc.com / password123

### Departments (8 total)
- Information Technology
- Human Resources
- Finance
- Marketing
- Operations
- Sales
- Legal
- Research & Development

### Sample Data Counts
- 15 Users and Employees
- 8 Departments
- 20 Positions
- 15 Addresses
- 15 Bank Details
- 15 Salary Records
- 10 Leave Records
- 27 Attendance Records

## ⚙️ Spring Boot Configuration

### Use H2 (Default)
```bash
mvn spring-boot:run
# or
java -jar target/hr-system.jar
```

### Use PostgreSQL
```bash
mvn spring-boot:run -Dspring.profiles.active=postgres
# or
java -jar target/hr-system.jar --spring.profiles.active=postgres
```

### Use MySQL
```bash
mvn spring-boot:run -Dspring.profiles.active=mysql
# or
java -jar target/hr-system.jar --spring.profiles.active=mysql
```

## 🔧 Manual Database Setup

### PostgreSQL Manual Setup

1. **Install PostgreSQL**
   ```bash
   # Ubuntu/Debian
   sudo apt update
   sudo apt install postgresql postgresql-contrib
   
   # macOS (with Homebrew)
   brew install postgresql
   
   # Windows: Download from https://www.postgresql.org/download/
   ```

2. **Create Database and User**
   ```sql
   sudo -u postgres psql
   CREATE DATABASE priacc_hr_system;
   CREATE USER postgres WITH PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE priacc_hr_system TO postgres;
   \q
   ```

3. **Run Schema and Data**
   ```bash
   psql -U postgres -d priacc_hr_system -f database/postgresql/schema.sql
   psql -U postgres -d priacc_hr_system -f database/postgresql/data.sql
   ```

### MySQL Manual Setup

1. **Install MySQL**
   ```bash
   # Ubuntu/Debian
   sudo apt update
   sudo apt install mysql-server
   
   # macOS (with Homebrew)
   brew install mysql
   
   # Windows: Download from https://dev.mysql.com/downloads/mysql/
   ```

2. **Create Database and User**
   ```sql
   mysql -u root -p
   CREATE DATABASE priacc_hr_system;
   CREATE USER 'hruser'@'localhost' IDENTIFIED BY 'hrpassword';
   GRANT ALL PRIVILEGES ON priacc_hr_system.* TO 'hruser'@'localhost';
   FLUSH PRIVILEGES;
   EXIT;
   ```

3. **Run Schema and Data**
   ```bash
   mysql -u hruser -p priacc_hr_system < database/mysql/schema.sql
   mysql -u hruser -p priacc_hr_system < database/mysql/data.sql
   ```

## 🔍 Database Management Tools

### pgAdmin (PostgreSQL)
- **URL**: http://localhost:8081
- **Email**: admin@priacc.com
- **Password**: admin123
- **Server**: postgres-hr (hostname when using Docker)

### phpMyAdmin (MySQL)
- **URL**: http://localhost:8082
- **Username**: hruser
- **Password**: hrpassword
- **Server**: mysql-hr (hostname when using Docker)

### H2 Console
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:hrdb
- **Username**: sa
- **Password**: (empty)

## 🔄 Database Migration

### From H2 to PostgreSQL
1. Stop the application
2. Start PostgreSQL with Docker or manually
3. Update application.properties or use postgres profile
4. Restart application

### From H2 to MySQL
1. Stop the application
2. Start MySQL with Docker or manually
3. Update application.properties or use mysql profile
4. Restart application

## 🛠️ Maintenance Commands

### Docker Commands
```bash
# Start all services
docker-compose up -d

# Stop all services
docker-compose down

# View logs
docker-compose logs -f postgres-hr
docker-compose logs -f mysql-hr

# Restart specific service
docker-compose restart postgres-hr

# Remove all data and restart fresh
docker-compose down -v
docker-compose up -d
```

### Database Backup
```bash
# PostgreSQL backup
docker exec hr_postgres pg_dump -U postgres priacc_hr_system > backup.sql

# MySQL backup
docker exec hr_mysql mysqldump -u hruser -phrpassword priacc_hr_system > backup.sql
```

## 🔧 Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Check what's using the port
   lsof -i :5432  # PostgreSQL
   lsof -i :3306  # MySQL
   
   # Change ports in docker-compose.yml if needed
   ```

2. **Permission Denied (PostgreSQL)**
   ```bash
   # Fix data directory permissions
   sudo chown -R 999:999 postgres_data/
   ```

3. **Connection Refused**
   - Ensure Docker services are running
   - Check firewall settings
   - Verify connection strings in application.properties

4. **Data Not Loading**
   - Check init scripts are in correct location
   - Verify file permissions
   - Check container logs for errors

## 📈 Performance Optimization

### PostgreSQL
- Enable connection pooling
- Tune shared_buffers and work_mem
- Add indexes for frequently queried columns

### MySQL
- Enable query cache
- Optimize innodb_buffer_pool_size
- Use appropriate storage engines

### Redis Caching
- Configure Spring Cache with Redis
- Cache frequent database queries
- Set appropriate TTL values

## 🔐 Security Considerations

### Production Settings
- Change default passwords
- Use environment variables for credentials
- Enable SSL/TLS connections
- Restrict network access
- Regular security updates
- Database user permission restrictions

### Environment Variables
```bash
# .env file for production
POSTGRES_PASSWORD=your_secure_password
MYSQL_ROOT_PASSWORD=your_secure_root_password
MYSQL_PASSWORD=your_secure_password
JWT_SECRET=your_very_long_jwt_secret_key
```

This database setup provides a robust foundation for the HR system with multiple deployment options and comprehensive management tools.