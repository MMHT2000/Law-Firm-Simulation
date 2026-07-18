import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class UITheme {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final Color PRIMARY = new Color(0x17202A);
    public static final Color PRIMARY_DARK = new Color(0x0B1117);
    public static final Color ACCENT = new Color(0xC9A24A);
    public static final Color SURFACE = new Color(250, 247, 239, 230);
    public static final Color TEXT_DARK = new Color(0x151A20);
    public static final Font TITLE_FONT = new Font("Cambria", Font.BOLD, 30);
    public static final Font SECTION_FONT = new Font("Cambria", Font.BOLD, 21);
    public static final Font BODY_FONT = new Font("Cambria", Font.PLAIN, 16);
    public static final Font BUTTON_FONT = new Font("Cambria", Font.BOLD, 15);

    private UITheme() {}

    public static JPanel backgroundPanel(String imagePath) {
        return new BackgroundPanel(imagePath);
    }

    public static JPanel surfacePanel(int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, width, height);
        panel.setBackground(SURFACE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 160)),
                new EmptyBorder(18, 22, 18, 22)
        ));
        return panel;
    }

    public static JLabel title(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(TEXT_DARK);
        label.setFont(TITLE_FONT);
        return label;
    }

    public static JLabel section(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(PRIMARY_DARK);
        label.setFont(SECTION_FONT);
        return label;
    }

    public static JLabel body(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(TEXT_DARK);
        label.setFont(BODY_FONT);
        return label;
    }

    public static JButton primaryButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(BUTTON_FONT);
        button.setForeground(new Color(0xF7E8BE));
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        return button;
    }

    public static void configureFrame(JFrame frame, String title) {
        frame.setTitle(title);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("Images/JMRC.png");
        if (icon.getIconWidth() > 0) {
            frame.setIconImage(icon.getImage());
        }
    }

    private static class BackgroundPanel extends JPanel {
        private final Image image;

        BackgroundPanel(String imagePath) {
            setLayout(null);
            ImageIcon icon = new ImageIcon(imagePath);
            image = icon.getIconWidth() > 0 ? icon.getImage() : null;
            setBackground(PRIMARY_DARK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(5, 8, 12, 146));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(new Color(201, 162, 74, 38));
            g2.fillRect(0, 0, getWidth(), 5);
            g2.dispose();
        }
    }
}
