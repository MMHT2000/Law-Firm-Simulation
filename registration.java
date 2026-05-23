import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registration extends JFrame implements ActionListener {
    JPanel panel;
    JLabel URLabel, FNLabel, LNLabel, UNlabel, ELabel, PASSLabel, image;
    JTextField FNTF, LNTF, UNTF, ETF;
    JPasswordField passwordTF;
    JButton createbutton, BTLButton;
    private ImageIcon bg;
    Font Font1 = new Font("Times New Roman", Font.BOLD, 18);
    ClientLogin cl;
    WelcomePage welcomePage;
    private UserDAO userDAO;

    public registration(ClientLogin cl, WelcomePage welcomePage) {
        super("User Registration");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.welcomePage = welcomePage;
        this.cl = cl;
        this.userDAO = new UserDAO();

        panel = new JPanel();
        panel.setLayout(null);

        URLabel = new JLabel("User Registration ");
        URLabel.setBounds(540, 80, 200, 30);
        URLabel.setFont(Font1);
        panel.add(URLabel);

        FNLabel = new JLabel("First Name: ");
        FNLabel.setBounds(500, 120, 100, 30);
        panel.add(FNLabel);

        LNLabel = new JLabel("Last Name: ");
        LNLabel.setBounds(500, 160, 100, 30);
        panel.add(LNLabel);

        UNlabel = new JLabel("Username:");
        UNlabel.setBounds(500, 200, 100, 30);
        panel.add(UNlabel);

        ELabel = new JLabel("Email: ");
        ELabel.setBounds(500, 240, 100, 30);
        panel.add(ELabel);

        PASSLabel = new JLabel("Password: ");
        PASSLabel.setBounds(500, 280, 100, 30);
        panel.add(PASSLabel);

        FNTF = new JTextField();
        FNTF.setBounds(620, 120, 100, 30);
        panel.add(FNTF);

        LNTF = new JTextField();
        LNTF.setBounds(620, 160, 100, 30);
        panel.add(LNTF);

        UNTF = new JTextField();
        UNTF.setBounds(620, 200, 100, 30);
        panel.add(UNTF);

        ETF = new JTextField();
        ETF.setBounds(620, 240, 100, 30);
        panel.add(ETF);

        passwordTF = new JPasswordField();
        passwordTF.setBounds(620, 280, 100, 30);
        passwordTF.setEchoChar('*');
        panel.add(passwordTF);

        createbutton = new JButton("Create Account");
        createbutton.setBackground(new Color(0x2596BE));
        createbutton.setOpaque(true);
        createbutton.addActionListener(this);
        createbutton.setBounds(540, 320, 160, 30);
        panel.add(createbutton);

        BTLButton = new JButton("Back To Login Page");
        BTLButton.setBackground(new Color(0x2596BE));
        BTLButton.setOpaque(true);
        BTLButton.addActionListener(this);
        BTLButton.setBounds(540, 360, 160, 30);
        panel.add(BTLButton);

        image = new JLabel();
        bg = new ImageIcon("images\\gf3.jpg");
        image.setIcon(bg);
        image.setBounds(0, 0, 1280, 720);
        panel.add(image);

        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (createbutton.getText().equals(command)) {
            String fName = FNTF.getText();
            String lName = LNTF.getText();
            String username = UNTF.getText();
            String email = ETF.getText();
            String password = new String(passwordTF.getPassword());

            if ((!fName.isEmpty()) && (!lName.isEmpty()) && (!username.isEmpty()) && (!email.isEmpty()) && (!password.isEmpty())) {
                User user = new User(username, password, email, fName, lName, "client");
                if (userDAO.addUser(user)) {
                    JOptionPane.showMessageDialog(this, "Registration successful. Please login to continue.");
                    cl.setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed. Username may already exist.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Information missing!");
            }
        } else if (BTLButton.getText().equals(command)) {
            ClientLogin loginPage = new ClientLogin(welcomePage);
            loginPage.setVisible(true);
            this.setVisible(false);
        }
    }
}