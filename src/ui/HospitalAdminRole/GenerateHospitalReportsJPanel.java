package ui.HospitalAdminRole;

import business.Business;
import business.Domain.Vaccine;
import business.Employee.Employee;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.WorkRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Panel for hospital admins to generate hospital reports
 */
public class GenerateHospitalReportsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private Business business;
    
    public GenerateHospitalReportsJPanel(JPanel userProcessContainer, UserAccount account,
                                         Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("Generate Hospital Reports");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Overview", createOverviewPanel());
        tabbedPane.addTab("Staff", createStaffPanel());
        tabbedPane.addTab("Work Requests", createWorkRequestsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton exportButton = new JButton("Export Report");
        exportButton.addActionListener(e -> exportReport());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(exportButton);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header info
        JPanel headerInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        headerInfo.add(new JLabel("Hospital: " + organization.getName()));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        headerInfo.add(new JLabel("Report Date: " + sdf.format(new java.util.Date())));
        panel.add(headerInfo, BorderLayout.NORTH);
        
        // Stats grid
        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Calculate stats
        int totalStaff = organization.getEmployeeDirectory().getEmployeeList().size();
        int activeStaff = 0;
        for (Employee emp : organization.getEmployeeDirectory().getEmployeeList()) {
            if (emp.isActive()) activeStaff++;
        }
        
        int totalRequests = organization.getWorkQueue().getWorkRequestList().size();
        int pendingRequests = 0;
        int completedRequests = 0;
        for (WorkRequest wr : organization.getWorkQueue().getWorkRequestList()) {
            String status = wr.getStatus();
            if ("Pending".equalsIgnoreCase(status) || "Sent".equalsIgnoreCase(status)) {
                pendingRequests++;
            } else if ("Completed".equalsIgnoreCase(status)) {
                completedRequests++;
            }
        }
        
        int totalVaccineTypes = business.getVaccineDirectory().size();
        
        int userAccounts = organization.getUserAccountDirectory().getUserAccountList().size();
        
        // Add stat cards
        statsPanel.add(createStatCard("Total Staff", String.valueOf(totalStaff), new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Vaccine Types", String.valueOf(totalVaccineTypes), new Color(46, 204, 113)));
        statsPanel.add(createStatCard("User Accounts", String.valueOf(userAccounts), new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Total Requests", String.valueOf(totalRequests), new Color(241, 196, 15)));
        statsPanel.add(createStatCard("Pending", String.valueOf(pendingRequests), new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Completed", String.valueOf(completedRequests), new Color(39, 174, 96)));
        statsPanel.add(createStatCard("Success Rate", 
            totalRequests > 0 ? String.format("%.1f%%", (completedRequests * 100.0 / totalRequests)) : "N/A",
            new Color(142, 68, 173)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== HOSPITAL STAFF REPORT ===\n\n");
        sb.append(String.format("Organization: %s\n", organization.getName()));
        sb.append(String.format("Total Staff: %d\n\n", 
            organization.getEmployeeDirectory().getEmployeeList().size()));
        
        sb.append("Staff Details:\n");
        sb.append("-".repeat(80)).append("\n");
        
        for (Employee emp : organization.getEmployeeDirectory().getEmployeeList()) {
            String role = "Staff";
            for (UserAccount ua : organization.getUserAccountDirectory().getUserAccountList()) {
                if (ua.getEmployee() != null && ua.getEmployee().equals(emp)) {
                    role = ua.getRole() != null ? ua.getRole().toString() : "Staff";
                    break;
                }
            }
            
            sb.append(String.format("ID: %s | Name: %-25s | Role: %-20s | Status: %s\n",
                emp.getEmployeeID(),
                emp.getName(),
                role,
                emp.isActive() ? "Active" : "Inactive"
            ));
        }
        
        textArea.setText(sb.toString());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createWorkRequestsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("=== WORK REQUESTS REPORT ===\n\n");
        sb.append(String.format("Organization: %s\n", organization.getName()));
        sb.append(String.format("Total Requests: %d\n\n", 
            organization.getWorkQueue().getWorkRequestList().size()));
        
        sb.append("Request Details:\n");
        sb.append("-".repeat(80)).append("\n");
        
        for (WorkRequest wr : organization.getWorkQueue().getWorkRequestList()) {
            sb.append(String.format("Date: %s | Type: %-25s | Status: %-10s | Sender: %s\n",
                sdf.format(wr.getRequestDate()),
                wr.getClass().getSimpleName(),
                wr.getStatus(),
                wr.getSender() != null ? wr.getSender().getUsername() : "N/A"
            ));
        }
        
        textArea.setText(sb.toString());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private void exportReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String timestamp = sdf.format(new java.util.Date());
        String filename = "HospitalReport_" + organization.getName().replaceAll("\\s+", "_") + 
                         "_" + timestamp + ".txt";
        
        JOptionPane.showMessageDialog(this,
            "Report would be exported to:\n" + filename + "\n\n" +
            "Note: Actual file export functionality can be implemented using Java FileWriter.",
            "Export Report",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
