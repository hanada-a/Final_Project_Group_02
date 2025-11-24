package ui.PublicHealthOfficerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.ComplianceAuditRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewComplianceReportsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private JTable complianceTable;
    private DefaultTableModel tableModel;
    
    public ViewComplianceReportsJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 20, 60));
        JLabel titleLabel = new JLabel("View Compliance Reports");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        int total = 0, pending = 0, completed = 0;
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof ComplianceAuditRequest) {
                total++;
                String status = ((ComplianceAuditRequest) obj).getStatus();
                if ("Pending".equalsIgnoreCase(status) || "Sent".equalsIgnoreCase(status)) pending++;
                else if ("Completed".equalsIgnoreCase(status)) completed++;
            }
        }
        
        statsPanel.add(createStatCard("Total Audits", String.valueOf(total), new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Pending", String.valueOf(pending), new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Completed", String.valueOf(completed), new Color(46, 204, 113)));
        
        mainPanel.add(statsPanel, BorderLayout.NORTH);
        
        String[] columns = {"Date", "Audit Type", "Provider", "Status", "Findings"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        complianceTable = new JTable(tableModel);
        complianceTable.setRowHeight(25);
        
        loadReports();
        mainPanel.add(new JScrollPane(complianceTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton reviewButton = new JButton("Review Audit");
        reviewButton.addActionListener(e -> reviewAudit());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadReports());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(reviewButton);
        buttonPanel.add(refreshButton);
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
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void loadReports() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof ComplianceAuditRequest) {
                ComplianceAuditRequest request = (ComplianceAuditRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "Compliance audit";
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    "Provider Compliance",
                    request.getSender() != null ? request.getSender().getUsername() : "N/A",
                    request.getStatus(),
                    request.getResolveDate() != null ? "Completed" : "In Progress"
                });
            }
        }
    }
    
    private void reviewAudit() {
        int selectedRow = complianceTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an audit to review", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String details = String.format("Compliance Audit Details\n\nDate: %s\nType: %s\nProvider: %s\nStatus: %s\nFindings: %s",
            tableModel.getValueAt(selectedRow, 0),
            tableModel.getValueAt(selectedRow, 1),
            tableModel.getValueAt(selectedRow, 2),
            tableModel.getValueAt(selectedRow, 3),
            tableModel.getValueAt(selectedRow, 4));
        
        JOptionPane.showMessageDialog(this, details, "Audit Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
