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
        panel = UITheme.backgroundPanel("Images/gf8.jpg");
        JPanel selector = UITheme.surfacePanel(430, 92, 420, 440);
        panel.add(selector);

        Font font = UITheme.BUTTON_FONT;

        label = UITheme.title("Find Counsel", 72, 34, 300, 42);
        selector.add(label);
        selector.add(UITheme.body("Choose a budget range to shortlist lawyers by hourly rate.", 74, 86, 280, 54));

        lowBtn = UITheme.primaryButton("Low Budget ($50-$150/hr)", 60, 168, 300, 40);
        lowBtn.setFont(font);
        lowBtn.addActionListener(this);
        selector.add(lowBtn);

        medBtn = UITheme.primaryButton("Medium Budget ($151-$300/hr)", 60, 224, 300, 40);
        medBtn.setFont(font);
        medBtn.addActionListener(this);
        selector.add(medBtn);

        highBtn = UITheme.primaryButton("High Budget ($301+/hr)", 60, 280, 300, 40);
        highBtn.setFont(font);
        highBtn.addActionListener(this);
        selector.add(highBtn);

        backBtn = UITheme.primaryButton("Back to Dashboard", 60, 346, 300, 40);
        backBtn.setFont(font);
        backBtn.addActionListener(this);
        selector.add(backBtn);

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
