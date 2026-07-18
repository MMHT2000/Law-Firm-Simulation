import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class OperationsHub extends JFrame implements ActionListener {
    private final JFrame previousScreen;
    private final User currentUser;
    private final FirmOperationsDAO dao;
    private final JTabbedPane tabs;
    private final JButton backButton;
    private final JButton refreshButton;

    public OperationsHub(JFrame previousScreen, User currentUser) {
        super("Firm Operations Hub");
        this.previousScreen = previousScreen;
        this.currentUser = currentUser;
        this.dao = new FirmOperationsDAO();

        UITheme.configureFrame(this, "Firm Operations Hub");
        JPanel background = UITheme.backgroundPanel("Images/bg4.jpg");

        JPanel shell = UITheme.surfacePanel(70, 42, 1140, 590);
        shell.setLayout(new BorderLayout(10, 10));
        background.add(shell);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = UITheme.title("Firm Operations Hub", 0, 0, 500, 44);
        header.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        refreshButton = UITheme.primaryButton("Refresh", 0, 0, 120, 34);
        backButton = UITheme.primaryButton("Back", 0, 0, 100, 34);
        refreshButton.addActionListener(this);
        backButton.addActionListener(this);
        actions.add(refreshButton);
        actions.add(backButton);
        header.add(actions, BorderLayout.EAST);
        shell.add(header, BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.setFont(UITheme.BODY_FONT);
        shell.add(tabs, BorderLayout.CENTER);
        background.add(shell);

        this.add(background);
        loadTabs();
    }

    private void loadTabs() {
        tabs.removeAll();
        tabs.addTab("Overview", tablePanel(new String[]{"Metric", "Value"}, dao.getReportRows(), null));
        tabs.addTab("Matters", tablePanel(new String[]{"Case #", "Matter", "Type", "Status", "Court", "Jurisdiction"}, dao.getMatterRows(), null));
        tabs.addTab("Intake", tablePanel(new String[]{"Client ID", "Client", "Email", "Company", "Retainer"}, dao.getIntakeRows(), null));
        tabs.addTab("Calendar", tablePanel(new String[]{"Case #", "Type", "Title", "Date", "Location"}, dao.getCalendarRows(), new JButton[]{actionButton("Add Event")}));
        tabs.addTab("Billing", tablePanel(new String[]{"Case #", "Invoice #", "Amount", "Status", "Issue", "Due"}, dao.getBillingRows(), new JButton[]{actionButton("Record Time"), actionButton("Add Invoice")}));
        tabs.addTab("Time", tablePanel(new String[]{"Case #", "Lawyer", "Activity", "Hours", "Rate", "Date", "Billable"}, dao.getTimeRows(), new JButton[]{actionButton("Record Time")}));
        tabs.addTab("Documents", tablePanel(new String[]{"Case #", "File", "Type", "Size", "Uploaded By", "Uploaded At"}, dao.getDocumentRows(), new JButton[]{actionButton("Add Document")}));
        tabs.addTab("Messages", tablePanel(new String[]{"Case #", "From", "To", "Subject", "Read", "Sent At"}, dao.getMessageRows(), new JButton[]{actionButton("Send Message")}));
        tabs.addTab("Reports", reportPanel());
    }

    private JPanel tablePanel(String[] columns, Vector<Vector<String>> rows, JButton[] buttons) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(Color.WHITE);
        Vector<String> columnVector = new Vector<>();
        for (String column : columns) {
            columnVector.add(column);
        }

        JTable table = new JTable(new DefaultTableModel(rows, columnVector));
        table.setRowHeight(26);
        table.setFont(new Font("Cambria", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Cambria", Font.BOLD, 14));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        if (buttons != null && buttons.length > 0) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.setBackground(Color.WHITE);
            for (JButton button : buttons) {
                buttonPanel.add(button);
            }
            panel.add(buttonPanel, BorderLayout.SOUTH);
        }
        return panel;
    }

    private JPanel reportPanel() {
        JPanel panel = tablePanel(new String[]{"Report", "Current Value"}, dao.getReportRows(), null);
        JTextArea note = new JTextArea("Next reports to add: aging receivables, lawyer workload, deadline forecast, trust ledger, pipeline conversion, and matter profitability.");
        note.setFont(UITheme.BODY_FONT);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setEditable(false);
        note.setBackground(Color.WHITE);
        panel.add(note, BorderLayout.SOUTH);
        return panel;
    }

    private JButton actionButton(String text) {
        JButton button = UITheme.primaryButton(text, 0, 0, 150, 32);
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == backButton) {
            previousScreen.setVisible(true);
            this.setVisible(false);
            return;
        }
        if (source == refreshButton) {
            loadTabs();
            return;
        }

        String command = event.getActionCommand();
        if ("Add Event".equals(command)) {
            addEventDialog();
        } else if ("Record Time".equals(command)) {
            addTimeDialog();
        } else if ("Add Invoice".equals(command)) {
            addInvoiceDialog();
        } else if ("Add Document".equals(command)) {
            addDocumentDialog();
        } else if ("Send Message".equals(command)) {
            addMessageDialog();
        }
    }

    private void addEventDialog() {
        JTextField caseId = new JTextField();
        JTextField type = new JTextField("Hearing");
        JTextField title = new JTextField();
        JTextField description = new JTextField();
        JTextField date = new JTextField("2026-07-18 09:00:00");
        JTextField location = new JTextField();
        if (confirm("Add hearing/deadline", new Object[]{"Case ID", caseId, "Type", type, "Title", title, "Description", description, "Date", date, "Location", location})) {
            boolean saved = dao.addCaseEvent(parseInt(caseId), type.getText(), title.getText(), description.getText(), date.getText(), location.getText());
            afterSave(saved);
        }
    }

    private void addTimeDialog() {
        JTextField caseId = new JTextField();
        JTextField lawyerId = new JTextField();
        JTextField activity = new JTextField();
        JTextField hours = new JTextField("1.0");
        JTextField rate = new JTextField("150.00");
        JTextField date = new JTextField("2026-07-18");
        JCheckBox billable = new JCheckBox("Billable", true);
        if (confirm("Record time", new Object[]{"Case ID", caseId, "Lawyer ID", lawyerId, "Activity", activity, "Hours", hours, "Rate", rate, "Date", date, billable})) {
            boolean saved = dao.addTimeEntry(parseInt(caseId), parseInt(lawyerId), activity.getText(), parseDouble(hours), parseDouble(rate), date.getText(), billable.isSelected());
            afterSave(saved);
        }
    }

    private void addInvoiceDialog() {
        JTextField caseId = new JTextField();
        JTextField number = new JTextField("INV-" + System.currentTimeMillis());
        JTextField amount = new JTextField("0.00");
        JComboBox<String> status = new JComboBox<>(new String[]{"Draft", "Sent", "Paid", "Overdue", "Cancelled"});
        JTextField issue = new JTextField("2026-07-18");
        JTextField due = new JTextField("2026-08-17");
        if (confirm("Add invoice", new Object[]{"Case ID", caseId, "Invoice #", number, "Amount", amount, "Status", status, "Issue Date", issue, "Due Date", due})) {
            boolean saved = dao.addInvoice(parseInt(caseId), number.getText(), parseDouble(amount), String.valueOf(status.getSelectedItem()), issue.getText(), due.getText());
            afterSave(saved);
        }
    }

    private void addDocumentDialog() {
        JTextField caseId = new JTextField();
        JTextField uploadedBy = new JTextField(currentUser != null ? String.valueOf(currentUser.getUserId()) : "");
        JTextField fileName = new JTextField();
        JTextField filePath = new JTextField();
        JTextField fileType = new JTextField("PDF");
        JTextField fileSize = new JTextField("0");
        if (confirm("Add document record", new Object[]{"Case ID", caseId, "Uploaded By User ID", uploadedBy, "File Name", fileName, "File Path", filePath, "File Type", fileType, "File Size", fileSize})) {
            boolean saved = dao.addDocumentRecord(parseInt(caseId), parseInt(uploadedBy), fileName.getText(), filePath.getText(), fileType.getText(), parseInt(fileSize));
            afterSave(saved);
        }
    }

    private void addMessageDialog() {
        JTextField caseId = new JTextField();
        JTextField sender = new JTextField(currentUser != null ? String.valueOf(currentUser.getUserId()) : "");
        JTextField recipient = new JTextField();
        JTextField subject = new JTextField();
        JTextArea body = new JTextArea(4, 24);
        if (confirm("Send case message", new Object[]{"Case ID", caseId, "Sender User ID", sender, "Recipient User ID", recipient, "Subject", subject, "Message", new JScrollPane(body)})) {
            boolean saved = dao.addMessage(parseInt(caseId), parseInt(sender), parseInt(recipient), subject.getText(), body.getText());
            afterSave(saved);
        }
    }

    private boolean confirm(String title, Object[] fields) {
        return JOptionPane.showConfirmDialog(this, fields, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }

    private void afterSave(boolean saved) {
        JOptionPane.showMessageDialog(this, saved ? "Saved successfully." : "Could not save. Check database setup and required IDs.");
        loadTabs();
    }

    private int parseInt(JTextField field) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double parseDouble(JTextField field) {
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
