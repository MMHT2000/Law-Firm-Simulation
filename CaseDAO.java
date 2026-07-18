import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaseDAO {

    public boolean addCase(Case c) {
        String sql = "INSERT INTO cases (case_number, case_name, case_type, description, client_id, " +
                     "primary_lawyer_id, co_counsel_ids, court_name, jurisdiction, filed_date, status, " +
                     "fee_type, hourly_rate, opposing_party) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getCaseNumber());
            stmt.setString(2, c.getCaseName());
            stmt.setString(3, c.getCaseType());
            stmt.setString(4, c.getDescription());
            stmt.setInt(5, c.getClientId());
            stmt.setInt(6, c.getPrimaryLawyerId());
            stmt.setString(7, c.getCoCounselIds());
            stmt.setString(8, c.getCourtName());
            stmt.setString(9, c.getJurisdiction());
            if (c.getFiledDate() != null) {
                stmt.setDate(10, new java.sql.Date(c.getFiledDate().getTime()));
            } else {
                stmt.setNull(10, Types.DATE);
            }
            stmt.setString(11, c.getStatus());
            stmt.setString(12, c.getFeeType());
            stmt.setDouble(13, c.getHourlyRate());
            stmt.setString(14, c.getOpposingParty());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Case> getAllCases() {
        List<Case> cases = new ArrayList<>();
        String sql = "SELECT * FROM cases ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Case c = new Case();
                c.setCaseId(rs.getInt("case_id"));
                c.setCaseNumber(rs.getString("case_number"));
                c.setCaseName(rs.getString("case_name"));
                c.setCaseType(rs.getString("case_type"));
                c.setDescription(rs.getString("description"));
                c.setClientId(rs.getInt("client_id"));
                c.setPrimaryLawyerId(rs.getInt("primary_lawyer_id"));
                c.setCoCounselIds(rs.getString("co_counsel_ids"));
                c.setCourtName(rs.getString("court_name"));
                c.setJurisdiction(rs.getString("jurisdiction"));
                c.setFiledDate(rs.getDate("filed_date"));
                c.setStatus(rs.getString("status"));
                c.setFeeType(rs.getString("fee_type"));
                c.setHourlyRate(rs.getDouble("hourly_rate"));
                c.setOpposingParty(rs.getString("opposing_party"));
                cases.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cases;
    }

    public List<Case> getCasesByClient(int clientId) {
        List<Case> cases = new ArrayList<>();
        String sql = "SELECT * FROM cases WHERE client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Case c = new Case();
                c.setCaseId(rs.getInt("case_id"));
                c.setCaseNumber(rs.getString("case_number"));
                c.setCaseName(rs.getString("case_name"));
                c.setStatus(rs.getString("status"));
                c.setOpposingParty(rs.getString("opposing_party"));
                cases.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cases;
    }

    public List<Case> getCasesByLawyer(int lawyerId) {
        List<Case> cases = new ArrayList<>();
        String sql = "SELECT c.* FROM cases c " +
                "LEFT JOIN lawyers l ON c.primary_lawyer_id = l.lawyer_id " +
                "WHERE c.primary_lawyer_id = ? OR l.user_id = ? OR FIND_IN_SET(?, c.co_counsel_ids)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lawyerId);
            stmt.setInt(2, lawyerId);
            stmt.setString(3, String.valueOf(lawyerId));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Case c = new Case();
                c.setCaseId(rs.getInt("case_id"));
                c.setCaseNumber(rs.getString("case_number"));
                c.setCaseName(rs.getString("case_name"));
                c.setStatus(rs.getString("status"));
                cases.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cases;
    }

    public Case getCaseById(int caseId) {
        String sql = "SELECT * FROM cases WHERE case_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, caseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Case c = new Case();
                c.setCaseId(rs.getInt("case_id"));
                c.setCaseNumber(rs.getString("case_number"));
                c.setCaseName(rs.getString("case_name"));
                c.setCaseType(rs.getString("case_type"));
                c.setDescription(rs.getString("description"));
                c.setClientId(rs.getInt("client_id"));
                c.setPrimaryLawyerId(rs.getInt("primary_lawyer_id"));
                c.setCoCounselIds(rs.getString("co_counsel_ids"));
                c.setCourtName(rs.getString("court_name"));
                c.setJurisdiction(rs.getString("jurisdiction"));
                c.setFiledDate(rs.getDate("filed_date"));
                c.setStatus(rs.getString("status"));
                c.setFeeType(rs.getString("fee_type"));
                c.setHourlyRate(rs.getDouble("hourly_rate"));
                c.setOpposingParty(rs.getString("opposing_party"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCase(Case c) {
        String sql = "UPDATE cases SET case_name = ?, case_type = ?, description = ?, status = ?, " +
                     "fee_type = ?, hourly_rate = ?, opposing_party = ? WHERE case_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getCaseName());
            stmt.setString(2, c.getCaseType());
            stmt.setString(3, c.getDescription());
            stmt.setString(4, c.getStatus());
            stmt.setString(5, c.getFeeType());
            stmt.setDouble(6, c.getHourlyRate());
            stmt.setString(7, c.getOpposingParty());
            stmt.setInt(8, c.getCaseId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCase(int caseId) {
        String sql = "DELETE FROM cases WHERE case_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, caseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
