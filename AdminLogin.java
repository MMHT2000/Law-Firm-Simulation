import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel, weLabel, image;
    private JTextField usernameField;
    private JPasswordField passwordTF;
    private JButton loginButton, homebutton;
    private Font Font1 = new Font("Times New Roman", Font.BOLD, 14);
    private ImageIcon bg;
    private JPanel panel;
    private WelcomePage welcomePage;
    private UserDAO userDAO;
    Font myFont, myFont2;

    public AdminLogin(WelcomePage welcomePage) {
        super("Admin Login Portal");
        this.welcomePage = welcomePage;
        this.userDAO = new UserDAO();

        UITheme.configureFrame(this, "Admin Login Portal");

        panel = UITheme.backgroundPanel("Images/gf1.jpg");
        JPanel loginPanel = UITheme.surfacePanel(470, 150, 340, 360);
        panel.add(loginPanel);

        myFont = UITheme.TITLE_FONT;
        myFont2 = UITheme.BODY_FONT;

        weLabel = UITheme.title("Managing Partner", 36, 28, 270, 38);
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

        loginButton = UITheme.primaryButton("Login", 42, 218, 120, 34);
        loginButton.setFont(myFont2);
        loginPanel.add(loginButton);
        loginButton.addActionListener(this);

        homebutton = UITheme.primaryButton("Home", 176, 218, 120, 34);
        homebutton.setFont(myFont2);
        loginPanel.add(homebutton);
        homebutton.addActionListener(this);

        loginButton.setFocusable(false);
        homebutton.setFocusable(false);

        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals("Login")) {
            String username = usernameField.getText();
            String password = passwordTF.getText();

            User user = userDAO.authenticate(username, password);
            if (user != null && "admin".equals(user.getRole())) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                this.setVisible(false);
                Admin a = new Admin(user);
                adminDashboard ad = new adminDashboard(a, this);
                ad.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username, password, or not an admin account!");
            }
        } else if (command.equals("Home")) {
            this.setVisible(false);
            if (welcomePage != null) {
                welcomePage.setVisible(true);
            } else {
                new WelcomePage().setVisible(true);
            }
        }
    }
}
