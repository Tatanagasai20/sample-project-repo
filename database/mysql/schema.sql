-- HR System MySQL Database Schema
-- Set MySQL specific settings
SET FOREIGN_KEY_CHECKS = 0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

-- Drop existing tables if they exist (in correct order due to foreign keys)
DROP TABLE IF EXISTS `attendances`;
DROP TABLE IF EXISTS `leaves`;
DROP TABLE IF EXISTS `employees`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `positions`;
DROP TABLE IF EXISTS `departments`;
DROP TABLE IF EXISTS `addresses`;
DROP TABLE IF EXISTS `salaries`;
DROP TABLE IF EXISTS `bank_details`;

-- Create Departments table
CREATE TABLE `departments` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` TEXT,
    `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_departments_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Positions table
CREATE TABLE `positions` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `description` TEXT,
    `department_id` BIGINT(20) UNSIGNED,
    `is_management` BOOLEAN DEFAULT FALSE,
    `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `fk_positions_department` (`department_id`),
    CONSTRAINT `fk_positions_department` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Addresses table
CREATE TABLE `addresses` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `street` VARCHAR(255),
    `city` VARCHAR(100),
    `state` VARCHAR(100),
    `zip_code` VARCHAR(20),
    `country` VARCHAR(100) DEFAULT 'USA',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Bank Details table
CREATE TABLE `bank_details` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `account_number` VARCHAR(50) NOT NULL,
    `bank_name` VARCHAR(100) NOT NULL,
    `ifsc_code` VARCHAR(20),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Salaries table
CREATE TABLE `salaries` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `amount` DECIMAL(10,2) NOT NULL,
    `currency` VARCHAR(10) DEFAULT 'USD',
    `effective_date` DATE NOT NULL,
    `basic` DECIMAL(10,2) DEFAULT 0,
    `hra` DECIMAL(10,2) DEFAULT 0,
    `bonus` DECIMAL(10,2) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Users table
CREATE TABLE `users` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('ADMIN', 'HR_ADMIN', 'MANAGER', 'EMPLOYEE') NOT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    `last_login_at` TIMESTAMP NULL,
    `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_users_username` (`username`),
    UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Employees table
CREATE TABLE `employees` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `employee_id` VARCHAR(20) NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `mobile_phone` VARCHAR(20),
    `phone` VARCHAR(20),
    `date_of_birth` DATE,
    `hire_date` DATE NOT NULL,
    `marital_status` ENUM('SINGLE', 'MARRIED', 'DIVORCED', 'WIDOWED'),
    `nationality` VARCHAR(50),
    `gender` ENUM('MALE', 'FEMALE', 'OTHER'),
    `employee_type` ENUM('FULL_TIME', 'PART_TIME', 'CONTRACT', 'INTERN'),
    `status` ENUM('ACTIVE', 'INACTIVE', 'TERMINATED', 'ON_LEAVE') DEFAULT 'ACTIVE',
    `user_id` BIGINT(20) UNSIGNED,
    `department_id` BIGINT(20) UNSIGNED,
    `position_id` BIGINT(20) UNSIGNED,
    `manager_id` BIGINT(20) UNSIGNED,
    `address_id` BIGINT(20) UNSIGNED,
    `salary_id` BIGINT(20) UNSIGNED,
    `bank_details_id` BIGINT(20) UNSIGNED,
    `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employees_employee_id` (`employee_id`),
    UNIQUE KEY `uk_employees_email` (`email`),
    KEY `fk_employees_user` (`user_id`),
    KEY `fk_employees_department` (`department_id`),
    KEY `fk_employees_position` (`position_id`),
    KEY `fk_employees_manager` (`manager_id`),
    KEY `fk_employees_address` (`address_id`),
    KEY `fk_employees_salary` (`salary_id`),
    KEY `fk_employees_bank_details` (`bank_details_id`),
    CONSTRAINT `fk_employees_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_employees_department` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_employees_position` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_employees_manager` FOREIGN KEY (`manager_id`) REFERENCES `employees` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_employees_address` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_employees_salary` FOREIGN KEY (`salary_id`) REFERENCES `salaries` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_employees_bank_details` FOREIGN KEY (`bank_details_id`) REFERENCES `bank_details` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Leaves table
CREATE TABLE `leaves` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `employee_id` BIGINT(20) UNSIGNED NOT NULL,
    `leave_type` ENUM('ANNUAL', 'SICK', 'MATERNITY', 'PATERNITY', 'EMERGENCY', 'UNPAID') NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `number_of_days` INT NOT NULL,
    `reason` TEXT NOT NULL,
    `status` ENUM('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED', 'TAKEN') DEFAULT 'PENDING',
    `comments` TEXT,
    `applied_date` DATE DEFAULT (CURDATE()),
    `approved_by` BIGINT(20) UNSIGNED,
    `approved_date` TIMESTAMP NULL,
    `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `fk_leaves_employee` (`employee_id`),
    KEY `fk_leaves_approved_by` (`approved_by`),
    KEY `idx_leaves_status` (`status`),
    KEY `idx_leaves_dates` (`start_date`, `end_date`),
    CONSTRAINT `fk_leaves_employee` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_leaves_approved_by` FOREIGN KEY (`approved_by`) REFERENCES `employees` (`id`) ON DELETE SET NULL,
    CONSTRAINT `chk_leaves_valid_dates` CHECK (`start_date` <= `end_date`),
    CONSTRAINT `chk_leaves_positive_days` CHECK (`number_of_days` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Attendances table
CREATE TABLE `attendances` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `employee_id` BIGINT(20) UNSIGNED NOT NULL,
    `date` DATE NOT NULL,
    `check_in_time` TIME,
    `check_out_time` TIME,
    `break_start_time` TIME,
    `break_end_time` TIME,
    `work_hours` DECIMAL(4,2) DEFAULT 0,
    `status` ENUM('PRESENT', 'ABSENT', 'LATE', 'HALF_DAY', 'ON_LEAVE') DEFAULT 'PRESENT',
    `ip_address` VARCHAR(45),
    `location` VARCHAR(255),
    `notes` TEXT,
    `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_attendances_employee_date` (`employee_id`, `date`),
    KEY `fk_attendances_employee` (`employee_id`),
    KEY `idx_attendances_date` (`date`),
    CONSTRAINT `fk_attendances_employee` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create additional indexes for better performance
CREATE INDEX `idx_employees_employee_id` ON `employees` (`employee_id`);
CREATE INDEX `idx_employees_email` ON `employees` (`email`);
CREATE INDEX `idx_users_email` ON `users` (`email`);
CREATE INDEX `idx_users_username` ON `users` (`username`);

-- Set AUTO_INCREMENT starting values
ALTER TABLE `departments` AUTO_INCREMENT = 1;
ALTER TABLE `positions` AUTO_INCREMENT = 1;
ALTER TABLE `addresses` AUTO_INCREMENT = 1;
ALTER TABLE `bank_details` AUTO_INCREMENT = 1;
ALTER TABLE `salaries` AUTO_INCREMENT = 1;
ALTER TABLE `users` AUTO_INCREMENT = 1;
ALTER TABLE `employees` AUTO_INCREMENT = 1;
ALTER TABLE `leaves` AUTO_INCREMENT = 1;
ALTER TABLE `attendances` AUTO_INCREMENT = 1;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;