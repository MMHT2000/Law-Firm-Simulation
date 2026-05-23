import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame implements ActionListener {
    JPanel panel;
    JLabel welcomeLabel, roleLabel, imageLabel, image;
    JButton ClientBtn, LawyerBtn, AdminBtn;
    ImageIcon Image, bg;

    public WelcomePage() {
        super("JMRC & Associates");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        Font Font1 = new Font("Times New Roman", Font.BOLD, 24);
        Font Font2 = new Font("Times New Roman", Font.BOLD, 16);
        Font Font3 = new Font("Times New Roman", Font.BOLD, 14);

        welcomeLabel = new JLabel("Welcome to JMRS & Associates");
        welcomeLabel.setBounds(490, 120, 350, 30);
        welcomeLabel.setForeground(Color.white);
        welcomeLabel.setFont(Font1);
        panel.add(welcomeLabel);

        Image = new ImageIcon("Images/JMRC.png");
        imageLabel = new JLabel(Image);
        imageLabel.setBounds(520, 160, 256, 256);

        panel.add(imageLabel);

        roleLabel = new JLabel("I am a/an");
        roleLabel.setBounds(610, 390, 80, 20);
        roleLabel.setForeground(Color.white);
        roleLabel.setFont(Font2);
        panel.add(roleLabel);

        ClientBtn = new JButton("Client");
        ClientBtn.setBounds(530, 430, 100, 40);
        ClientBtn.setFont(Font3);
        ClientBtn.setBackground(new Color(0x2596BE));
        panel.add(ClientBtn);
        ClientBtn.addActionListener(this);

        LawyerBtn = new JButton("Lawyer");
        LawyerBtn.setBounds(650, 430, 100, 40);
        LawyerBtn.setFont(Font3);
        LawyerBtn.setBackground(new Color(0x2596BE));
        panel.add(LawyerBtn);
        LawyerBtn.addActionListener(this);

        AdminBtn = new JButton("Admin");
        AdminBtn.setBounds(590, 480, 100, 40);
        AdminBtn.setFont(Font3);
        AdminBtn.setBackground(new Color(0x2596BE));
        panel.add(AdminBtn);
        AdminBtn.addActionListener(this);

        ClientBtn.setFocusable(false);
        LawyerBtn.setFocusable(false);
        AdminBtn.setFocusable(false);

        this.setIconImage(Image.getImage());

        image = new JLabel();
        bg = new ImageIcon("images\\gf10.jpg");
        image.setIcon(bg);
        image.setBounds(0, 0, 1280, 720);
        panel.add(image);

        this.add(panel);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (ClientBtn.getText().equals(command)) {
            ClientLogin cl = new ClientLogin(this);
            cl.setVisible(true);
            this.setVisible(false);
        } else if (LawyerBtn.getText().equals(command)) {
            LawyerLogin ll = new LawyerLogin(this);
            ll.setVisible(true);
            this.setVisible(false);
        } else if (AdminBtn.getText().equals(command)) {
            AdminLogin al = new AdminLogin(this);
            al.setVisible(true);
            this.setVisible(false);
        }
    }
}