import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class updateProfile extends JFrame implements MouseListener, ActionListener {
    JPanel panel;
    JLabel fNameLabel, lNameLabel, emailLabel, userLabel;
    JTextField fNameTF, lNameTF, emailTF, userTF;
    JButton updateBtn, backBtn, clearBtn;
    Color myColor1, myColor2, myColor3;
    Font myFont, myFont2;
    ImageIcon icon;

    User user;
    UserDAO userDAO;
    dashBoard db;
    ClientLogin cl;

    public updateProfile(User user, dashBoard db, ClientLogin cl) {
        super("Update profile");
        this.setSize(1280, 720);
        icon = new ImageIcon("images/updateProfile.jpg");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.db = db;
        this.cl = cl;
        this.user = user;
        this.userDAO = new UserDAO();

        myColor1 = new Color(191, 232, 247);
        myColor2 = new Color(236, 235, 232);
        myColor3 = new Color(50, 120, 0);
        myFont = new Font("Century", Font.ITALIC, 17);
        myFont2 = new Font("Times New Roman", Font.PLAIN, 16);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(myColor2);

        fNameLabel = new JLabel("First Name: ");
        fNameLabel.setBounds(450, 90, 150, 30);
        fNameLabel.setFont(myFont2);
        panel.add(fNameLabel);

        lNameLabel = new JLabel("Last Name: ");
        lNameLabel.setBounds(450, 130, 150, 30);
        lNameLabel.setFont(myFont2);
        panel.add(lNameLabel);

        fNameTF = new JTextField(user.getFirstName());
        fNameTF.setBounds(600, 90, 200, 30);
        fNameTF.setFont(myFont2);
        panel.add(fNameTF);

        lNameTF = new JTextField(user.getLastName());
        lNameTF.setBounds(600, 130, 200, 30);
        lNameTF.setFont(myFont2);
        panel.add(lNameTF);

        emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(450, 170, 150, 30);
        emailLabel.setFont(myFont2);
        panel.add(emailLabel);

        emailTF = new JTextField(user.getEmail());
        emailTF.setBounds(600, 170, 200, 30);
        emailTF.setFont(myFont2);
        panel.add(emailTF);

        userLabel = new JLabel("Username: ");
        userLabel.setBounds(450, 210, 150, 30);
        userLabel.setFont(myFont2);
        panel.add(userLabel);

        userTF = new JTextField(user.getUsername());
        userTF.setBounds(600, 210, 200, 30);
        userTF.setFont(myFont2);
        userTF.setEditable(false);
        panel.add(userTF);

        clearBtn = new JButton("Clear all");
        clearBtn.setBounds(650, 50, 100, 25);
        clearBtn.setFont(myFont);
        clearBtn.setBackground(myColor1);
        clearBtn.setForeground(Color.BLACK);
        clearBtn.addMouseListener(this);
        clearBtn.addActionListener(this);
        clearBtn.setBorder(null);
        panel.add(clearBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(650, 250, 100, 25);
        updateBtn.setFont(myFont);
        updateBtn.setBackground(myColor1);
        updateBtn.setForeground(Color.BLACK);
        updateBtn.addMouseListener(this);
        updateBtn.addActionListener(this);
        updateBtn.setBorder(null);
        panel.add(updateBtn);

        icon = new ImageIcon("images/backBtn.png");
        backBtn = new JButton(icon);
        backBtn.setBounds(850, 10, 36, 30);
        backBtn.addMouseListener(this);
        backBtn.addActionListener(this);
        backBtn.setBorder(null);
        panel.add(backBtn);

        this.add(panel);
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
        if (updateBtn.getText().equals(command)) {
            String fName = fNameTF.getText();
            String lName = lNameTF.getText();
            String email = emailTF.getText();

            if ((!fName.isEmpty()) && (!lName.isEmpty()) && (!email.isEmpty())) {
                int dialog = JOptionPane.YES_NO_OPTION;
                int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this information?", "Profile update?", dialog);
                if (result == 0) {
                    user.setFirstName(fName);
                    user.setLastName(lName);
                    user.setEmail(email);

                    if (userDAO.updateUser(user)) {
                        JOptionPane.showMessageDialog(this, "Information updated.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update profile.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill up all the fields");
            }
        } else if (backBtn == ae.getSource()) {
            db.setVisible(true);
            this.setVisible(false);
        } else if (clearBtn == ae.getSource()) {
            fNameTF.setText("");
            lNameTF.setText("");
            emailTF.setText("");
        }
    }
}