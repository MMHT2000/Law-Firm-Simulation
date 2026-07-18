import java.sql.*;

public class DemoDataSeeder {
    private static final String DEFAULT_PASSWORD = "password";

    public int seedSuitsInspiredDemo() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            return 0;
        }

        int count = 0;
        try {
            count += upsertUser(conn, "harvey", "Harvey", "Specter", "harvey@pearsonspecter.example", "lawyer");
            count += upsertUser(conn, "mike", "Mike", "Ross", "mike@pearsonspecter.example", "lawyer");
            count += upsertUser(conn, "jessica", "Jessica", "Pearson", "jessica@pearsonspecter.example", "lawyer");
            count += upsertUser(conn, "louis", "Louis", "Litt", "louis@pearsonspecter.example", "lawyer");
            count += upsertUser(conn, "rachel", "Rachel", "Zane", "rachel@pearsonspecter.example", "lawyer");
            count += upsertUser(conn, "katrina", "Katrina", "Bennett", "katrina@pearsonspecter.example", "lawyer");
            count += upsertUser(conn, "donna", "Donna", "Paulsen", "donna@pearsonspecter.example", "staff");

            count += addLawyer(conn, "harvey", "NY-0001-HS", "Mergers, hostile takeovers, settlement strategy", 14, 950, 8, 142, 4);
            count += addLawyer(conn, "mike", "NY-0002-MR", "Class actions, pro bono, litigation research", 7, 475, 6, 68, 6);
            count += addLawyer(conn, "jessica", "NY-0003-JP", "Managing partner, corporate governance, crisis defense", 22, 1100, 10, 210, 8);
            count += addLawyer(conn, "louis", "NY-0004-LL", "Finance, tax, regulatory disputes", 16, 725, 7, 119, 12);
            count += addLawyer(conn, "rachel", "NY-0005-RZ", "Employment, contracts, client advocacy", 5, 350, 5, 39, 3);
            count += addLawyer(conn, "katrina", "NY-0006-KB", "Securities, compliance, trial preparation", 9, 525, 4, 76, 5);

            count += addClient(conn, "Ava Hessington", "hessington@energy.example", "Hessington Oil", 250000);
            count += addClient(conn, "Gillis Industries", "board@gillis.example", "Gillis Industries", 150000);
            count += addClient(conn, "Liberty Rail", "legal@libertyrail.example", "Liberty Rail", 90000);
            count += addClient(conn, "Folsom Foods", "generalcounsel@folsom.example", "Folsom Foods", 125000);
            count += addClient(conn, "McKernon Motors", "ceo@mckernon.example", "McKernon Motors", 175000);
            count += addClient(conn, "Coastal Motors", "claims@coastal.example", "Coastal Motors", 80000);

            count += addCase(conn, "PSL-001", "Ava Hessington Energy Merger Defense", "Corporate", "High-stakes energy acquisition and executive liability defense.", "Ava Hessington", "jessica", "Supreme Court of New York", "New York", "Active", "Retainer", 1100, "Cameron Dennis");
            count += addCase(conn, "PSL-002", "Gillis Industries Hostile Takeover", "Corporate", "Board defense and takeover strategy for a vulnerable manufacturing group.", "Gillis Industries", "harvey", "Delaware Chancery Court", "Delaware", "Active", "Hourly", 950, "Logan Sanders");
            count += addCase(conn, "PSL-003", "Liberty Rail Whistleblower Matter", "Civil", "Employment retaliation and compliance dispute with major discovery exposure.", "Liberty Rail", "mike", "US District Court", "New York", "Pending", "Hourly", 475, "Maria Monroe");
            count += addCase(conn, "PSL-004", "Folsom Foods Class Action", "Civil", "Large employment class action with settlement and public-relations risk.", "Folsom Foods", "rachel", "US District Court", "New York", "Active", "Contingency", 350, "Employees Coalition");
            count += addCase(conn, "PSL-005", "McKernon Motors Acquisition", "Corporate", "Legacy client acquisition defense and founder control dispute.", "McKernon Motors", "louis", "Supreme Court of New York", "New York", "On Hold", "Flat", 725, "Private Equity Group");
            count += addCase(conn, "PSL-006", "Coastal Motors Product Liability", "Civil", "Product defect claims and coordinated settlement strategy.", "Coastal Motors", "katrina", "US District Court", "New Jersey", "Appeal", "Hourly", 525, "Plaintiff Steering Committee");

            count += seedOperations(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int upsertUser(Connection conn, String username, String first, String last, String email, String role) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, first_name, last_name, role) VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE email = VALUES(email), first_name = VALUES(first_name), last_name = VALUES(last_name), role = VALUES(role)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, PasswordUtils.hashPassword(DEFAULT_PASSWORD));
            stmt.setString(3, email);
            stmt.setString(4, first);
            stmt.setString(5, last);
            stmt.setString(6, role);
            return stmt.executeUpdate();
        }
    }

    private int addLawyer(Connection conn, String username, String bar, String specialties, int years, double rate, int active, int won, int lost) throws SQLException {
        int userId = idFor(conn, "SELECT user_id FROM users WHERE username = ?", username);
        if (userId == 0) {
            return 0;
        }
        String sql = "INSERT INTO lawyers (user_id, bar_number, specialties, years_experience, hourly_rate, active_cases, cases_won, cases_lost) " +
                "SELECT ?, ?, ?, ?, ?, ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM lawyers WHERE user_id = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, bar);
            stmt.setString(3, specialties);
            stmt.setInt(4, years);
            stmt.setDouble(5, rate);
            stmt.setInt(6, active);
            stmt.setInt(7, won);
            stmt.setInt(8, lost);
            stmt.setInt(9, userId);
            return stmt.executeUpdate();
        }
    }

    private int addClient(Connection conn, String name, String email, String company, double retainer) throws SQLException {
        String username = company.toLowerCase().replaceAll("[^a-z0-9]", "");
        String[] parts = name.split(" ", 2);
        upsertUser(conn, username, parts[0], parts.length > 1 ? parts[1] : "", email, "client");
        int userId = idFor(conn, "SELECT user_id FROM users WHERE username = ?", username);
        String sql = "INSERT INTO clients (user_id, company_name, contact_person, billing_address, retainer_balance) " +
                "SELECT ?, ?, ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM clients WHERE user_id = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, company);
            stmt.setString(3, name);
            stmt.setString(4, "601 E 54th St, New York, NY");
            stmt.setDouble(5, retainer);
            stmt.setInt(6, userId);
            return stmt.executeUpdate();
        }
    }

    private int addCase(Connection conn, String number, String name, String type, String description, String clientName, String lawyerUsername, String court, String jurisdiction, String status, String feeType, double rate, String opponent) throws SQLException {
        int clientId = idFor(conn, "SELECT client_id FROM clients WHERE contact_person = ?", clientName);
        int lawyerId = idFor(conn, "SELECT l.lawyer_id FROM lawyers l JOIN users u ON l.user_id = u.user_id WHERE u.username = ?", lawyerUsername);
        String sql = "INSERT INTO cases (case_number, case_name, case_type, description, client_id, primary_lawyer_id, court_name, jurisdiction, filed_date, status, fee_type, hourly_rate, opposing_party) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE case_name = VALUES(case_name), status = VALUES(status), primary_lawyer_id = VALUES(primary_lawyer_id), hourly_rate = VALUES(hourly_rate)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, number);
            stmt.setString(2, name);
            stmt.setString(3, type);
            stmt.setString(4, description);
            stmt.setInt(5, clientId);
            stmt.setInt(6, lawyerId);
            stmt.setString(7, court);
            stmt.setString(8, jurisdiction);
            stmt.setString(9, status);
            stmt.setString(10, feeType);
            stmt.setDouble(11, rate);
            stmt.setString(12, opponent);
            return stmt.executeUpdate();
        }
    }

    private int seedOperations(Connection conn) throws SQLException {
        int count = 0;
        count += addEventIfMissing(conn, "PSL-001", "Hearing", "Emergency injunction conference", "2026-07-22 09:30:00", "New York Supreme Court");
        count += addEventIfMissing(conn, "PSL-002", "Deadline", "Tender-offer response deadline", "2026-07-29 17:00:00", "Boardroom");
        count += addEventIfMissing(conn, "PSL-003", "Meeting", "Whistleblower deposition prep", "2026-08-03 10:00:00", "Conference Room A");
        count += addInvoiceIfMissing(conn, "PSL-001", "PSL-INV-1001", 125000, "Sent", "2026-07-18", "2026-08-17");
        count += addInvoiceIfMissing(conn, "PSL-002", "PSL-INV-1002", 87500, "Draft", "2026-07-18", "2026-08-17");
        count += addTimeIfMissing(conn, "PSL-002", "harvey", "Negotiated standstill terms with opposing counsel.", 3.5, 950, "2026-07-18");
        count += addTimeIfMissing(conn, "PSL-003", "mike", "Reviewed discovery production and drafted witness outline.", 5.2, 475, "2026-07-18");
        count += addDocumentIfMissing(conn, "PSL-001", "jessica", "Hessington emergency injunction memo.pdf", "Documents/hessington-injunction-memo.pdf", "PDF", 482000);
        count += addDocumentIfMissing(conn, "PSL-002", "harvey", "Gillis takeover defense deck.pptx", "Documents/gillis-defense-deck.pptx", "PPTX", 950000);
        count += addMessageIfMissing(conn, "PSL-003", "mike", "donna", "Deposition prep", "Please schedule the witness room and circulate the revised prep binder.");
        return count;
    }

    private int addEventIfMissing(Connection conn, String caseNumber, String type, String title, String date, String location) throws SQLException {
        int caseId = idFor(conn, "SELECT case_id FROM cases WHERE case_number = ?", caseNumber);
        if (exists(conn, "SELECT COUNT(*) FROM case_events WHERE case_id = ? AND title = ?", caseId, title)) {
            return 0;
        }
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO case_events (case_id, event_type, title, event_date, location) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, caseId);
            stmt.setString(2, type);
            stmt.setString(3, title);
            stmt.setString(4, date);
            stmt.setString(5, location);
            return stmt.executeUpdate();
        }
    }

    private int addInvoiceIfMissing(Connection conn, String caseNumber, String invoiceNumber, double amount, String status, String issue, String due) throws SQLException {
        int caseId = idFor(conn, "SELECT case_id FROM cases WHERE case_number = ?", caseNumber);
        if (exists(conn, "SELECT COUNT(*) FROM invoices WHERE invoice_number = ?", invoiceNumber)) {
            return 0;
        }
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO invoices (case_id, invoice_number, amount, status, issue_date, due_date) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, caseId);
            stmt.setString(2, invoiceNumber);
            stmt.setDouble(3, amount);
            stmt.setString(4, status);
            stmt.setString(5, issue);
            stmt.setString(6, due);
            return stmt.executeUpdate();
        }
    }

    private int addTimeIfMissing(Connection conn, String caseNumber, String lawyerUsername, String activity, double hours, double rate, String date) throws SQLException {
        int caseId = idFor(conn, "SELECT case_id FROM cases WHERE case_number = ?", caseNumber);
        int lawyerId = idFor(conn, "SELECT l.lawyer_id FROM lawyers l JOIN users u ON l.user_id = u.user_id WHERE u.username = ?", lawyerUsername);
        if (exists(conn, "SELECT COUNT(*) FROM time_entries WHERE case_id = ? AND lawyer_id = ? AND activity_description = ?", caseId, lawyerId, activity)) {
            return 0;
        }
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO time_entries (case_id, lawyer_id, activity_description, hours, hourly_rate, date_worked, billable) VALUES (?, ?, ?, ?, ?, ?, TRUE)")) {
            stmt.setInt(1, caseId);
            stmt.setInt(2, lawyerId);
            stmt.setString(3, activity);
            stmt.setDouble(4, hours);
            stmt.setDouble(5, rate);
            stmt.setString(6, date);
            return stmt.executeUpdate();
        }
    }

    private int addDocumentIfMissing(Connection conn, String caseNumber, String username, String name, String path, String type, int size) throws SQLException {
        int caseId = idFor(conn, "SELECT case_id FROM cases WHERE case_number = ?", caseNumber);
        int userId = idFor(conn, "SELECT user_id FROM users WHERE username = ?", username);
        if (exists(conn, "SELECT COUNT(*) FROM documents WHERE case_id = ? AND file_name = ?", caseId, name)) {
            return 0;
        }
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO documents (case_id, uploaded_by, file_name, file_path, file_type, file_size) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, caseId);
            stmt.setInt(2, userId);
            stmt.setString(3, name);
            stmt.setString(4, path);
            stmt.setString(5, type);
            stmt.setInt(6, size);
            return stmt.executeUpdate();
        }
    }

    private int addMessageIfMissing(Connection conn, String caseNumber, String senderUsername, String recipientUsername, String subject, String body) throws SQLException {
        int caseId = idFor(conn, "SELECT case_id FROM cases WHERE case_number = ?", caseNumber);
        int sender = idFor(conn, "SELECT user_id FROM users WHERE username = ?", senderUsername);
        int recipient = idFor(conn, "SELECT user_id FROM users WHERE username = ?", recipientUsername);
        if (exists(conn, "SELECT COUNT(*) FROM messages WHERE case_id = ? AND subject = ?", caseId, subject)) {
            return 0;
        }
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO messages (case_id, sender_id, recipient_id, subject, body, is_read) VALUES (?, ?, ?, ?, ?, FALSE)")) {
            stmt.setInt(1, caseId);
            stmt.setInt(2, sender);
            stmt.setInt(3, recipient);
            stmt.setString(4, subject);
            stmt.setString(5, body);
            return stmt.executeUpdate();
        }
    }

    private int idFor(Connection conn, String sql, String value) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private boolean exists(Connection conn, String sql, Object... values) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
