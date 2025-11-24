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
 * Panel for data analysts to process pending requests
 */
public class ProcessPendingRequestsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private JTable requestTable;
    private DefaultTableModel tableModel;
    
    public ProcessPendingRequestsJPanel(JPanel userProcessContainer, UserAccount account,
                                        Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel titleLabel = new JLabel("Process Pending Requests");
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
        int processing = 0;
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof HealthDataAnalysisRequest) {
                total++;
                String status = ((HealthDataAnalysisRequest) obj).getStatus();
                if ("Pending".equalsIgnoreCase(status) || "Sent".equalsIgnoreCase(status)) {
                    pending++;
                } else if ("Processing".equalsIgnoreCase(status)) {
                    processing++;
                }
            }
        }
        
        statsPanel.add(createStatCard("Total", String.valueOf(total), new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Pending", String.valueOf(pending), new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Processing", String.valueOf(processing), new Color(155, 89, 182)));
        
        mainPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        
        // Left - Table
        String[] columns = {"Date", "Description", "Status", "Priority"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        requestTable = new JTable(tableModel);
        requestTable.setRowHeight(25);
        
        loadRequests();
        
        splitPane.setLeftComponent(new JScrollPane(requestTable));
        
        // Right - Action panel
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBorder(BorderFactory.createTitledBorder("Request Actions"));
        
        JPanel actionButtonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        actionButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton startButton = new JButton("Start Processing");
        startButton.addActionListener(e -> startProcessing());
        
        JButton completeButton = new JButton("Mark Complete");
        completeButton.addActionListener(e -> markComplete());
        
        JButton assignPriorityButton = new JButton("Assign Priority");
        assignPriorityButton.addActionListener(e -> assignPriority());
        
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewDetails());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadRequests());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        actionButtonPanel.add(startButton);
        actionButtonPanel.add(completeButton);
        actionButtonPanel.add(assignPriorityButton);
        actionButtonPanel.add(viewDetailsButton);
        actionButtonPanel.add(refreshButton);
        actionButtonPanel.add(backButton);
        
        actionPanel.add(actionButtonPanel, BorderLayout.CENTER);
        splitPane.setRightComponent(actionPanel);
        
        mainPanel.add(splitPane, BorderLayout.CENTER);
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
    
    private void loadRequests() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof HealthDataAnalysisRequest) {
                HealthDataAnalysisRequest request = (HealthDataAnalysisRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "Analysis request";
                String description = message.length() > 30 ? message.substring(0, 27) + "..." : message;
                
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    description,
                    request.getStatus(),
                    "Normal"
                });
            }
        }
    }
    
    private void startProcessing() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String status = (String) tableModel.getValueAt(selectedRow, 2);
        if ("Processing".equalsIgnoreCase(status) || "Completed".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "This request is already being processed or completed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        tableModel.setValueAt("Processing", selectedRow, 2);
        JOptionPane.showMessageDialog(this, 
            "Request is now being processed.\nYou can track progress in the Analytics Dashboard.", 
            "Processing Started", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void markComplete() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String status = (String) tableModel.getValueAt(selectedRow, 2);
        if ("Completed".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "This request is already completed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        tableModel.setValueAt("Completed", selectedRow, 2);
        JOptionPane.showMessageDialog(this, 
            "Request marked as completed.\nResults are now available for reporting.", 
            "Completed", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void assignPriority() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] priorities = {"Low", "Normal", "High", "Urgent"};
        String priority = (String) JOptionPane.showInputDialog(
            this,
            "Select priority level:",
            "Assign Priority",
            JOptionPane.QUESTION_MESSAGE,
            null,
            priorities,
            priorities[1]
        );
        
        if (priority != null) {
            tableModel.setValueAt(priority, selectedRow, 3);
            JOptionPane.showMessageDialog(this, 
                "Priority set to: " + priority, 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void viewDetails() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String date = (String) tableModel.getValueAt(selectedRow, 0);
        String desc = (String) tableModel.getValueAt(selectedRow, 1);
        String status = (String) tableModel.getValueAt(selectedRow, 2);
        String priority = (String) tableModel.getValueAt(selectedRow, 3);
        
        String details = String.format(
            "Request Details\n\n" +
            "Date: %s\n" +
            "Description: %s\n" +
            "Status: %s\n" +
            "Priority: %s\n\n" +
            "Use the action buttons to process this request.",
            date, desc, status, priority
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
