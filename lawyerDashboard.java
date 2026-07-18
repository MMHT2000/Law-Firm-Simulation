import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class lawyerDashboard extends JFrame implements ActionListener, MouseListener {
    JPanel panel;
    JLabel wlabel, nameLabel, emailLabel, casesLabel;
    JButton logOutButton, myCasesButton, operationsHubButton;
    Font myFont, Font1;
    ImageIcon icon;
    User user;
    UserDAO userDAO;
    CaseDAO caseDAO;
    LawyerLogin ll;
    JTable casesTable;
    DefaultTableModel tableModel;

    public lawyerDashboard(User user, LawyerLogin ll) {
        super("Lawyer Dashboard");
        this.setSize(1280, 720);
        icon = new ImageIcon("images/student.jpg");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.ll = ll;
        this.user = user;
        this.userDAO = new UserDAO();
        this.caseDAO = new CaseDAO();

        myFont = new Font("Cambria", Font.PLAIN, 17);
        Font1 = new Font("Times New Roman", Font.BOLD, 24);

        panel = new JPanel();
        panel.setLayout(null);

        String displayName = user != null ? user.getFirstName() : "Lawyer";

        wlabel = new JLabel("Welcome back, " + displayName + "!");
        wlabel.setBounds(550, 20, 300, 30);
        wlabel.setFont(Font1);
        panel.add(wlabel);

        nameLabel = new JLabel("Name: " + user.getFirstName() + " " + user.getLastName());
        nameLabel.setBounds(480, 60, 200, 30);
        nameLabel.setFont(myFont);
        panel.add(nameLabel);

        emailLabel = new JLabel("Email: " + user.getEmail());
        emailLabel.setBounds(480, 90, 200, 30);
        emailLabel.setFont(myFont);
        panel.add(emailLabel);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Case ID");
        columnNames.add("Case Name");
        columnNames.add("Client");
        columnNames.add("Status");

        Vector<Vector<String>> data = getAssignedCases();
        tableModel = new DefaultTableModel(data, columnNames);
        casesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(casesTable);
        scrollPane.setBounds(480, 130, 300, 150);
        panel.add(scrollPane);

        casesLabel = new JLabel("Your Assigned Cases:");
        casesLabel.setBounds(480, 110, 150, 20);
        panel.add(casesLabel);

        myCasesButton = new JButton("View My Cases");
        myCasesButton.setBounds(540, 300, 160, 30);
        myCasesButton.setFont(myFont);
        myCasesButton.setBackground(new Color(0x2596BE));
        myCasesButton.addActionListener(this);
        panel.add(myCasesButton);

        operationsHubButton = new JButton("Operations Hub");
        operationsHubButton.setBounds(540, 350, 160, 30);
        operationsHubButton.setFont(myFont);
        operationsHubButton.setBackground(new Color(0x2596BE));
        operationsHubButton.addActionListener(this);
        panel.add(operationsHubButton);

        logOutButton = new JButton("Log out");
        logOutButton.setBounds(540, 400, 160, 30);
        logOutButton.setFont(myFont);
        logOutButton.setBackground(new Color(0x2596BE));
        logOutButton.addActionListener(this);
        panel.add(logOutButton);

        logOutButton.addActionListener(this);
        myCasesButton.addActionListener(this);
        operationsHubButton.addActionListener(this);

        logOutButton.setFocusable(false);
        myCasesButton.setFocusable(false);
        operationsHubButton.setFocusable(false);

        JLabel background = new JLabel();
        ImageIcon bg = new ImageIcon("images\\gf8.jpg");
        background.setIcon(bg);
        background.setBounds(0, 0, 1280, 720);
        panel.add(background);

        this.add(panel);
    }

    private Vector<Vector<String>> getAssignedCases() {
        Vector<Vector<String>> assignedCases = new Vector<>();
        java.util.List<Case> cases = caseDAO.getCasesByLawyer(user.getUserId());

        for (Case c : cases) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(c.getCaseId()));
            row.add(c.getCaseName() != null ? c.getCaseName() : "");
            row.add(c.getOpposingParty() != null ? c.getOpposingParty() : "");
            row.add(c.getStatus() != null ? c.getStatus() : "Active");
            assignedCases.add(row);
        }

        return assignedCases;
    }

    public void mouseClicked(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (logOutButton.getText().equals(command)) {
            ll.setVisible(true);
            this.setVisible(false);
        } else if (myCasesButton.getText().equals(command)) {
            lawyerCaseView lcv = new lawyerCaseView(user, this);
            lcv.setVisible(true);
            this.setVisible(false);
        } else if (operationsHubButton.getText().equals(command)) {
            OperationsHub hub = new OperationsHub(this, user);
            hub.setVisible(true);
            this.setVisible(false);
        }
    }
}
