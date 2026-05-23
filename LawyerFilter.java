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
        columnNames.add("Username");

        Vector<Vector<String>> data = readLawyersByBudget();

        tableModel = new DefaultTableModel(data, columnNames);
        lawyerTable = new JTable(tableModel);
        lawyerTable.setPreferredScrollableViewportSize(new Dimension(1000, 500));

        JScrollPane scrollPane = new JScrollPane(lawyerTable);
        scrollPane.setBounds(140, 50, 1000, 500);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        backBtn = new JButton("Back to Dashboard");
        backBtn.setBounds(540, 570, 200, 30);
        backBtn.setBackground(new Color(0x2596BE));
        backBtn.addActionListener(this);
        panel.add(backBtn);

        panel.add(scrollPane);

        JLabel background = new JLabel();
        bg = new ImageIcon("images\\gf8.jpg");
        background.setIcon(bg);
        background.setBounds(0, 0, 1280, 720);
        panel.add(background);

        this.add(panel);
    }

    private Vector<Vector<String>> readLawyersByBudget() {
        Vector<Vector<String>> lawyersData = new Vector<>();
        List<User> lawyers = userDAO.getUsersByRole("lawyer");

        for (User u : lawyers) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(u.getUserId()));
            row.add(u.getFirstName() + " " + u.getLastName());
            row.add(u.getEmail() != null ? u.getEmail() : "");
            row.add(u.getUsername());
            lawyersData.add(row);
        }

        return lawyersData;
    }

    public void actionPerformed(ActionEvent ae) {
        dashboard.setVisible(true);
        this.setVisible(false);
    }
}