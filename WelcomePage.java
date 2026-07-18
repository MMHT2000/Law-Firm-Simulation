import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame implements ActionListener {
    JPanel panel;
    JLabel welcomeLabel, roleLabel, imageLabel;
    JButton ClientBtn, LawyerBtn, AdminBtn;
    ImageIcon Image;

    public WelcomePage() {
        super("JMRC & Associates");
        UITheme.configureFrame(this, "JMRC & Associates");

        panel = UITheme.backgroundPanel("Images/gf10.jpg");

        JPanel hero = UITheme.surfacePanel(420, 84, 440, 510);
        panel.add(hero);

        welcomeLabel = UITheme.title("JMRC & Associates", 52, 28, 340, 42);
        hero.add(welcomeLabel);

        JLabel subLabel = UITheme.body("Suits-inspired corporate law command center", 56, 70, 330, 26);
        hero.add(subLabel);

        Image = new ImageIcon("Images/JMRC.png");
        imageLabel = new JLabel(Image);
        imageLabel.setBounds(92, 110, 256, 210);
        hero.add(imageLabel);

        roleLabel = UITheme.section("Choose your portal", 120, 332, 220, 28);
        hero.add(roleLabel);

        ClientBtn = UITheme.primaryButton("Client", 58, 382, 100, 38);
        LawyerBtn = UITheme.primaryButton("Lawyer", 170, 382, 100, 38);
        AdminBtn = UITheme.primaryButton("Admin", 282, 382, 100, 38);

        hero.add(ClientBtn);
        hero.add(LawyerBtn);
        hero.add(AdminBtn);
        ClientBtn.addActionListener(this);
        LawyerBtn.addActionListener(this);
        AdminBtn.addActionListener(this);

        ClientBtn.setFocusable(false);
        LawyerBtn.setFocusable(false);
        AdminBtn.setFocusable(false);

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
