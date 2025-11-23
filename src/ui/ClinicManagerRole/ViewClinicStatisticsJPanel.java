package ui.ClinicManagerRole;

import business.Business;
import business.Employee.Employee;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.WorkRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for clinic managers to view clinic statistics
 */
public class ViewClinicStatisticsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private UserAccount account;
    
    public ViewClinicStatisticsJPanel(JPanel userProcessContainer, UserAccount account,
                                     Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.account = account;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        JLabel titleLabel = new JLabel("Clinic Statistics - " + organization.getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Overview tab
        tabbedPane.addTab("Overview", createOverviewPanel());
        
        // Staff tab
        tabbedPane.addTab("Staff", createStaffPanel());
        
        // Work Requests tab
        tabbedPane.addTab("Work Requests", createWorkRequestsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Statistics refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Calculate statistics
        int totalStaff = organization.getEmployeeDirectory().getEmployeeList().size();
        int totalAccounts = organization.getUserAccountDirectory().getUserAccountList().size();
        int totalRequests = organization.getWorkQueue().getWorkRequestList().size();
        
        int pendingRequests = 0;
        int completedRequests = 0;
        for (WorkRequest wr : organization.getWorkQueue().getWorkRequestList()) {
            if ("Pending".equals(wr.getStatus())) pendingRequests++;
            if ("Completed".equals(wr.getStatus())) completedRequests++;
        }
        
        // Create stat cards
        panel.add(createStatCard("Total Staff Members", String.valueOf(totalStaff), new Color(52, 152, 219)));
        panel.add(createStatCard("Active User Accounts", String.valueOf(totalAccounts), new Color(46, 204, 113)));
        panel.add(createStatCard("Total Work Requests", String.valueOf(totalRequests), new Color(155, 89, 182)));
        panel.add(createStatCard("Pending Requests", String.valueOf(pendingRequests), new Color(230, 126, 34)));
        panel.add(createStatCard("Completed Requests", String.valueOf(completedRequests), new Color(26, 188, 156)));
        panel.add(createStatCard("Organization Type", "Clinic", new Color(52, 73, 94)));
        panel.add(createStatCard("Manager", account.getEmployee().getName(), new Color(241, 196, 15)));
        panel.add(createStatCard("Status", "Active", new Color(39, 174, 96)));
        
        return panel;
    }
    
    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLabel.setForeground(Color.WHITE);
        labelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(labelLabel);
        
        return card;
    }
    
    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Name", "Email", "Phone", "Role"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        
        for (Employee emp : organization.getEmployeeDirectory().getEmployeeList()) {
            String role = "Staff";
            for (UserAccount ua : organization.getUserAccountDirectory().getUserAccountList()) {
                if (ua.getEmployee() != null && ua.getEmployee().equals(emp)) {
                    role = ua.getRole() != null ? ua.getRole().toString() : "Staff";
                    break;
                }
            }
            model.addRow(new Object[]{emp.getName(), emp.getEmail(), emp.getPhoneNumber(), role});
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createWorkRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Type", "Status", "Sender", "Message"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        
        for (WorkRequest wr : organization.getWorkQueue().getWorkRequestList()) {
            model.addRow(new Object[]{
                wr.getClass().getSimpleName().replace("Request", ""),
                wr.getStatus(),
                wr.getSender() != null ? wr.getSender().getUsername() : "N/A",
                wr.getMessage() != null ? 
                    (wr.getMessage().length() > 50 ? wr.getMessage().substring(0, 47) + "..." : wr.getMessage()) : ""
            });
        }
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
