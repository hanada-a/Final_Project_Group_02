package ui.ProviderCoordinatorRole;

import business.Business;
import business.Enterprise;
import business.Network;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.ComplianceAuditRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ScheduleComplianceAuditsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JTable providerTable;
    private DefaultTableModel tableModel;
    
    public ScheduleComplianceAuditsJPanel(JPanel userProcessContainer, UserAccount account,
                                          Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(75, 0, 130));
        JLabel titleLabel = new JLabel("Schedule Compliance Audits");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"Provider Name", "Type", "Last Audit", "Status", "Action"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        providerTable = new JTable(tableModel);
        providerTable.setRowHeight(25);
        
        loadProviders();
        mainPanel.add(new JScrollPane(providerTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton scheduleButton = new JButton("Schedule Audit");
        scheduleButton.setBackground(new Color(75, 0, 130));
        scheduleButton.setForeground(Color.WHITE);
        scheduleButton.addActionListener(e -> scheduleAudit());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadProviders());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(scheduleButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadProviders() {
        tableModel.setRowCount(0);
        
        for (Network network : business.getEcoSystem().getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseList()) {
                if (enterprise.getName().contains("Healthcare") || enterprise.getName().contains("Provider")) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        tableModel.addRow(new Object[]{
                            org.getName(),
                            org.getClass().getSimpleName().replace("Organization", ""),
                            "N/A",
                            "Pending",
                            "Not Scheduled"
                        });
                    }
                }
            }
        }
    }
    
    private void scheduleAudit() {
        int selectedRow = providerTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a provider", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String providerName = (String) tableModel.getValueAt(selectedRow, 0);
        
        String[] auditTypes = {"Full Compliance Audit", "Vaccine Storage Audit", "Record Keeping Audit", "Staff Training Audit"};
        String auditType = (String) JOptionPane.showInputDialog(
            this,
            "Select audit type for " + providerName + ":",
            "Schedule Audit",
            JOptionPane.QUESTION_MESSAGE,
            null,
            auditTypes,
            auditTypes[0]
        );
        
        if (auditType != null) {
            // Create audit request
            ComplianceAuditRequest request = new ComplianceAuditRequest();
            request.setSender(account);
            request.setStatus("Sent");
            request.setMessage(auditType + " scheduled for " + providerName);
            
            organization.getWorkQueue().getWorkRequestList().add(request);
            account.getWorkQueue().getWorkRequestList().add(request);
            
            tableModel.setValueAt("Scheduled", selectedRow, 3);
            tableModel.setValueAt(auditType, selectedRow, 4);
            
            JOptionPane.showMessageDialog(this,
                "Compliance audit scheduled successfully!\n\n" +
                "Provider: " + providerName + "\n" +
                "Audit Type: " + auditType + "\n\n" +
                "The provider will be notified.",
                "Audit Scheduled",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
