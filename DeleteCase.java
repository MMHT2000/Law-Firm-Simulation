import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteCase extends JFrame implements ActionListener{
    private JPanel panel;
    private JLabel userLabel, Cidlabel, image;
    private JTextField Cidtf;
    ImageIcon bg;
    private JButton deletecase, backToDashboardButton;
    private CaseDAO caseDAO;
    private adminDashboard adminDashboard;

    public DeleteCase(adminDashboard adminDashboard){
        super("Delete a Case");
        this.setSize(1280,720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(null);
        this.setLocationRelativeTo(null);
        this.caseDAO = new CaseDAO();
        this.adminDashboard = adminDashboard;
        Font Font1 = new Font("Times New Roman", Font.BOLD, 16);
        userLabel = new JLabel("Delete Case ");
        userLabel.setBounds(550,80,120,30);
        userLabel.setFont(Font1);
        panel.add(userLabel);
        Cidlabel = new JLabel("Case id to delete:");
        Cidlabel.setBounds(480,150,150,30);
        panel.add(Cidlabel);
        Cidtf = new JTextField();
        Cidtf.setBounds(650,150,120,30);
        panel.add(Cidtf);
        deletecase = new JButton("Delete Case");
        deletecase.setBackground(new Color(0x2596BE));
        deletecase.setOpaque(true);
        deletecase.setBounds(550,200,120,30);
        deletecase.addActionListener(this);
        panel.add(deletecase);
        backToDashboardButton = new JButton("Back to Dashboard");
        backToDashboardButton.setBackground(new Color(0x2596BE));
        backToDashboardButton.setOpaque(true);
        backToDashboardButton.setBounds(680,200,160,30);
        backToDashboardButton.addActionListener(this);
        panel.add(backToDashboardButton);
        image =new JLabel();
        bg=new ImageIcon("images\\bg3.jpg");
        image.setIcon(bg);
        image.setBounds(0,0,1280,720);
        panel.add(image);
        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae){
        String command = ae.getActionCommand();
        if (backToDashboardButton.getText().equals(command)) {
            adminDashboard.setVisible(true);
            this.setVisible(false);
        } else if(deletecase.getText().equals(command)){
            String caseID = Cidtf.getText();
            if(!caseID.isEmpty()){
                int caseId = 0;
                try {
                    caseId = Integer.parseInt(caseID);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Case ID");
                    return;
                }
                Case c = caseDAO.getCaseById(caseId);
                if (c != null) {
                    int dialog = JOptionPane.YES_NO_OPTION;
                    int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete case " + caseID + "?", "Delete Case?", dialog);
                    if (result == 0) {
                        if (caseDAO.deleteCase(caseId)) {
                            JOptionPane.showMessageDialog(this, "Case Deleted Successfully");
                            adminDashboard.setVisible(true);
                            this.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to delete case");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Case not found");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a Case ID");
            }
        }
    }
}