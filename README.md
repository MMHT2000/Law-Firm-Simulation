# Law Firm Simulation

A Java Swing desktop law-firm management simulation with a Suits-inspired corporate-law theme. The app supports admin, lawyer, and client portals backed by a MySQL database through XAMPP.

## Current Features

- Role-based portals for Admin, Lawyer, and Client.
- Client and lawyer account creation/login.
- Admin dashboard for cases, lawyers, demo data, and firm operations.
- Client dashboard with profile management, case view, and budget-based lawyer matching.
- Lawyer dashboard with assigned cases and access to firm operations.
- Operations Hub with tabs for:
  - Overview
  - Matters
  - Intake
  - Calendar/events
  - Billing
  - Time entries
  - Documents
  - Messages
  - Reports
- Suits-inspired demo data loader with lawyers, clients, corporate matters, events, invoices, documents, and messages.
- Expanded database schema for conflict checks, related contacts, tasks, matter notes, expenses, payments, trust ledger, and audit logs.

## Requirements

- JDK installed and available from the terminal.
- XAMPP with MySQL running on port `3307`.
- `mysql-connector-j-8.4.0.jar` in the project root.

## Database Setup

Start XAMPP MySQL on port `3307`, then load the schema:

```bat
C:\xampp\mysql\bin\mysql.exe -h 127.0.0.1 -P 3307 -u root --execute="source F:/Law-Firm-Simulation/database.sql"
```

The app connects to:

```text
jdbc:mysql://localhost:3307/lawfirm_db
```

## Build

From the project root:

```bat
javac *.java
```

## Run

Use the XAMPP run script:

```bat
run-xampp.bat
```

This launches Java with the MySQL connector on the classpath:

```bat
java -cp ".;mysql-connector-j-8.4.0.jar" Start
```

## Default Login

Admin:

```text
Username: admin
Password: admin
```

After logging in as admin, click **Load Suits Demo** to seed demo lawyers, clients, matters, events, invoices, time entries, documents, and messages.

Demo users created by the seeder use:

```text
Password: password
```

Example demo lawyer usernames:

- `harvey`
- `mike`
- `jessica`
- `louis`
- `rachel`
- `katrina`

## Important Files

- `Start.java`: app entry point.
- `DatabaseConnection.java`: MySQL connection config.
- `database.sql`: schema and default admin data.
- `run-xampp.bat`: recommended launcher.
- `UITheme.java`: shared visual styling.
- `DemoDataSeeder.java`: Suits-inspired demo data.
- `OperationsHub.java`: firm-wide operations screen.
- `UserDAO.java`, `CaseDAO.java`, `FirmOperationsDAO.java`: database access.

## Notes

- The project currently uses plain Java Swing with no build tool.
- Compiled `.class` files are present in the repository because the original project tracked them.
- If login or registration fails, confirm XAMPP MySQL is running on port `3307` and launch with `run-xampp.bat`.
