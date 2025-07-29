-- Insert Departments
INSERT INTO departments (id, name, description, created_date, last_modified_date) VALUES 
(1, 'Information Technology', 'IT Department handling all technology-related operations', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Human Resources', 'HR Department managing employee relations and policies', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Finance', 'Finance Department handling financial operations', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Marketing', 'Marketing Department managing brand and customer relations', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Operations', 'Operations Department managing daily business operations', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Positions
INSERT INTO positions (id, title, description, department_id, is_management, created_date, last_modified_date) VALUES 
(1, 'Software Developer', 'Develops and maintains software applications', 1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Senior Software Developer', 'Senior developer with team leadership responsibilities', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'HR Manager', 'Manages HR operations and employee relations', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'HR Assistant', 'Assists with HR administrative tasks', 2, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Finance Manager', 'Manages financial operations and budgets', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Accountant', 'Handles accounting and financial records', 3, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Marketing Manager', 'Manages marketing campaigns and strategies', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Marketing Executive', 'Executes marketing campaigns and activities', 4, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Addresses
INSERT INTO addresses (id, street, city, state, zip_code, country) VALUES 
(1, '123 Main St', 'New York', 'NY', '10001', 'USA'),
(2, '456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA'),
(3, '789 Pine Rd', 'Chicago', 'IL', '60601', 'USA'),
(4, '321 Elm St', 'Houston', 'TX', '77001', 'USA'),
(5, '654 Maple Dr', 'Phoenix', 'AZ', '85001', 'USA');

-- Insert Bank Details
INSERT INTO bank_details (id, account_number, bank_name, ifsc_code) VALUES 
(1, '1234567890', 'Chase Bank', 'CHAS0001234'),
(2, '2345678901', 'Bank of America', 'BOFA0001234'),
(3, '3456789012', 'Wells Fargo', 'WELL0001234'),
(4, '4567890123', 'Citibank', 'CITI0001234'),
(5, '5678901234', 'JPMorgan Chase', 'JPMC0001234');

-- Insert Salaries
INSERT INTO salaries (id, amount, currency, effective_date, basic, hra, bonus) VALUES 
(1, 75000.00, 'USD', '2024-01-01', 60000.00, 10000.00, 5000.00),
(2, 95000.00, 'USD', '2024-01-01', 80000.00, 12000.00, 3000.00),
(3, 85000.00, 'USD', '2024-01-01', 70000.00, 11000.00, 4000.00),
(4, 55000.00, 'USD', '2024-01-01', 45000.00, 7000.00, 3000.00),
(5, 90000.00, 'USD', '2024-01-01', 75000.00, 12000.00, 3000.00);

-- Insert Users (Note: passwords are 'password123' encoded with BCrypt)
INSERT INTO users (id, username, email, password, role, is_active, created_date, last_modified_date) VALUES 
(1, 'admin', 'admin@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'hr_manager', 'hr@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'HR_ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'john_doe', 'john.doe@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'jane_smith', 'jane.smith@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'MANAGER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'bob_johnson', 'bob.johnson@priacc.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HI/2NhWNd6kh4xs.6FfIu', 'EMPLOYEE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Employees
INSERT INTO employees (id, employee_id, first_name, last_name, email, mobile_phone, date_of_birth, hire_date, marital_status, nationality, employee_type, status, user_id, department_id, position_id, address_id, salary_id, bank_details_id, created_date, last_modified_date) VALUES 
(1, 'EMP001', 'System', 'Administrator', 'admin@priacc.com', '+1234567890', '1985-05-15', '2020-01-15', 'SINGLE', 'American', 'FULL_TIME', 'ACTIVE', 1, 1, 2, 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'EMP002', 'Sarah', 'Wilson', 'hr@priacc.com', '+1234567891', '1988-08-22', '2021-03-10', 'MARRIED', 'American', 'FULL_TIME', 'ACTIVE', 2, 2, 3, 2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'EMP003', 'John', 'Doe', 'john.doe@priacc.com', '+1234567892', '1990-12-03', '2022-06-15', 'SINGLE', 'American', 'FULL_TIME', 'ACTIVE', 3, 1, 1, 3, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'EMP004', 'Jane', 'Smith', 'jane.smith@priacc.com', '+1234567893', '1987-04-18', '2021-09-20', 'MARRIED', 'American', 'FULL_TIME', 'ACTIVE', 4, 3, 5, 4, 5, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'EMP005', 'Bob', 'Johnson', 'bob.johnson@priacc.com', '+1234567894', '1992-11-08', '2023-02-01', 'SINGLE', 'American', 'FULL_TIME', 'ACTIVE', 5, 1, 1, 5, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert some Leave records
INSERT INTO leaves (id, employee_id, leave_type, start_date, end_date, number_of_days, reason, status, applied_date, approved_by, approved_date, created_date, last_modified_date) VALUES 
(1, 3, 'ANNUAL', '2024-02-15', '2024-02-19', 5, 'Family vacation', 'APPROVED', '2024-02-01', 2, '2024-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 'SICK', '2024-01-20', '2024-01-22', 3, 'Flu symptoms', 'APPROVED', '2024-01-20', 2, '2024-01-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 'ANNUAL', '2024-03-10', '2024-03-17', 7, 'Wedding anniversary trip', 'PENDING', '2024-02-25', NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert some Attendance records for the last week
INSERT INTO attendances (id, employee_id, date, check_in_time, check_out_time, break_start_time, break_end_time, work_hours, status, created_date, last_modified_date) VALUES 
(1, 3, '2024-01-15', '09:00:00', '17:30:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, '2024-01-15', '08:45:00', '17:15:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 5, '2024-01-15', '09:15:00', '18:00:00', '12:30:00', '13:30:00', 7.75, 'PRESENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, '2024-01-16', '09:05:00', '17:25:00', '12:00:00', '13:00:00', 7.33, 'PRESENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, '2024-01-16', '08:50:00', '17:20:00', '12:00:00', '13:00:00', 7.5, 'PRESENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);