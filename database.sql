-- Law Firm Management System Database Schema
-- Run this in phpMyAdmin after starting XAMPP MySQL

CREATE DATABASE IF NOT EXISTS lawfirm_db;
USE lawfirm_db;

-- Users table (for all roles: admin, lawyer, client)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role ENUM('admin', 'lawyer', 'client', 'staff') NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Admin credentials (for initial setup)
CREATE TABLE admin_config (
    config_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_username VARCHAR(50) NOT NULL,
    admin_password VARCHAR(255) NOT NULL
);

-- Lawyers table (extends users)
CREATE TABLE lawyers (
    lawyer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    bar_number VARCHAR(50),
    specialties TEXT,
    years_experience INT,
    hourly_rate DECIMAL(10,2),
    active_cases INT DEFAULT 0,
    cases_won INT DEFAULT 0,
    cases_lost INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Clients table (extends users)
CREATE TABLE clients (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    company_name VARCHAR(100),
    contact_person VARCHAR(100),
    billing_address TEXT,
    retainer_balance DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Cases table
CREATE TABLE cases (
    case_id INT AUTO_INCREMENT PRIMARY KEY,
    case_number VARCHAR(50) UNIQUE,
    case_name VARCHAR(200),
    case_type ENUM('Civil', 'Criminal', 'Corporate', 'Family', 'Real Estate', 'IP', 'Other'),
    description TEXT,
    client_id INT,
    primary_lawyer_id INT,
    co_counsel_ids TEXT,
    court_name VARCHAR(100),
    jurisdiction VARCHAR(100),
    filed_date DATE,
    status ENUM('Active', 'Pending', 'Closed', 'On Hold', 'Appeal') DEFAULT 'Active',
    fee_type ENUM('Contingency', 'Hourly', 'Flat', 'Retainer'),
    hourly_rate DECIMAL(10,2),
    opposing_party VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(client_id),
    FOREIGN KEY (primary_lawyer_id) REFERENCES lawyers(lawyer_id)
);

-- Case events (hearings, deadlines, meetings)
CREATE TABLE case_events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    event_type ENUM('Hearing', 'Deadline', 'Meeting', 'Filing') NOT NULL,
    title VARCHAR(200),
    description TEXT,
    event_date DATETIME,
    location VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE
);

-- Time entries for billing
CREATE TABLE time_entries (
    entry_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    lawyer_id INT,
    activity_description TEXT,
    hours DECIMAL(5,2),
    hourly_rate DECIMAL(10,2),
    date_worked DATE,
    billable BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id),
    FOREIGN KEY (lawyer_id) REFERENCES lawyers(lawyer_id)
);

-- Invoices
CREATE TABLE invoices (
    invoice_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    invoice_number VARCHAR(50) UNIQUE,
    amount DECIMAL(10,2),
    status ENUM('Draft', 'Sent', 'Paid', 'Overdue', 'Cancelled'),
    issue_date DATE,
    due_date DATE,
    paid_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id)
);

-- Documents
CREATE TABLE documents (
    doc_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    uploaded_by INT,
    file_name VARCHAR(255),
    file_path VARCHAR(500),
    file_type VARCHAR(50),
    file_size INT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id),
    FOREIGN KEY (uploaded_by) REFERENCES users(user_id)
);

-- Messages between client and lawyer
CREATE TABLE messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    sender_id INT,
    recipient_id INT,
    subject VARCHAR(200),
    body TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id),
    FOREIGN KEY (sender_id) REFERENCES users(user_id),
    FOREIGN KEY (recipient_id) REFERENCES users(user_id)
);

-- Insert default admin (password: admin)
-- Password is hashed with SHA-256 and salt using PasswordUtils

INSERT INTO users (username, password, email, first_name, last_name, role)
VALUES ('admin', 'o9EPQypvEKbt1uNP62R/04k6iNWvNMfPl/ecfPho3ug=:pR1DSPU6V4YbQqL2', 'admin@lawfirm.com', 'System', 'Administrator', 'admin');

INSERT INTO admin_config (admin_username, admin_password)
VALUES ('admin', 'admin');

-- Indexes for performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_cases_client ON cases(client_id);
CREATE INDEX idx_cases_lawyer ON cases(primary_lawyer_id);
CREATE INDEX idx_cases_status ON cases(status);
CREATE INDEX idx_events_date ON case_events(event_date);