import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class adminDashboard extends JFrame implements MouseListener, ActionListener {
    JPanel panel;
    JLabel wlabel, userNameLabel, emailLabel, image;
    JButton addACaseButton, addALawyerButton, viewCasesButton, editCaseButton, deleteCaseButton, viewLawyersButton, operationsHubButton, demoDataButton, logOutButton;
    Font myFont, Font1;
    ImageIcon icon;
    AdminLogin al;
    ImageIcon bg;
    User user;
    CaseDAO caseDAO;
    UserDAO userDAO;

    public adminDashboard(Admin a, AdminLogin al) {
        super("My dashboard");
        UITheme.configureFrame(this, "Admin Dashboard");

        this.al = al;
        this.user = a.getUser();
        this.caseDAO = new CaseDAO();
        this.userDAO = new UserDAO();

        myFont = UITheme.BODY_FONT;
        Font1 = UITheme.TITLE_FONT;

        panel = UITheme.backgroundPanel("Images/gf8.jpg");
        JPanel shell = UITheme.surfacePanel(410, 66, 460, 560);
        panel.add(shell);

        String displayName = user != null ? user.getFirstName() : "Admin";
        String displayEmail = user != null ? user.getEmail() : "";

        wlabel = UITheme.title("Firm Command Center", 42, 26, 360, 42);
        shell.add(wlabel);

        userNameLabel = UITheme.body("Admin: " + displayName + " (" + (user != null ? user.getUsername() : "") + ")", 46, 84, 330, 28);
        shell.add(userNameLabel);

        emailLabel = UITheme.body("Email: " + displayEmail, 46, 118, 350, 28);
        shell.add(emailLabel);

        addACaseButton = UITheme.primaryButton("Add a case", 46, 176, 170, 36);
        shell.add(addACaseButton);

        viewCasesButton = UITheme.primaryButton("View cases", 236, 176, 170, 36);
        shell.add(viewCasesButton);

        editCaseButton = UITheme.primaryButton("Edit a case", 46, 230, 170, 36);
        shell.add(editCaseButton);

        deleteCaseButton = UITheme.primaryButton("Delete a case", 236, 230, 170, 36);
        shell.add(deleteCaseButton);

        addALawyerButton = UITheme.primaryButton("Add a lawyer", 46, 300, 170, 36);
        shell.add(addALawyerButton);

        viewLawyersButton = UITheme.primaryButton("View lawyers", 236, 300, 170, 36);
        shell.add(viewLawyersButton);

        operationsHubButton = UITheme.primaryButton("Operations Hub", 46, 360, 170, 36);
        shell.add(operationsHubButton);

        demoDataButton = UITheme.primaryButton("Load Suits Demo", 236, 360, 170, 36);
        shell.add(demoDataButton);

        logOutButton = UITheme.primaryButton("Log out", 46, 456, 360, 36);
        shell.add(logOutButton);

        this.add(panel);

        addACaseButton.addActionListener(this);
        addALawyerButton.addActionListener(this);
        viewCasesButton.addActionListener(this);
        editCaseButton.addActionListener(this);
        deleteCaseButton.addActionListener(this);
        viewLawyersButton.addActionListener(this);
        operationsHubButton.addActionListener(this);
        demoDataButton.addActionListener(this);
        logOutButton.addActionListener(this);

        addACaseButton.setFocusable(false);
        addALawyerButton.setFocusable(false);
        viewCasesButton.setFocusable(false);
        editCaseButton.setFocusable(false);
        deleteCaseButton.setFocusable(false);
        viewLawyersButton.setFocusable(false);
        operationsHubButton.setFocusable(false);
        demoDataButton.setFocusable(false);
        logOutButton.setFocusable(false);
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
        if (logOutButton.getText().equals(command)) {
            al.setVisible(true);
            this.setVisible(false);
        } else if (addACaseButton.getText().equals(command)) {
            AddCase ac = new AddCase(this);
            ac.setVisible(true);
            this.setVisible(false);
        } else if (addALawyerButton.getText().equals(command)) {
            addALawyer al = new addALawyer(this);
            al.setVisible(true);
            this.setVisible(false);
        } else if (viewCasesButton.getText().equals(command)) {
            CaseInfoWindow ciw = new CaseInfoWindow(this);
            ciw.setVisible(true);
            this.setVisible(false);
        } else if (editCaseButton.getText().equals(command)) {
            EditCase ec = new EditCase(this);
            ec.setVisible(true);
            this.setVisible(false);
        } else if (deleteCaseButton.getText().equals(command)) {
            DeleteCase dc = new DeleteCase(this);
            dc.setVisible(true);
            this.setVisible(false);
        } else if (viewLawyersButton.getText().equals(command)) {
            LawyerInfoWindow liw = new LawyerInfoWindow(this);
            liw.setVisible(true);
            this.setVisible(false);
        } else if (operationsHubButton.getText().equals(command)) {
            OperationsHub hub = new OperationsHub(this, user);
            hub.setVisible(true);
            this.setVisible(false);
        } else if (demoDataButton.getText().equals(command)) {
            DemoDataSeeder seeder = new DemoDataSeeder();
            int affected = seeder.seedSuitsInspiredDemo();
            if (affected > 0) {
                JOptionPane.showMessageDialog(this, "Loaded Suits-inspired demo data. Default demo password: password");
            } else {
                JOptionPane.showMessageDialog(this, "Demo data was not loaded. Check MySQL/XAMPP and database.sql setup.");
            }
        }
    }
}
