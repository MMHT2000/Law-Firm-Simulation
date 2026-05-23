import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ClientCaseView extends JFrame implements ActionListener {
    private JTable caseInfoTable;
    private DefaultTableModel tableModel;
    private User user;
    private CaseDAO caseDAO;
    private ClientLogin loginPage;
    private JFrame previousWindow;
    private JButton backBtn;
    private ImageIcon bg;

    public ClientCaseView(User user, JFrame previousWindow) {
        super("My Cases");
        this.user = user;
        this.previousWindow = previousWindow;
        this.caseDAO = new CaseDAO();
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initialize();
    }

    private void initialize() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Case ID");
        columnNames.add("Case Name");
        columnNames.add("Opponent");
        columnNames.add("Case Status");

        Vector<Vector<String>> data = readClientCases();

        tableModel = new DefaultTableModel(data, columnNames);
        caseInfoTable = new JTable(tableModel);
        caseInfoTable.setPreferredScrollableViewportSize(new Dimension(1000, 500));

        JScrollPane scrollPane = new JScrollPane(caseInfoTable);
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
        background.setOpaque(true);
        background.setLayout(null);

        this.add(panel);
    }

    private Vector<Vector<String>> readClientCases() {
        Vector<Vector<String>> clientCases = new Vector<>();
        java.util.List<Case> cases = caseDAO.getCasesByClient(user.getUserId());

        for (Case c : cases) {
            Vector<String> caseInfo = new Vector<>();
            caseInfo.add(String.valueOf(c.getCaseId()));
            caseInfo.add(c.getCaseName() != null ? c.getCaseName() : "");
            caseInfo.add(c.getOpposingParty() != null ? c.getOpposingParty() : "");
            caseInfo.add(c.getStatus() != null ? c.getStatus() : "Active");
            clientCases.add(caseInfo);
        }

        return clientCases;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {
            previousWindow.setVisible(true);
            this.setVisible(false);
        }
    }
}