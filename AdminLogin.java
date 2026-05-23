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

        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        myFont = new Font("Century", Font.ITALIC, 24);
        myFont2 = new Font("Times New Roman", Font.BOLD, 17);

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

        loginButton.setFocusable(false);
        homebutton.setFocusable(false);

        image =new JLabel();
        bg=new ImageIcon("images\\gf1.jpg");
        image.setIcon(bg);
        image.setBounds(0,0,1280,720);
        panel.add(image);

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
            welcomePage.setVisible(true);
        }
    }
}