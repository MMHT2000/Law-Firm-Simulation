import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CaseInfoWindow extends JFrame implements ActionListener {
    private JTable caseInfoTable;
    private JTextField searchField;
    private JComboBox<String> filterCombo;
    private JButton searchBtn, resetBtn, backBtn;
    private DefaultTableModel tableModel;
    private CaseDAO caseDAO;
    private adminDashboard adminDashboard;

    public CaseInfoWindow() {
        this(null);
    }

    public CaseInfoWindow(adminDashboard adminDashboard) {
        super("Case Information");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.caseDAO = new CaseDAO();
        this.adminDashboard = adminDashboard;
        initialize();
    }

    private void initialize() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Case ID");
        columnNames.add("Case Number");
        columnNames.add("Case Name");
        columnNames.add("Opponent");
        columnNames.add("Status");

        Vector<Vector<String>> data = readAllCases();

        tableModel = new DefaultTableModel(data, columnNames);
        caseInfoTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(caseInfoTable);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchField = new JTextField(15);
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        filterCombo = new JComboBox<>(new String[]{"All", "Active", "Closed", "Pending", "On Hold"});
        searchPanel.add(new JLabel("Status: "));
        searchPanel.add(filterCombo);

        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        searchPanel.add(searchBtn);

        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);
        searchPanel.add(resetBtn);

        this.add(searchPanel, BorderLayout.NORTH);

        // Add back button if we have a dashboard reference
        if (adminDashboard != null) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            backBtn = new JButton("Back to Dashboard");
            backBtn.addActionListener(this);
            buttonPanel.add(backBtn);
            this.add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    private Vector<Vector<String>> readAllCases() {
        Vector<Vector<String>> allCases = new Vector<>();
        java.util.List<Case> cases = caseDAO.getAllCases();

        for (Case c : cases) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(c.getCaseId()));
            row.add(c.getCaseNumber() != null ? c.getCaseNumber() : "");
            row.add(c.getCaseName() != null ? c.getCaseName() : "");
            row.add(c.getOpposingParty() != null ? c.getOpposingParty() : "");
            row.add(c.getStatus() != null ? c.getStatus() : "Active");
            allCases.add(row);
        }

        return allCases;
    }

    private Vector<Vector<String>> readFilteredCases(String statusFilter) {
        Vector<Vector<String>> filteredCases = new Vector<>();
        java.util.List<Case> cases = caseDAO.getAllCases();

        for (Case c : cases) {
            if (statusFilter.equals("All") || (c.getStatus() != null && c.getStatus().equalsIgnoreCase(statusFilter))) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(c.getCaseId()));
                row.add(c.getCaseNumber() != null ? c.getCaseNumber() : "");
                row.add(c.getCaseName() != null ? c.getCaseName() : "");
                row.add(c.getOpposingParty() != null ? c.getOpposingParty() : "");
                row.add(c.getStatus() != null ? c.getStatus() : "Active");
                filteredCases.add(row);
            }
        }

        return filteredCases;
    }

    private void performSearch() {
        String searchTerm = searchField.getText().toLowerCase();
        String statusFilter = (String) filterCombo.getSelectedItem();

        Vector<Vector<String>> data = readFilteredCases(statusFilter);
        tableModel.setRowCount(0);

        for (Vector<String> row : data) {
            boolean matches = searchTerm.isEmpty();
            for (String cell : row) {
                if (cell.toLowerCase().contains(searchTerm)) {
                    matches = true;
                    break;
                }
            }
            if (matches) {
                tableModel.addRow(row);
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {
            if (adminDashboard != null) {
                adminDashboard.setVisible(true);
                this.setVisible(false);
            }
            return;
        }
        if (ae.getSource() == searchBtn) {
            performSearch();
        } else if (ae.getSource() == resetBtn) {
            searchField.setText("");
            filterCombo.setSelectedIndex(0);
            Vector<Vector<String>> data = readAllCases();
            tableModel.setRowCount(0);
            for (Vector<String> row : data) {
                tableModel.addRow(row);
            }
        }
    }
}
