import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addALawyer extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel titleLabel, usernameLabel, fnameLabel, lnameLabel, emailLabel, passwordLabel, designationLabel, lawyerTypeLabel, educationLabel;
    private JTextField usernameTF, fnameTF, lnameTF, emailTF, designationTF, lawyerTypeTF, educationTF;
    private JPasswordField passwordTF;
    private JButton addBtn, backBtn;
    private UserDAO userDAO;
    private adminDashboard adminDashboard;

    public addALawyer(adminDashboard adminDashboard) {
        super("Add a Lawyer");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.adminDashboard = adminDashboard;
        this.userDAO = new UserDAO();

        panel = new JPanel();
        panel.setLayout(null);

        Font Font1 = new Font("Times New Roman", Font.BOLD, 18);
        Font Font2 = new Font("Times New Roman", Font.BOLD, 14);

        titleLabel = new JLabel("Add a New Lawyer");
        titleLabel.setFont(Font1);
        titleLabel.setBounds(550, 50, 200, 30);
        panel.add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(400, 100, 100, 25);
        panel.add(usernameLabel);

        usernameTF = new JTextField();
        usernameTF.setBounds(520, 100, 150, 25);
        panel.add(usernameTF);

        fnameLabel = new JLabel("First Name:");
        fnameLabel.setBounds(400, 140, 100, 25);
        panel.add(fnameLabel);

        fnameTF = new JTextField();
        fnameTF.setBounds(520, 140, 150, 25);
        panel.add(fnameTF);

        lnameLabel = new JLabel("Last Name:");
        lnameLabel.setBounds(700, 140, 100, 25);
        panel.add(lnameLabel);

        lnameTF = new JTextField();
        lnameTF.setBounds(820, 140, 150, 25);
        panel.add(lnameTF);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(400, 180, 100, 25);
        panel.add(emailLabel);

        emailTF = new JTextField();
        emailTF.setBounds(520, 180, 150, 25);
        panel.add(emailTF);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(700, 180, 100, 25);
        panel.add(passwordLabel);

        passwordTF = new JPasswordField();
        passwordTF.setBounds(820, 180, 150, 25);
        panel.add(passwordTF);

        designationLabel = new JLabel("Designation:");
        designationLabel.setBounds(400, 220, 100, 25);
        panel.add(designationLabel);

        designationTF = new JTextField();
        designationTF.setBounds(520, 220, 150, 25);
        panel.add(designationTF);

        lawyerTypeLabel = new JLabel("Lawyer Type:");
        lawyerTypeLabel.setBounds(400, 260, 100, 25);
        panel.add(lawyerTypeLabel);

        lawyerTypeTF = new JTextField();
        lawyerTypeTF.setBounds(520, 260, 150, 25);
        panel.add(lawyerTypeTF);

        educationLabel = new JLabel("Education:");
        educationLabel.setBounds(400, 300, 100, 25);
        panel.add(educationLabel);

        educationTF = new JTextField();
        educationTF.setBounds(520, 300, 150, 25);
        panel.add(educationTF);

        addBtn = new JButton("Add Lawyer");
        addBtn.setBounds(520, 360, 120, 30);
        addBtn.setBackground(new Color(0x2596BE));
        addBtn.addActionListener(this);
        panel.add(addBtn);

        backBtn = new JButton("Back to Dashboard");
        backBtn.setBounds(650, 360, 150, 30);
        backBtn.setBackground(new Color(0x2596BE));
        backBtn.addActionListener(this);
        panel.add(backBtn);

        JLabel background = new JLabel();
        ImageIcon bg = new ImageIcon("images\\bg3.jpg");
        background.setIcon(bg);
        background.setBounds(0, 0, 1280, 720);
        panel.add(background);

        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (backBtn.getText().equals(command)) {
            adminDashboard.setVisible(true);
            this.setVisible(false);
        } else if (addBtn.getText().equals(command)) {
            String username = usernameTF.getText();
            String firstName = fnameTF.getText();
            String lastName = lnameTF.getText();
            String email = emailTF.getText();
            String password = new String(passwordTF.getPassword());

            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            User lawyer = new User();
            lawyer.setUsername(username);
            lawyer.setFirstName(firstName);
            lawyer.setLastName(lastName);
            lawyer.setEmail(email);
            lawyer.setPassword(password);
            lawyer.setRole("lawyer");

            if (userDAO.addUser(lawyer)) {
                JOptionPane.showMessageDialog(this, "Lawyer added successfully!");
                adminDashboard.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add lawyer!");
            }
        }
    }
}