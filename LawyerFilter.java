import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class LawyerFilter extends JFrame implements ActionListener {
    private JTable lawyerTable;
    private DefaultTableModel tableModel;
    private JButton backBtn;
    private dashBoard dashboard;
    private String budgetRange;
    private ImageIcon bg;
    private UserDAO userDAO;
    private JLabel summaryLabel;

    public LawyerFilter(dashBoard dashboard, String budgetRange) {
        super("Lawyers in Budget Range");
        this.dashboard = dashboard;
        this.budgetRange = budgetRange;
        this.userDAO = new UserDAO();
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initialize();
    }

    private void initialize() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Email");
        columnNames.add("Specialties");
        columnNames.add("Experience");
        columnNames.add("Hourly Rate");
        columnNames.add("Active Cases");

        Vector<Vector<String>> data = readLawyersByBudget();

        tableModel = new DefaultTableModel(data, columnNames);
        lawyerTable = new JTable(tableModel);
        lawyerTable.setPreferredScrollableViewportSize(new Dimension(1000, 500));

        JScrollPane scrollPane = new JScrollPane(lawyerTable);
        scrollPane.setBounds(100, 150, 1080, 390);

        JPanel panel = UITheme.backgroundPanel("Images/bg4.jpg");
        JPanel header = UITheme.surfacePanel(100, 42, 1080, 82);
        panel.add(header);

        summaryLabel = UITheme.title("Lawyers for " + budgetRange, 24, 18, 900, 38);
        header.add(summaryLabel);

        backBtn = UITheme.primaryButton("Back to Dashboard", 490, 570, 300, 36);
        backBtn.addActionListener(this);
        panel.add(backBtn);

        panel.add(scrollPane);

        this.add(panel);
    }

    private Vector<Vector<String>> readLawyersByBudget() {
        Vector<Vector<String>> lawyersData = new Vector<>();
        List<LawyerProfile> lawyers = userDAO.getLawyersForBudget(budgetRange);

        for (LawyerProfile lawyer : lawyers) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(lawyer.getUserId()));
            row.add(lawyer.getName());
            row.add(lawyer.getEmail() != null ? lawyer.getEmail() : "");
            row.add(lawyer.getSpecialties() != null ? lawyer.getSpecialties() : "General Practice");
            row.add(lawyer.getYearsExperience() + " years");
            row.add(lawyer.getHourlyRate() > 0 ? "$" + String.format("%.2f", lawyer.getHourlyRate()) : "Not set");
            row.add(String.valueOf(lawyer.getActiveCases()));
            lawyersData.add(row);
        }

        return lawyersData;
    }

    public void actionPerformed(ActionEvent ae) {
        dashboard.setVisible(true);
        this.setVisible(false);
    }
}
