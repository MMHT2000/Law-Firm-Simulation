import java.sql.*;
import java.util.Vector;

public class FirmOperationsDAO {
    public Vector<Vector<String>> getMatterRows() {
        return queryRows(
                "SELECT case_number, case_name, case_type, status, court_name, jurisdiction " +
                        "FROM cases ORDER BY updated_at DESC",
                6
        );
    }

    public Vector<Vector<String>> getIntakeRows() {
        return queryRows(
                "SELECT u.user_id, CONCAT(COALESCE(u.first_name, ''), ' ', COALESCE(u.last_name, '')), " +
                        "u.email, COALESCE(c.company_name, ''), COALESCE(c.retainer_balance, 0) " +
                        "FROM users u LEFT JOIN clients c ON u.user_id = c.user_id " +
                        "WHERE u.role = 'client' ORDER BY u.created_at DESC",
                5
        );
    }

    public Vector<Vector<String>> getCalendarRows() {
        return queryRows(
                "SELECT COALESCE(c.case_number, ''), e.event_type, e.title, e.event_date, e.location " +
                        "FROM case_events e LEFT JOIN cases c ON e.case_id = c.case_id " +
                        "ORDER BY e.event_date ASC",
                5
        );
    }

    public Vector<Vector<String>> getBillingRows() {
        return queryRows(
                "SELECT COALESCE(c.case_number, ''), COALESCE(i.invoice_number, ''), " +
                        "COALESCE(i.amount, 0), COALESCE(i.status, 'Draft'), COALESCE(i.issue_date, ''), COALESCE(i.due_date, '') " +
                        "FROM invoices i LEFT JOIN cases c ON i.case_id = c.case_id " +
                        "ORDER BY i.due_date DESC",
                6
        );
    }

    public Vector<Vector<String>> getTimeRows() {
        return queryRows(
                "SELECT COALESCE(c.case_number, ''), COALESCE(u.username, ''), t.activity_description, " +
                        "t.hours, t.hourly_rate, t.date_worked, IF(t.billable, 'Billable', 'Non-billable') " +
                        "FROM time_entries t " +
                        "LEFT JOIN cases c ON t.case_id = c.case_id " +
                        "LEFT JOIN lawyers l ON t.lawyer_id = l.lawyer_id " +
                        "LEFT JOIN users u ON l.user_id = u.user_id " +
                        "ORDER BY t.date_worked DESC",
                7
        );
    }

    public Vector<Vector<String>> getDocumentRows() {
        return queryRows(
                "SELECT COALESCE(c.case_number, ''), d.file_name, d.file_type, d.file_size, COALESCE(u.username, ''), d.uploaded_at " +
                        "FROM documents d " +
                        "LEFT JOIN cases c ON d.case_id = c.case_id " +
                        "LEFT JOIN users u ON d.uploaded_by = u.user_id " +
                        "ORDER BY d.uploaded_at DESC",
                6
        );
    }

    public Vector<Vector<String>> getMessageRows() {
        return queryRows(
                "SELECT COALESCE(c.case_number, ''), COALESCE(sender.username, ''), COALESCE(recipient.username, ''), " +
                        "m.subject, IF(m.is_read, 'Read', 'Unread'), m.sent_at " +
                        "FROM messages m " +
                        "LEFT JOIN cases c ON m.case_id = c.case_id " +
                        "LEFT JOIN users sender ON m.sender_id = sender.user_id " +
                        "LEFT JOIN users recipient ON m.recipient_id = recipient.user_id " +
                        "ORDER BY m.sent_at DESC",
                6
        );
    }

    public Vector<Vector<String>> getReportRows() {
        Vector<Vector<String>> rows = new Vector<>();
        addMetric(rows, "Open matters", count("SELECT COUNT(*) FROM cases WHERE status IN ('Active', 'Pending', 'On Hold', 'Appeal')"));
        addMetric(rows, "Closed matters", count("SELECT COUNT(*) FROM cases WHERE status = 'Closed'"));
        addMetric(rows, "Upcoming events", count("SELECT COUNT(*) FROM case_events WHERE event_date >= NOW()"));
        addMetric(rows, "Unpaid invoices", count("SELECT COUNT(*) FROM invoices WHERE status IN ('Draft', 'Sent', 'Overdue')"));
        addMetric(rows, "Stored documents", count("SELECT COUNT(*) FROM documents"));
        addMetric(rows, "Unread messages", count("SELECT COUNT(*) FROM messages WHERE is_read = FALSE"));
        addMetric(rows, "Billable hours", scalar("SELECT COALESCE(SUM(hours), 0) FROM time_entries WHERE billable = TRUE"));
        addMetric(rows, "Invoice value", "$" + scalar("SELECT COALESCE(SUM(amount), 0) FROM invoices"));
        return rows;
    }

    public boolean addCaseEvent(int caseId, String eventType, String title, String description, String eventDate, String location) {
        return update(
                "INSERT INTO case_events (case_id, event_type, title, description, event_date, location) VALUES (?, ?, ?, ?, ?, ?)",
                caseId, eventType, title, description, eventDate, location
        );
    }

    public boolean addTimeEntry(int caseId, int lawyerId, String activity, double hours, double rate, String dateWorked, boolean billable) {
        return update(
                "INSERT INTO time_entries (case_id, lawyer_id, activity_description, hours, hourly_rate, date_worked, billable) VALUES (?, ?, ?, ?, ?, ?, ?)",
                caseId, lawyerId, activity, hours, rate, dateWorked, billable
        );
    }

    public boolean addInvoice(int caseId, String invoiceNumber, double amount, String status, String issueDate, String dueDate) {
        return update(
                "INSERT INTO invoices (case_id, invoice_number, amount, status, issue_date, due_date) VALUES (?, ?, ?, ?, ?, ?)",
                caseId, invoiceNumber, amount, status, issueDate, dueDate
        );
    }

    public boolean addDocumentRecord(int caseId, int uploadedBy, String fileName, String filePath, String fileType, int fileSize) {
        return update(
                "INSERT INTO documents (case_id, uploaded_by, file_name, file_path, file_type, file_size) VALUES (?, ?, ?, ?, ?, ?)",
                caseId, uploadedBy, fileName, filePath, fileType, fileSize
        );
    }

    public boolean addMessage(int caseId, int senderId, int recipientId, String subject, String body) {
        return update(
                "INSERT INTO messages (case_id, sender_id, recipient_id, subject, body, is_read) VALUES (?, ?, ?, ?, ?, FALSE)",
                caseId, senderId, recipientId, subject, body
        );
    }

    private Vector<Vector<String>> queryRows(String sql, int columnCount) {
        Vector<Vector<String>> rows = new Vector<>();
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            addUnavailableRow(rows, columnCount);
            return rows;
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(String.valueOf(rs.getObject(i) != null ? rs.getObject(i) : ""));
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            addErrorRow(rows, columnCount, e.getMessage());
        }
        return rows;
    }

    private boolean update(String sql, Object... values) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int count(String sql) {
        String value = scalar(sql);
        try {
            return (int) Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String scalar(String sql) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            return "0";
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return String.valueOf(rs.getObject(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    private void addMetric(Vector<Vector<String>> rows, String metric, Object value) {
        Vector<String> row = new Vector<>();
        row.add(metric);
        row.add(String.valueOf(value));
        rows.add(row);
    }

    private void addUnavailableRow(Vector<Vector<String>> rows, int columnCount) {
        Vector<String> row = new Vector<>();
        row.add("Database unavailable");
        while (row.size() < columnCount) {
            row.add("");
        }
        rows.add(row);
    }

    private void addErrorRow(Vector<Vector<String>> rows, int columnCount, String message) {
        Vector<String> row = new Vector<>();
        row.add("Query error");
        row.add(message != null ? message : "");
        while (row.size() < columnCount) {
            row.add("");
        }
        rows.add(row);
    }
}
