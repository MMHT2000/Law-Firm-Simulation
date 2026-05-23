import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class LawyerInfoWindow extends JFrame {
    private JTable lawyerInfoTable;
    JLabel image;
    private DefaultTableModel tableModel;
    ImageIcon bg;
    private JPanel panel;
    private UserDAO userDAO;

    public LawyerInfoWindow() {
        super("Lawyer Information");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.userDAO = new UserDAO();

        panel = new JPanel();
        panel.setLayout(null);
        this.setLocationRelativeTo(null);

        image = new JLabel();
        bg = new ImageIcon("images\\bg4.jpg");
        image.setIcon(bg);
        image.setBounds(0, 0, 1280, 720);
        panel.add(image);

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

        JScrollPane scrollPane = new JScrollPane(lawyerInfoTable);
        scrollPane.setBounds(140, 50, 1000, 500);
        panel.add(scrollPane);
        panel.add(image);

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

}