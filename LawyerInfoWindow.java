import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class LawyerInfoWindow extends JFrame implements ActionListener {
    private JTable lawyerInfoTable;
    private DefaultTableModel tableModel;
    private JPanel panel;
    private UserDAO userDAO;
    private adminDashboard adminDashboard;
    private JButton backBtn;

    public LawyerInfoWindow() {
        this(null);
    }

    public LawyerInfoWindow(adminDashboard adminDashboard) {
        super("Lawyer Information");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.userDAO = new UserDAO();
        this.adminDashboard = adminDashboard;

        panel = UITheme.backgroundPanel("Images/bg4.jpg");
        initialize();
    }

    private void initialize() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Name");
        columnNames.add("Email");
        columnNames.add("Username");

        Vector<Vector<String>> data = readAllLawyers();

        tableModel = new DefaultTableModel(data, columnNames);
        lawyerInfoTable = new JTable(tableModel);
        lawyerInfoTable.setRowHeight(26);

        JScrollPane scrollPane = new JScrollPane(lawyerInfoTable);
        scrollPane.setBounds(140, 70, 1000, 470);
        panel.add(scrollPane);

        if (adminDashboard != null) {
            backBtn = UITheme.primaryButton("Back to Dashboard", 540, 570, 220, 36);
            backBtn.addActionListener(this);
            panel.add(backBtn);
        }

        this.add(panel);
    }

    private Vector<Vector<String>> readAllLawyers() {
        Vector<Vector<String>> allLawyers = new Vector<>();
        java.util.List<User> users = userDAO.getAllUsers();

        for (User u : users) {
            if ("lawyer".equals(u.getRole())) {
                Vector<String> lawyer = new Vector<>();
                lawyer.add(u.getFirstName() + " " + u.getLastName());
                lawyer.add(u.getEmail() != null ? u.getEmail() : "");
                lawyer.add(u.getUsername());
                allLawyers.add(lawyer);
            }
        }

        return allLawyers;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn && adminDashboard != null) {
            adminDashboard.setVisible(true);
            this.setVisible(false);
        }
    }
}
