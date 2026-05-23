import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage();
            new ClientLogin(welcomePage);

            System.out.println();
            System.out.println("Database: MySQL via XAMPP");
            System.out.println("Default admin username: admin");
            System.out.println("Please check database.sql for initial setup");
            System.out.println();

            welcomePage.setVisible(true);
        });
    }
}
