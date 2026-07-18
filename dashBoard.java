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
        UITheme.configureFrame(this, "Client Dashboard");

        this.cl = cl;
        this.user = user;
        this.userDAO = new UserDAO();

        myFont = UITheme.BODY_FONT;
        Font1 = UITheme.TITLE_FONT;

        panel = UITheme.backgroundPanel("Images/gf8.jpg");

        JPanel profilePanel = UITheme.surfacePanel(120, 82, 440, 430);
        JPanel actionPanel = UITheme.surfacePanel(610, 82, 430, 430);
        panel.add(profilePanel);
        panel.add(actionPanel);

        wlabel = UITheme.title("Welcome back, " + user.getFirstName() + "!", 28, 24, 360, 42);
        profilePanel.add(wlabel);

        nameLabel = UITheme.body("Name: " + user.getFirstName() + " " + user.getLastName(), 32, 104, 330, 30);
        profilePanel.add(nameLabel);

        emailLabel = UITheme.body("Email: " + user.getEmail(), 32, 146, 360, 30);
        profilePanel.add(emailLabel);

        hiddenPass = "**********";
        passwordLabel = UITheme.body("Password: " + hiddenPass, 32, 188, 210, 30);
        profilePanel.add(passwordLabel);

        showBtn = UITheme.primaryButton("Show", 255, 188, 86, 30);
        showBtn.addMouseListener(this);
        showBtn.addActionListener(this);
        profilePanel.add(showBtn);

        editBtn = UITheme.primaryButton("Edit profile", 32, 270, 142, 34);
        editBtn.addActionListener(this);
        profilePanel.add(editBtn);

        deleteBtn = UITheme.primaryButton("Delete profile", 188, 270, 154, 34);
        deleteBtn.addMouseListener(this);
        deleteBtn.addActionListener(this);
        profilePanel.add(deleteBtn);

        logoutBtn = UITheme.primaryButton("Log out", 32, 320, 310, 34);
        logoutBtn.addActionListener(this);
        profilePanel.add(logoutBtn);

        actionPanel.add(UITheme.title("Client Services", 28, 24, 320, 42));
        actionPanel.add(UITheme.body("Track your active matters, review firm updates, and find counsel that matches your budget.", 32, 78, 342, 58));

        myCasesBtn = UITheme.primaryButton("My Cases", 32, 166, 330, 42);
        myCasesBtn.addActionListener(this);
        actionPanel.add(myCasesBtn);

        hireLawyerBtn = UITheme.primaryButton("Hire Lawyer", 32, 226, 330, 42);
        hireLawyerBtn.addActionListener(this);
        actionPanel.add(hireLawyerBtn);

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
