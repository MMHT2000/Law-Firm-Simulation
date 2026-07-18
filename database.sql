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

-- Related parties for conflict checks and richer matter records
CREATE TABLE related_contacts (
    related_contact_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    full_name VARCHAR(150),
    organization VARCHAR(150),
    relationship_type ENUM('Client', 'Opposing Party', 'Opposing Counsel', 'Witness', 'Judge', 'Insurer', 'Expert', 'Other') DEFAULT 'Other',
    email VARCHAR(100),
    phone VARCHAR(30),
    conflict_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE
);

-- Conflict check records before accepting or expanding representation
CREATE TABLE conflict_checks (
    conflict_check_id INT AUTO_INCREMENT PRIMARY KEY,
    requested_by INT,
    client_name VARCHAR(150),
    opposing_party VARCHAR(150),
    matter_summary TEXT,
    result ENUM('Clear', 'Potential Conflict', 'Conflict', 'Needs Review') DEFAULT 'Needs Review',
    reviewed_by INT,
    reviewed_at DATETIME,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (requested_by) REFERENCES users(user_id),
    FOREIGN KEY (reviewed_by) REFERENCES users(user_id)
);

-- Task and workflow management
CREATE TABLE task_templates (
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    case_type VARCHAR(50),
    title VARCHAR(150),
    default_due_days INT,
    default_role ENUM('admin', 'lawyer', 'client', 'staff') DEFAULT 'staff',
    priority ENUM('Low', 'Normal', 'High', 'Urgent') DEFAULT 'Normal'
);

CREATE TABLE tasks (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    assigned_to INT,
    title VARCHAR(150),
    description TEXT,
    due_date DATE,
    priority ENUM('Low', 'Normal', 'High', 'Urgent') DEFAULT 'Normal',
    status ENUM('Open', 'In Progress', 'Blocked', 'Done', 'Cancelled') DEFAULT 'Open',
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at DATETIME,
    FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES users(user_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Matter notes and timeline entries
CREATE TABLE matter_notes (
    note_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    created_by INT,
    note_type ENUM('General', 'Strategy', 'Client Call', 'Court', 'Billing', 'Risk') DEFAULT 'General',
    note_body TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Expenses and payments
CREATE TABLE expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    submitted_by INT,
    description TEXT,
    amount DECIMAL(10,2),
    expense_date DATE,
    billable BOOLEAN DEFAULT TRUE,
    status ENUM('Draft', 'Submitted', 'Approved', 'Rejected', 'Billed') DEFAULT 'Draft',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (case_id) REFERENCES cases(case_id),
    FOREIGN KEY (submitted_by) REFERENCES users(user_id)
);

CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT,
    client_id INT,
    amount DECIMAL(10,2),
    payment_method ENUM('Cash', 'Check', 'Card', 'Bank Transfer', 'Trust Transfer', 'Other') DEFAULT 'Other',
    payment_date DATE,
    reference_number VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

-- Trust/IOLTA-style ledger for retainers and client funds
CREATE TABLE trust_ledger (
    ledger_id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT,
    case_id INT,
    transaction_type ENUM('Deposit', 'Disbursement', 'Invoice Payment', 'Refund', 'Adjustment') NOT NULL,
    amount DECIMAL(10,2),
    transaction_date DATE,
    reference_number VARCHAR(100),
    memo TEXT,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(client_id),
    FOREIGN KEY (case_id) REFERENCES cases(case_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Audit log for sensitive actions
CREATE TABLE audit_logs (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    actor_user_id INT,
    entity_type VARCHAR(50),
    entity_id INT,
    action VARCHAR(80),
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (actor_user_id) REFERENCES users(user_id)
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
CREATE INDEX idx_related_contacts_case ON related_contacts(case_id);
CREATE INDEX idx_conflict_result ON conflict_checks(result);
CREATE INDEX idx_tasks_case_status ON tasks(case_id, status);
CREATE INDEX idx_expenses_case ON expenses(case_id);
CREATE INDEX idx_payments_invoice ON payments(invoice_id);
CREATE INDEX idx_trust_client ON trust_ledger(client_id);
CREATE INDEX idx_audit_entity ON audit_logs(entity_type, entity_id);
