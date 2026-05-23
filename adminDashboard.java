import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class adminDashboard extends JFrame implements MouseListener, ActionListener {
    JPanel panel;
    JLabel wlabel, userNameLabel, emailLabel, image;
    JButton addACaseButton, addALawyerButton, viewCasesButton, editCaseButton, deleteCaseButton, viewLawyersButton, logOutButton;
    Font myFont, Font1;
    ImageIcon icon;
    AdminLogin al;
    ImageIcon bg;
    User user;
    CaseDAO caseDAO;
    UserDAO userDAO;

    public adminDashboard(Admin a, AdminLogin al) {
        super("My dashboard");
        this.setSize(1280, 720);
        icon = new ImageIcon("images/student.jpg");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.al = al;
        this.user = a.getUser();
        this.caseDAO = new CaseDAO();
        this.userDAO = new UserDAO();

        myFont = new Font("Cambria", Font.PLAIN, 17);
        Font1 = new Font("Times New Roman", Font.BOLD, 24);

        panel = new JPanel();
        panel.setLayout(null);

        String displayName = user != null ? user.getFirstName() : "Admin";
        String displayEmail = user != null ? user.getEmail() : "";

        wlabel = new JLabel("Welcome back, " + displayName + "!");
        wlabel.setBounds(550, 50, 300, 50);
        wlabel.setForeground(Color.white);
        wlabel.setFont(Font1);
        panel.add(wlabel);

        userNameLabel = new JLabel("Username: " + (user != null ? user.getUsername() : ""));
        userNameLabel.setBounds(550, 150, 200, 30);
        userNameLabel.setForeground(Color.white);
        userNameLabel.setFont(myFont);
        panel.add(userNameLabel);

        emailLabel = new JLabel("Email: " + displayEmail);
        emailLabel.setBounds(550, 190, 350, 30);
        emailLabel.setForeground(Color.white);
        emailLabel.setFont(myFont);
        panel.add(emailLabel);

        addACaseButton = new JButton("Add a case");
        addACaseButton.setBounds(550, 230, 200, 30);
        addACaseButton.setFont(myFont);
        addACaseButton.setBackground(new Color(0x2596BE));
        panel.add(addACaseButton);

        viewCasesButton = new JButton("View cases");
        viewCasesButton.setBounds(550, 270, 200, 30);
        viewCasesButton.setFont(myFont);
        viewCasesButton.setBackground(new Color(0x2596BE));
        panel.add(viewCasesButton);

        editCaseButton = new JButton("Edit a case");
        editCaseButton.setBounds(550, 310, 200, 30);
        editCaseButton.setFont(myFont);
        editCaseButton.setBackground(new Color(0x2596BE));
        panel.add(editCaseButton);

        deleteCaseButton = new JButton("Delete a case");
        deleteCaseButton.setBounds(550, 350, 200, 30);
        deleteCaseButton.setFont(myFont);
        deleteCaseButton.setBackground(new Color(0x2596BE));
        panel.add(deleteCaseButton);

        addALawyerButton = new JButton("Add a lawyer");
        addALawyerButton.setBounds(550, 390, 200, 30);
        addALawyerButton.setFont(myFont);
        addALawyerButton.setBackground(new Color(0x2596BE));
        panel.add(addALawyerButton);

        viewLawyersButton = new JButton("View lawyers");
        viewLawyersButton.setBounds(550, 430, 200, 30);
        viewLawyersButton.setFont(myFont);
        viewLawyersButton.setBackground(new Color(0x2596BE));
        panel.add(viewLawyersButton);

        logOutButton = new JButton("Log out");
        logOutButton.setBounds(550, 560, 200, 30);
        logOutButton.setFont(myFont);
        logOutButton.setBackground(new Color(0x2596BE));
        panel.add(logOutButton);

        image = new JLabel();
        bg = new ImageIcon("images\\gf8.jpg");
        image.setIcon(bg);
        image.setBounds(0, 0, 1280, 720);
        panel.add(image);

        this.add(panel);

        addACaseButton.addActionListener(this);
        addALawyerButton.addActionListener(this);
        viewCasesButton.addActionListener(this);
        editCaseButton.addActionListener(this);
        deleteCaseButton.addActionListener(this);
        viewLawyersButton.addActionListener(this);
        logOutButton.addActionListener(this);

        addACaseButton.setFocusable(false);
        addALawyerButton.setFocusable(false);
        viewCasesButton.setFocusable(false);
        editCaseButton.setFocusable(false);
        deleteCaseButton.setFocusable(false);
        viewLawyersButton.setFocusable(false);
        logOutButton.setFocusable(false);
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (logOutButton.getText().equals(command)) {
            al.setVisible(true);
            this.setVisible(false);
        } else if (addACaseButton.getText().equals(command)) {
            AddCase ac = new AddCase(this);
            ac.setVisible(true);
            this.setVisible(false);
        } else if (addALawyerButton.getText().equals(command)) {
            addALawyer al = new addALawyer(this);
            al.setVisible(true);
            this.setVisible(false);
        } else if (viewCasesButton.getText().equals(command)) {
            CaseInfoWindow ciw = new CaseInfoWindow();
            ciw.setVisible(true);
            this.setVisible(false);
        } else if (editCaseButton.getText().equals(command)) {
            EditCase ec = new EditCase(this);
            ec.setVisible(true);
            this.setVisible(false);
        } else if (deleteCaseButton.getText().equals(command)) {
            DeleteCase dc = new DeleteCase(this);
            dc.setVisible(true);
            this.setVisible(false);
        } else if (viewLawyersButton.getText().equals(command)) {
            LawyerInfoWindow liw = new LawyerInfoWindow();
            liw.setVisible(true);
            this.setVisible(false);
        }
    }
}