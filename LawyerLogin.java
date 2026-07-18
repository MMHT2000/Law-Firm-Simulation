import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LawyerLogin extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel, weLabel, image;
    private JTextField usernameField;
    private JPasswordField passwordTF;
    private JButton loginButton, homebutton, RegBtn;
    private Font Font1 = new Font("Times New Roman", Font.BOLD, 14);
    private JPanel panel;
    Font myFont, myFont2;
    private WelcomePage welcomePage;
    private UserDAO userDAO;
    ImageIcon bg;

    public LawyerLogin(WelcomePage welcomePage, lawyers legacyLawyers) {
        this(welcomePage);
    }

    public LawyerLogin(WelcomePage welcomePage) {
        super("Lawyer Login Portal");
        this.welcomePage = welcomePage;
        this.userDAO = new UserDAO();

        UITheme.configureFrame(this, "Lawyer Login Portal");

        panel = UITheme.backgroundPanel("Images/gf7.jpg");
        JPanel loginPanel = UITheme.surfacePanel(470, 142, 340, 390);
        panel.add(loginPanel);

        myFont = UITheme.TITLE_FONT;
        myFont2 = UITheme.BODY_FONT;

        weLabel = UITheme.title("Lawyer Portal", 58, 28, 240, 38);
        loginPanel.add(weLabel);

        usernameLabel = UITheme.body("Username", 42, 98, 120, 26);
        usernameLabel.setFont(myFont2);
        loginPanel.add(usernameLabel);

        passwordLabel = UITheme.body("Password", 42, 150, 100, 26);
        passwordLabel.setFont(myFont2);
        loginPanel.add(passwordLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 96, 145, 30);
        loginPanel.add(usernameField);

        passwordTF = new JPasswordField();
        passwordTF.setBounds(150, 148, 145, 30);
        passwordTF.setEchoChar('*');
        loginPanel.add(passwordTF);

        loginButton = UITheme.primaryButton("Login", 42, 214, 120, 34);
        loginButton.setFont(myFont2);
        loginPanel.add(loginButton);
        loginButton.addActionListener(this);

        homebutton = UITheme.primaryButton("Home", 176, 214, 120, 34);
        homebutton.setFont(myFont2);
        loginPanel.add(homebutton);
        homebutton.addActionListener(this);

        RegBtn = UITheme.primaryButton("Create an Account", 42, 276, 254, 34);
        RegBtn.setFont(Font1);
        loginPanel.add(RegBtn);
        RegBtn.addActionListener(this);

        RegBtn.setFocusable(false);
        loginButton.setFocusable(false);
        homebutton.setFocusable(false);

        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (RegBtn.getText().equals(command)) {
            registration2 r1 = new registration2(this, welcomePage);
            r1.setVisible(true);
            this.setVisible(false);
        } else if (homebutton.getText().equals(command)) {
            if (welcomePage != null) {
                welcomePage.setVisible(true);
            } else {
                new WelcomePage().setVisible(true);
            }
            this.setVisible(false);
        } else if (loginButton.getText().equals(command)) {
            String name = usernameField.getText();
            String pass = passwordTF.getText();

            User user = userDAO.authenticate(name, pass);
            if (user != null && "lawyer".equals(user.getRole())) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                lawyerDashboard db = new lawyerDashboard(user, this);
                db.setVisible(true);
                this.setVisible(false);
            } else if (user != null) {
                JOptionPane.showMessageDialog(this, "This account is not a lawyer account!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }
}
