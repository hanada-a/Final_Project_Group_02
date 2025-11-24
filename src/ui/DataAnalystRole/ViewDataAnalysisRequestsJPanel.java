package ui.DataAnalystRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.HealthDataAnalysisRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for data analysts to view health data analysis requests
 */
public class ViewDataAnalysisRequestsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private JTable requestTable;
    private DefaultTableModel tableModel;
    
    public ViewDataAnalysisRequestsJPanel(JPanel userProcessContainer, UserAccount account,
                                          Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel titleLabel = new JLabel("View Data Analysis Requests");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Table
        String[] columns = {"Date", "Analysis Type", "Description", "Status", "Requested By"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        requestTable = new JTable(tableModel);
        requestTable.setRowHeight(25);
        
        loadRequests();
        
        mainPanel.add(new JScrollPane(requestTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton processButton = new JButton("Process Request");
        processButton.addActionListener(e -> processRequest());
        
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewDetails());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadRequests());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(processButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadRequests() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof HealthDataAnalysisRequest) {
                HealthDataAnalysisRequest request = (HealthDataAnalysisRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "Data analysis request";
                String description = message.length() > 40 ? message.substring(0, 37) + "..." : message;
                
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    "Statistical Analysis",
                    description,
                    request.getStatus(),
                    request.getSender() != null ? request.getSender().getUsername() : "N/A"
                });
            }
        }
    }
    
    private void processRequest() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request to process", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String status = (String) tableModel.getValueAt(selectedRow, 3);
        if ("Completed".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "This request is already completed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        tableModel.setValueAt("Processing", selectedRow, 3);
        JOptionPane.showMessageDialog(this, 
            "Data analysis request is now being processed.\n" +
            "Results will be available in the analytics dashboard.", 
            "Processing", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void viewDetails() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request to view", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String date = (String) tableModel.getValueAt(selectedRow, 0);
        String type = (String) tableModel.getValueAt(selectedRow, 1);
        String desc = (String) tableModel.getValueAt(selectedRow, 2);
        String status = (String) tableModel.getValueAt(selectedRow, 3);
        String sender = (String) tableModel.getValueAt(selectedRow, 4);
        
        String details = String.format(
            "Request Details\n\n" +
            "Date: %s\n" +
            "Analysis Type: %s\n" +
            "Description: %s\n" +
            "Status: %s\n" +
            "Requested By: %s\n\n" +
            "Note: Process this request to generate insights",
            date, type, desc, status, sender
        );
        
        JOptionPane.showMessageDialog(this, details, 
            "Request Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
