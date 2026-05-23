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

    public LawyerLogin(WelcomePage welcomePage) {
        super("Lawyer Login Portal");
        this.welcomePage = welcomePage;
        this.userDAO = new UserDAO();

        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        myFont = new Font("Century", Font.ITALIC, 24);
        myFont2 = new Font("Times New Roman", Font.PLAIN, 17);

        weLabel = new JLabel("WELCOME BACK! ");
        weLabel.setBounds(550, 200, 300, 30);
        weLabel.setForeground(Color.white);
        weLabel.setFont(myFont);
        panel.add(weLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(myFont2);
        usernameLabel.setForeground(Color.white);
        usernameLabel.setBounds(525, 250, 120, 30);
        panel.add(usernameLabel);

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(525, 300, 100, 30);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(myFont2);
        panel.add(passwordLabel);

        usernameField = new JTextField();
        usernameField.setBounds(675, 250, 100, 30);
        panel.add(usernameField);

        passwordTF = new JPasswordField();
        passwordTF.setBounds(675, 300, 100, 30);
        passwordTF.setEchoChar('*');
        panel.add(passwordTF);

        loginButton = new JButton("Login");
        loginButton.setFont(myFont2);
        loginButton.setBackground(new Color(0x2596BE));
        loginButton.setOpaque(true);
        loginButton.setBounds(545, 350, 95, 30);
        panel.add(loginButton);
        loginButton.addActionListener(this);

        homebutton = new JButton("Home");
        homebutton.setFont(myFont2);
        homebutton.setBackground(new Color(0x2596BE));
        homebutton.setOpaque(true);
        homebutton.setBounds(650, 350, 95, 30);
        panel.add(homebutton);
        homebutton.addActionListener(this);

        RegBtn = new JButton("Create an Account");
        RegBtn.setFont(Font1);
        RegBtn.setBounds(545, 400, 200, 30);
        RegBtn.setBackground(new Color(0x2596BE));
        panel.add(RegBtn);
        RegBtn.addActionListener(this);

        RegBtn.setFocusable(false);
        loginButton.setFocusable(false);
        homebutton.setFocusable(false);

        image = new JLabel();
        bg = new ImageIcon("images\\gf7.jpg");
        image.setIcon(bg);
        image.setBounds(0, 0, 1280, 720);
        panel.add(image);

        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (RegBtn.getText().equals(command)) {
            registration2 r1 = new registration2(this, welcomePage);
            r1.setVisible(true);
            this.setVisible(false);
        } else if (homebutton.getText().equals(command)) {
            welcomePage.setVisible(true);
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