package ui.HospitalAdminRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.ComplianceAuditRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for hospital admins to review compliance status
 */
public class ReviewComplianceStatusJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private JTable complianceTable;
    private DefaultTableModel tableModel;
    
    public ReviewComplianceStatusJPanel(JPanel userProcessContainer, UserAccount account,
                                        Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("Review Compliance Status");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        int total = 0;
        int pending = 0;
        int completed = 0;
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof ComplianceAuditRequest) {
                total++;
                String status = ((ComplianceAuditRequest) obj).getStatus();
                if ("Pending".equalsIgnoreCase(status) || "Sent".equalsIgnoreCase(status)) {
                    pending++;
                } else if ("Completed".equalsIgnoreCase(status)) {
                    completed++;
                }
            }
        }
        
        statsPanel.add(createStatCard("Total Audits", String.valueOf(total), new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Pending", String.valueOf(pending), new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Completed", String.valueOf(completed), new Color(46, 204, 113)));
        
        mainPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Date", "Audit Type", "Status", "Requested By", "Notes"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        complianceTable = new JTable(tableModel);
        complianceTable.setRowHeight(25);
        
        loadComplianceAudits();
        
        mainPanel.add(new JScrollPane(complianceTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadComplianceAudits();
            JOptionPane.showMessageDialog(this, "Compliance data refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton completeButton = new JButton("Mark Complete");
        completeButton.addActionListener(e -> markComplete());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void loadComplianceAudits() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof ComplianceAuditRequest) {
                ComplianceAuditRequest request = (ComplianceAuditRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "Compliance audit";
                String auditType = message.length() > 30 ? message.substring(0, 27) + "..." : message;
                
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    auditType,
                    request.getStatus(),
                    request.getSender() != null ? request.getSender().getUsername() : "N/A",
                    request.getResolveDate() != null ? "Resolved on " + sdf.format(request.getResolveDate()) : "-"
                });
            }
        }
    }
    
    private void markComplete() {
        int selectedRow = complianceTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an audit to complete", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String currentStatus = (String) tableModel.getValueAt(selectedRow, 2);
        if ("Completed".equalsIgnoreCase(currentStatus)) {
            JOptionPane.showMessageDialog(this, "This audit is already completed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Mark this audit as completed?",
            "Confirm Completion",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Completed", selectedRow, 2);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            tableModel.setValueAt("Resolved on " + sdf.format(new java.util.Date()), selectedRow, 4);
            JOptionPane.showMessageDialog(this, 
                "Audit marked as completed", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
