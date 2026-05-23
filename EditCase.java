import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCase extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel userLabel, caseIdLabel, clintlabel, opponentlabel, casetypelabel, casestatuslabel, casedislabel;
    private JTextField caseIdtf, clinttf, opponenttf, casetypetf, casestatustf;
    private JTextArea textArea;
    private JButton editcase, backToDashboardButton;
    private CaseDAO caseDAO;
    private adminDashboard adminDashboard;
    private JLabel image;
    private ImageIcon bg;

    public EditCase(adminDashboard adminDashboard) {
        super("Edit a Case");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(null);
        this.setLocationRelativeTo(null);
        this.adminDashboard = adminDashboard;
        this.caseDAO = new CaseDAO();

        Font Font1 = new Font("Times New Roman", Font.BOLD, 16);
        Font Font2 = new Font("Times New Roman", Font.BOLD, 14);

        userLabel = new JLabel("Edit Case ");
        userLabel.setBounds(550, 80, 120, 30);
        userLabel.setFont(Font1);
        panel.add(userLabel);

        caseIdLabel = new JLabel("Case ID:");
        caseIdLabel.setBounds(300, 120, 120, 30);
        panel.add(caseIdLabel);

        clintlabel = new JLabel("Client ID:");
        clintlabel.setBounds(300, 160, 120, 30);
        panel.add(clintlabel);

        opponentlabel = new JLabel("Opponent:");
        opponentlabel.setBounds(300, 200, 120, 30);
        panel.add(opponentlabel);

        casetypelabel = new JLabel("Case Type:");
        casetypelabel.setBounds(300, 240, 120, 30);
        panel.add(casetypelabel);

        casestatuslabel = new JLabel("Case Status:");
        casestatuslabel.setBounds(300, 280, 120, 30);
        panel.add(casestatuslabel);

        caseIdtf = new JTextField();
        caseIdtf.setBounds(430, 120, 120, 30);
        panel.add(caseIdtf);

        clinttf = new JTextField();
        clinttf.setBounds(430, 160, 120, 30);
        panel.add(clinttf);

        opponenttf = new JTextField();
        opponenttf.setBounds(430, 200, 120, 30);
        panel.add(opponenttf);

        casetypetf = new JTextField();
        casetypetf.setBounds(430, 240, 120, 30);
        panel.add(casetypetf);

        casestatustf = new JTextField();
        casestatustf.setBounds(430, 280, 120, 30);
        panel.add(casestatustf);

        casedislabel = new JLabel("Case Description:");
        casedislabel.setBounds(580, 120, 120, 30);
        casedislabel.setFont(Font2);
        panel.add(casedislabel);

        textArea = new JTextArea();
        textArea.setBounds(580, 160, 260, 110);
        textArea.setLineWrap(true);
        panel.add(textArea);

        editcase = new JButton("Edit Case");
        editcase.setBackground(new Color(0x2596BE));
        editcase.setOpaque(true);
        editcase.setBounds(470, 530, 160, 30);
        editcase.addActionListener(this);
        panel.add(editcase);

        backToDashboardButton = new JButton("Back to Dashboard");
        backToDashboardButton.setBackground(new Color(0x2596BE));
        backToDashboardButton.setOpaque(true);
        backToDashboardButton.setBounds(650, 530, 160, 30);
        backToDashboardButton.addActionListener(this);
        panel.add(backToDashboardButton);

        image = new JLabel();
        bg = new ImageIcon("images\\bg3.jpg");
        image.setIcon(bg);
        image.setBounds(0, 0, 1280, 720);
        panel.add(image);
        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (backToDashboardButton.getText().equals(command)) {
            adminDashboard.setVisible(true);
            this.setVisible(false);
        } else if (editcase.getText().equals(command)) {
            String caseIdStr = caseIdtf.getText();
            String clientStr = clinttf.getText();
            String opponent = opponenttf.getText();
            String caseType = casetypetf.getText();
            String caseStatus = casestatustf.getText();
            String caseDescription = textArea.getText();

            int caseId = 0;
            int clientId = 0;

            try {
                caseId = Integer.parseInt(caseIdStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Case ID");
                return;
            }

            Case existingCase = caseDAO.getCaseById(caseId);
            if (existingCase == null) {
                JOptionPane.showMessageDialog(this, "Case not found");
                return;
            }

            if (!clientStr.isEmpty()) {
                try {
                    clientId = Integer.parseInt(clientStr);
                    existingCase.setClientId(clientId);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Client ID");
                    return;
                }
            }

            existingCase.setOpposingParty(opponent);
            existingCase.setCaseType(caseType);
            existingCase.setStatus(caseStatus);
            existingCase.setDescription(caseDescription);

            if (caseDAO.updateCase(existingCase)) {
                JOptionPane.showMessageDialog(this, "Case Updated Successfully");
                adminDashboard.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update case");
            }
        }
    }
}