import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetRange extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel label;
    private JButton lowBtn, medBtn, highBtn, backBtn;
    private dashBoard dashboard;
    private ImageIcon bg;

    public BudgetRange(dashBoard dashboard) {
        super("Select Budget Range");
        this.dashboard = dashboard;
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initialize();
    }

    private void initialize() {
        panel = new JPanel();
        panel.setLayout(null);

        Font font = new Font("Times New Roman", Font.BOLD, 18);

        label = new JLabel("Select Your Budget Range:");
        label.setBounds(500, 100, 300, 40);
        label.setFont(font);
        label.setForeground(Color.white);
        panel.add(label);

        lowBtn = new JButton("Low Budget ($500-$2000)");
        lowBtn.setBounds(500, 180, 300, 40);
        lowBtn.setFont(font);
        lowBtn.setBackground(new Color(0x2596BE));
        lowBtn.addActionListener(this);
        panel.add(lowBtn);

        medBtn = new JButton("Medium Budget ($2000-$5000)");
        medBtn.setBounds(500, 240, 300, 40);
        medBtn.setFont(font);
        medBtn.setBackground(new Color(0x2596BE));
        medBtn.addActionListener(this);
        panel.add(medBtn);

        highBtn = new JButton("High Budget ($5000+)");
        highBtn.setBounds(500, 300, 300, 40);
        highBtn.setFont(font);
        highBtn.setBackground(new Color(0x2596BE));
        highBtn.addActionListener(this);
        panel.add(highBtn);

        backBtn = new JButton("Back to Dashboard");
        backBtn.setBounds(500, 360, 300, 40);
        backBtn.setFont(font);
        backBtn.setBackground(new Color(0x2596BE));
        backBtn.addActionListener(this);
        panel.add(backBtn);

        JLabel background = new JLabel();
        bg = new ImageIcon("images\\gf8.jpg");
        background.setIcon(bg);
        background.setBounds(0, 0, 1280, 720);
        panel.add(background);

        this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (backBtn.getText().equals(command)) {
            dashboard.setVisible(true);
            this.setVisible(false);
        } else {
            LawyerFilter lf = new LawyerFilter(dashboard, command);
            lf.setVisible(true);
            this.setVisible(false);
        }
    }
}