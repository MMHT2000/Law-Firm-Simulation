import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class dashBoard extends JFrame implements MouseListener, ActionListener {
    JPanel panel;
    JLabel wlabel, nameLabel, emailLabel, passwordLabel, image;
    JButton logoutBtn, showBtn, editBtn, deleteBtn, myCasesBtn, hireLawyerBtn;
    Font myFont, Font1;
    ImageIcon icon;
    String hiddenPass = "";
    ClientLogin cl;
    ImageIcon bg;

    User user;
    UserDAO userDAO;

    public dashBoard(User user, ClientLogin cl) {
        super("My dashboard");
        this.setSize(1280, 720);
        icon = new ImageIcon("images/student.jpg");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.cl = cl;
        this.user = user;
        this.userDAO = new UserDAO();

        myFont = new Font("Cambria", Font.PLAIN, 17);
        Font1 = new Font("Times New Roman", Font.BOLD, 24);

        panel = new JPanel();
        panel.setLayout(null);

        wlabel = new JLabel("Welcome back, " + user.getFirstName() + "!");
        wlabel.setForeground(Color.white);
        wlabel.setBounds(550, 50, 300, 50);
        wlabel.setFont(Font1);
        panel.add(wlabel);

        nameLabel = new JLabel("Name: " + user.getFirstName() + " " + user.getLastName());
        nameLabel.setBounds(480, 150, 200, 30);
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(myFont);
        panel.add(nameLabel);

        emailLabel = new JLabel("Email: " + user.getEmail());
        emailLabel.setBounds(480, 190, 350, 30);
        emailLabel.setForeground(Color.white);
        emailLabel.setFont(myFont);
        panel.add(emailLabel);

        hiddenPass = "**********";
        passwordLabel = new JLabel("Password: " + hiddenPass);
        passwordLabel.setBounds(480, 230, 200, 30);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(myFont);
        panel.add(passwordLabel);

        logoutBtn = new JButton("Log out");
        logoutBtn.setBounds(580, 380, 100, 30);
        logoutBtn.setBorder(null);
        logoutBtn.setBackground(new Color(0x2596BE));
        logoutBtn.addActionListener(this);
        panel.add(logoutBtn);

        showBtn = new JButton("Show");
        showBtn.setBounds(680, 230, 80, 30);
        showBtn.setBorder(null);
        showBtn.setBackground(new Color(0x2596BE));
        showBtn.addMouseListener(this);
        showBtn.addActionListener(this);
        panel.add(showBtn);

        editBtn = new JButton("Edit profile");
        editBtn.setBounds(580, 280, 100, 30);
        editBtn.setBorder(null);
        editBtn.setBackground(new Color(0x2596BE));
        editBtn.addActionListener(this);
        panel.add(editBtn);

        deleteBtn = new JButton("Delete profile");
        deleteBtn.setBounds(580, 330, 100, 30);
        deleteBtn.setBorder(null);
        deleteBtn.addMouseListener(this);
        deleteBtn.setBackground(new Color(0x2596BE));
        deleteBtn.addActionListener(this);
        panel.add(deleteBtn);

        myCasesBtn = new JButton("My Cases");
        myCasesBtn.setBounds(580, 230, 100, 30);
        myCasesBtn.setBorder(null);
        myCasesBtn.setBackground(new Color(0x2596BE));
        myCasesBtn.addActionListener(this);
        panel.add(myCasesBtn);

        hireLawyerBtn = new JButton("Hire Lawyer");
        hireLawyerBtn.setBounds(690, 230, 100, 30);
        hireLawyerBtn.setBorder(null);
        hireLawyerBtn.setBackground(new Color(0x2596BE));
        hireLawyerBtn.addActionListener(this);
        panel.add(hireLawyerBtn);

        image = new JLabel();
        bg = new ImageIcon("images\\gf8.jpg");
        image.setIcon(bg);
        image.setBounds(0, 0, 1280, 720);
        panel.add(image);

        this.add(panel);
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == deleteBtn) {
            deleteBtn.setForeground(Color.RED);
        }
    }

    public void mouseExited(MouseEvent me) {
        if (me.getSource() == deleteBtn) {
            deleteBtn.setForeground(Color.BLACK);
        }
    }

    public void mousePressed(MouseEvent me) {
        // Password is hashed, cannot show
    }

    public void mouseReleased(MouseEvent me) {
        // Password is hashed, cannot show
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (logoutBtn.getText().equals(command)) {
            ClientLogin loginPage = new ClientLogin(null);
            loginPage.setVisible(true);
            this.setVisible(false);
        } else if (deleteBtn.getText().equals(command)) {
            int dialog = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Delete account?", dialog);
            if (result == 0) {
                if (userDAO.deleteUser(user.getUserId())) {
                    JOptionPane.showMessageDialog(this, "User deleted!");
                    ClientLogin loginPage = new ClientLogin(null);
                    loginPage.setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete account!");
                }
            }
        } else if (editBtn.getText().equals(command)) {
            updateProfile upr = new updateProfile(user, this, cl);
            upr.setVisible(true);
            this.setVisible(false);
        } else if (myCasesBtn.getText().equals(command)) {
            ClientCaseView ccv = new ClientCaseView(user, this);
            ccv.setVisible(true);
            this.setVisible(false);
        } else if (hireLawyerBtn.getText().equals(command)) {
            BudgetRange br = new BudgetRange(this);
            br.setVisible(true);
            this.setVisible(false);
        }
    }
}