-- HR System PostgreSQL Sample Data
-- Insert data in correct order due to foreign key constraints

-- Insert Departments
INSERT INTO departments (name, description) VALUES 
('Information Technology', 'IT Department handling all technology-related operations'),
('Human Resources', 'HR Department managing employee relations and policies'),
('Finance', 'Finance Department handling financial operations'),
('Marketing', 'Marketing Department managing brand and customer relations'),
('Operations', 'Operations Department managing daily business operations'),
('Sales', 'Sales Department managing customer acquisition and revenue'),
('Legal', 'Legal Department handling compliance and legal matters'),
('Research & Development', 'R&D Department for innovation and product development');

-- Insert Positions
INSERT INTO positions (title, description, department_id, is_management) VALUES 
('Software Developer', 'Develops and maintains software applications', 1, FALSE),
('Senior Software Developer', 'Senior developer with team leadership responsibilities', 1, TRUE),
('DevOps Engineer', 'Manages deployment and infrastructure', 1, FALSE),
('IT Manager', 'Manages IT operations and team', 1, TRUE),
('HR Manager', 'Manages HR operations and employee relations', 2, TRUE),
('HR Assistant', 'Assists with HR administrative tasks', 2, FALSE),
('Recruiter', 'Handles recruitment and talent acquisition', 2, FALSE),
('Finance Manager', 'Manages financial operations and budgets', 3, TRUE),
('Accountant', 'Handles accounting and financial records', 3, FALSE),
('Financial Analyst', 'Analyzes financial data and trends', 3, FALSE),
('Marketing Manager', 'Manages marketing campaigns and strategies', 4, TRUE),
('Marketing Executive', 'Executes marketing campaigns and activities', 4, FALSE),
('Digital Marketing Specialist', 'Manages digital marketing channels', 4, FALSE),
('Operations Manager', 'Manages daily operations', 5, TRUE),
('Operations Executive', 'Handles operational tasks', 5, FALSE),
('Sales Manager', 'Manages sales team and targets', 6, TRUE),
('Sales Executive', 'Handles sales activities and customer relations', 6, FALSE),
('Legal Counsel', 'Provides legal advice and handles compliance', 7, TRUE),
('Research Scientist', 'Conducts research and development activities', 8, FALSE),
('Product Manager', 'Manages product development and strategy', 8, TRUE);

-- Insert Addresses
INSERT INTO addresses (street, city, state, zip_code, country) VALUES 
('123 Main St', 'New York', 'NY', '10001', 'USA'),
('456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA'),
('789 Pine Rd', 'Chicago', 'IL', '60601', 'USA'),
('321 Elm St', 'Houston', 'TX', '77001', 'USA'),
('654 Maple Dr', 'Phoenix', 'AZ', '85001', 'USA'),
('987 Cedar Ln', 'Philadelphia', 'PA', '19101', 'USA'),
('147 Birch Way', 'San Antonio', 'TX', '78201', 'USA'),
('258 Willow St', 'San Diego', 'CA', '92101', 'USA'),
('369 Spruce Ave', 'Dallas', 'TX', '75201', 'USA'),
('741 Ash Blvd', 'San Jose', 'CA', '95101', 'USA'),
('852 Palm St', 'Austin', 'TX', '73301', 'USA'),
('963 Fir Dr', 'Jacksonville', 'FL', '32099', 'USA'),
('159 Poplar Rd', 'Fort Worth', 'TX', '76101', 'USA'),
('357 Hickory Ave', 'Columbus', 'OH', '43085', 'USA'),
('486 Redwood Ln', 'San Francisco', 'CA', '94102', 'USA');

-- Insert Bank Details
INSERT INTO bank_details (account_number, bank_name, ifsc_code) VALUES 
('1234567890', 'Chase Bank', 'CHAS0001234'),
('2345678901', 'Bank of America', 'BOFA0001234'),
('3456789012', 'Wells Fargo', 'WELL0001234'),
('4567890123', 'Citibank', 'CITI0001234'),
('5678901234', 'JPMorgan Chase', 'JPMC0001234'),
('6789012345', 'PNC Bank', 'PNCB0001234'),
('7890123456', 'US Bank', 'USBK0001234'),
('8901234567', 'TD Bank', 'TDBA0001234'),
('9012345678', 'Capital One', 'CAPO0001234'),
('0123456789', 'HSBC Bank', 'HSBC0001234'),
('1357924680', 'Discover Bank', 'DISC0001234'),
('2468135790', 'American Express', 'AMEX0001234'),
('3691470258', 'Goldman Sachs', 'GOLD0001234'),
('4815926370', 'Morgan Stanley', 'MORG0001234'),
('5927384610', 'Charles Schwab', 'SCHW0001234');

-- Insert Salaries
INSERT INTO salaries (amount, currency, effective_date, basic, hra, bonus) VALUES 
(75000.00, 'USD', '2024-01-01', 60000.00, 10000.00, 5000.00),
(95000.00, 'USD', '2024-01-01', 80000.00, 12000.00, 3000.00),
(85000.00, 'USD', '2024-01-01', 70000.00, 11000.00, 4000.00),
(55000.00, 'USD', '2024-01-01', 45000.00, 7000.00, 3000.00),
(90000.00, 'USD', '2024-01-01', 75000.00, 12000.00, 3000.00),
(120000.00, 'USD', '2024-01-01', 100000.00, 15000.00, 5000.00),
(65000.00, 'USD', '2024-01-01', 52000.00, 8000.00, 5000.00),
(110000.00, 'USD', '2024-01-01', 90000.00, 15000.00, 5000.00),
(70000.00, 'USD', '2024-01-01', 55000.00, 10000.00, 5000.00),
(80000.00, 'USD', '2024-01-01', 65000.00, 10000.00, 5000.00),
(105000.00, 'USD', '2024-01-01', 85000.00, 15000.00, 5000.00),
(60000.00, 'USD', '2024-01-01', 48000.00, 8000.00, 4000.00),
(72000.00, 'USD', '2024-01-01', 58000.00, 9000.00, 5000.00),
(95000.00, 'USD', '2024-01-01', 78000.00, 12000.00, 5000.00),
(58000.00, 'USD', '2024-01-01', 46000.00, 8000.00, 4000.00);

-- Insert Users (password is 'password123' hashed with BCrypt)
INSERT INTO users (username, email, password, role, is_active) VALUES 
('admin', 'admin@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'ADMIN', TRUE),
('hr_manager', 'hr@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'HR_ADMIN', TRUE),
('john_doe', 'john.doe@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE),
('jane_smith', 'jane.smith@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'MANAGER', TRUE),
('bob_johnson', 'bob.johnson@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE),
('alice_williams', 'alice.williams@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'MANAGER', TRUE),
('mike_brown', 'mike.brown@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE),
('sarah_davis', 'sarah.davis@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE),
('david_wilson', 'david.wilson@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'MANAGER', TRUE),
('lisa_garcia', 'lisa.garcia@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE),
('tom_martinez', 'tom.martinez@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'MANAGER', TRUE),
('emma_taylor', 'emma.taylor@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE),
('chris_anderson', 'chris.anderson@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE),
('nicole_thomas', 'nicole.thomas@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'MANAGER', TRUE),
('ryan_jackson', 'ryan.jackson@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', TRUE);

-- Insert Employees
INSERT INTO employees (employee_id, first_name, last_name, email, mobile_phone, phone, date_of_birth, hire_date, marital_status, nationality, gender, employee_type, status, user_id, department_id, position_id, manager_id, address_id, salary_id, bank_details_id) VALUES 
('EMP001', 'System', 'Administrator', 'admin@priacc.com', '+1234567890', '+1234567890', '1985-05-15', '2020-01-15', 'SINGLE', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 1, 1, 4, NULL, 1, 6, 1),
('EMP002', 'Sarah', 'Wilson', 'hr@priacc.com', '+1234567891', '+1234567891', '1988-08-22', '2021-03-10', 'MARRIED', 'American', 'FEMALE', 'FULL_TIME', 'ACTIVE', 2, 2, 5, 1, 2, 5, 2),
('EMP003', 'John', 'Doe', 'john.doe@priacc.com', '+1234567892', '+1234567892', '1990-12-03', '2022-06-15', 'SINGLE', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 3, 1, 1, 1, 3, 1, 3),
('EMP004', 'Jane', 'Smith', 'jane.smith@priacc.com', '+1234567893', '+1234567893', '1987-04-18', '2021-09-20', 'MARRIED', 'American', 'FEMALE', 'FULL_TIME', 'ACTIVE', 4, 3, 8, 1, 4, 8, 4),
('EMP005', 'Bob', 'Johnson', 'bob.johnson@priacc.com', '+1234567894', '+1234567894', '1992-11-08', '2023-02-01', 'SINGLE', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 5, 1, 1, 3, 5, 1, 5),
('EMP006', 'Alice', 'Williams', 'alice.williams@priacc.com', '+1234567895', '+1234567895', '1986-03-12', '2020-05-20', 'MARRIED', 'American', 'FEMALE', 'FULL_TIME', 'ACTIVE', 6, 4, 11, 1, 6, 11, 6),
('EMP007', 'Mike', 'Brown', 'mike.brown@priacc.com', '+1234567896', '+1234567896', '1991-07-25', '2022-08-10', 'SINGLE', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 7, 1, 3, 1, 7, 7, 7),
('EMP008', 'Sarah', 'Davis', 'sarah.davis@priacc.com', '+1234567897', '+1234567897', '1989-01-30', '2021-11-15', 'MARRIED', 'American', 'FEMALE', 'FULL_TIME', 'ACTIVE', 8, 2, 7, 2, 8, 4, 8),
('EMP009', 'David', 'Wilson', 'david.wilson@priacc.com', '+1234567898', '+1234567898', '1984-09-18', '2019-04-08', 'MARRIED', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 9, 5, 14, 1, 9, 14, 9),
('EMP010', 'Lisa', 'Garcia', 'lisa.garcia@priacc.com', '+1234567899', '+1234567899', '1993-06-14', '2023-01-12', 'SINGLE', 'American', 'FEMALE', 'FULL_TIME', 'ACTIVE', 10, 3, 10, 4, 10, 10, 10),
('EMP011', 'Tom', 'Martinez', 'tom.martinez@priacc.com', '+1234567800', '+1234567800', '1983-12-05', '2018-07-22', 'MARRIED', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 11, 6, 16, 1, 11, 8, 11),
('EMP012', 'Emma', 'Taylor', 'emma.taylor@priacc.com', '+1234567801', '+1234567801', '1994-02-28', '2023-03-05', 'SINGLE', 'American', 'FEMALE', 'FULL_TIME', 'ACTIVE', 12, 4, 13, 6, 12, 13, 12),
('EMP013', 'Chris', 'Anderson', 'chris.anderson@priacc.com', '+1234567802', '+1234567802', '1988-10-11', '2020-12-18', 'MARRIED', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 13, 6, 17, 11, 13, 12, 13),
('EMP014', 'Nicole', 'Thomas', 'nicole.thomas@priacc.com', '+1234567803', '+1234567803', '1985-08-07', '2019-09-30', 'MARRIED', 'American', 'FEMALE', 'FULL_TIME', 'ACTIVE', 14, 8, 20, 1, 14, 11, 14),
('EMP015', 'Ryan', 'Jackson', 'ryan.jackson@priacc.com', '+1234567804', '+1234567804', '1991-04-22', '2022-01-25', 'SINGLE', 'American', 'MALE', 'FULL_TIME', 'ACTIVE', 15, 8, 19, 14, 15, 15, 15);

-- Insert Leave records
INSERT INTO leaves (employee_id, leave_type, start_date, end_date, number_of_days, reason, status, applied_date, approved_by, approved_date) VALUES 
(3, 'ANNUAL', '2024-02-15', '2024-02-19', 5, 'Family vacation', 'APPROVED', '2024-02-01', 2, '2024-02-02 10:30:00'),
(5, 'SICK', '2024-01-20', '2024-01-22', 3, 'Flu symptoms', 'APPROVED', '2024-01-20', 2, '2024-01-20 14:15:00'),
(4, 'ANNUAL', '2024-03-10', '2024-03-17', 7, 'Wedding anniversary trip', 'PENDING', '2024-02-25', NULL, NULL),
(7, 'SICK', '2024-01-15', '2024-01-16', 2, 'Food poisoning', 'APPROVED', '2024-01-15', 1, '2024-01-15 09:45:00'),
(10, 'ANNUAL', '2024-04-01', '2024-04-05', 5, 'Spring break vacation', 'PENDING', '2024-03-15', NULL, NULL),
(12, 'EMERGENCY', '2024-01-25', '2024-01-25', 1, 'Family emergency', 'APPROVED', '2024-01-25', 6, '2024-01-25 11:20:00'),
(15, 'ANNUAL', '2024-05-20', '2024-05-24', 5, 'Personal travel', 'PENDING', '2024-05-01', NULL, NULL),
(8, 'MATERNITY', '2024-06-01', '2024-08-30', 90, 'Maternity leave', 'APPROVED', '2024-05-15', 2, '2024-05-16 16:00:00'),
(13, 'SICK', '2024-02-10', '2024-02-12', 3, 'Migraine', 'APPROVED', '2024-02-10', 11, '2024-02-10 13:30:00'),
(6, 'ANNUAL', '2024-07-15', '2024-07-26', 10, 'Summer vacation', 'PENDING', '2024-06-20', NULL, NULL);

-- Insert Attendance records (last 30 days sample)
INSERT INTO attendances (employee_id, date, check_in_time, check_out_time, break_start_time, break_end_time, work_hours, status, ip_address, location) VALUES 
-- EMP003 (John Doe) - Recent attendance
(3, '2024-01-15', '09:00:00', '17:30:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.101', 'New York Office'),
(3, '2024-01-16', '09:05:00', '17:25:00', '12:00:00', '13:00:00', 7.33, 'PRESENT', '192.168.1.101', 'New York Office'),
(3, '2024-01-17', '08:55:00', '17:35:00', '12:00:00', '13:00:00', 7.67, 'PRESENT', '192.168.1.101', 'New York Office'),
(3, '2024-01-18', '09:10:00', '17:20:00', '12:00:00', '13:00:00', 7.17, 'PRESENT', '192.168.1.101', 'New York Office'),
(3, '2024-01-19', '09:00:00', '17:30:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.101', 'New York Office'),

-- EMP004 (Jane Smith) - Recent attendance
(4, '2024-01-15', '08:45:00', '17:15:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.102', 'Houston Office'),
(4, '2024-01-16', '08:50:00', '17:20:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.102', 'Houston Office'),
(4, '2024-01-17', '08:40:00', '17:10:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.102', 'Houston Office'),
(4, '2024-01-18', '08:55:00', '17:25:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.102', 'Houston Office'),
(4, '2024-01-19', '08:45:00', '17:15:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.102', 'Houston Office'),

-- EMP005 (Bob Johnson) - Recent attendance
(5, '2024-01-15', '09:15:00', '18:00:00', '12:30:00', '13:30:00', 7.75, 'PRESENT', '192.168.1.103', 'Phoenix Office'),
(5, '2024-01-16', '09:20:00', '17:50:00', '12:30:00', '13:30:00', 7.5, 'PRESENT', '192.168.1.103', 'Phoenix Office'),
(5, '2024-01-17', '09:10:00', '17:40:00', '12:30:00', '13:30:00', 7.5, 'PRESENT', '192.168.1.103', 'Phoenix Office'),
(5, '2024-01-18', '09:25:00', '17:55:00', '12:30:00', '13:30:00', 7.5, 'LATE', '192.168.1.103', 'Phoenix Office'),
(5, '2024-01-19', '09:15:00', '18:00:00', '12:30:00', '13:30:00', 7.75, 'PRESENT', '192.168.1.103', 'Phoenix Office'),

-- EMP007 (Mike Brown) - Recent attendance with some absent days
(7, '2024-01-15', NULL, NULL, NULL, NULL, 0, 'ABSENT', NULL, NULL),
(7, '2024-01-16', NULL, NULL, NULL, NULL, 0, 'ABSENT', NULL, NULL),
(7, '2024-01-17', '09:30:00', '17:45:00', '12:00:00', '13:00:00', 7.25, 'PRESENT', '192.168.1.104', 'Philadelphia Office'),
(7, '2024-01-18', '09:00:00', '17:30:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.104', 'Philadelphia Office'),
(7, '2024-01-19', '09:05:00', '17:35:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.104', 'Philadelphia Office'),

-- EMP010 (Lisa Garcia) - Recent attendance
(10, '2024-01-15', '08:30:00', '17:00:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.105', 'San Jose Office'),
(10, '2024-01-16', '08:35:00', '17:05:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.105', 'San Jose Office'),
(10, '2024-01-17', '08:25:00', '16:55:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.105', 'San Jose Office'),
(10, '2024-01-18', '08:40:00', '17:10:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.105', 'San Jose Office'),
(10, '2024-01-19', '08:30:00', '17:00:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', '192.168.1.105', 'San Jose Office');

-- Add some weekend and holiday data
INSERT INTO attendances (employee_id, date, check_in_time, check_out_time, break_start_time, break_end_time, work_hours, status) VALUES 
-- Weekend work (overtime)
(1, '2024-01-20', '10:00:00', '14:00:00', NULL, NULL, 4.0, 'PRESENT'),
(3, '2024-01-21', '09:00:00', '13:00:00', NULL, NULL, 4.0, 'PRESENT');

-- Update sequence values to avoid conflicts
SELECT setval('departments_id_seq', 20);
SELECT setval('positions_id_seq', 30);
SELECT setval('addresses_id_seq', 20);
SELECT setval('bank_details_id_seq', 20);
SELECT setval('salaries_id_seq', 20);
SELECT setval('users_id_seq', 20);
SELECT setval('employees_id_seq', 20);
SELECT setval('leaves_id_seq', 20);
SELECT setval('attendances_id_seq', 50);