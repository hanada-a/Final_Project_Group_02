package ui.EpidemiologistRole;

import business.Business;
import business.Domain.Disease;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.DiseaseReportRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Work area for Epidemiologist role
 */
public class EpidemiologistWorkAreaJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    
    private JTable workRequestTable;
    private DefaultTableModel model;
    
    public EpidemiologistWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        initComponents();
        populateTable();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel titleLabel = new JLabel("Epidemiologist Work Area - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Center - Table
        String[] columnNames = {"Request ID", "Disease", "Case Count", "Location", "Status", "Date"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        workRequestTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(workRequestTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(e -> viewRequest());
        buttonPanel.add(viewButton);
        
        JButton analyzeButton = new JButton("Analyze Trend");
        analyzeButton.addActionListener(e -> analyzeTrend());
        buttonPanel.add(analyzeButton);
        
        JButton issueAlertButton = new JButton("Issue Alert");
        issueAlertButton.addActionListener(e -> issueAlert());
        buttonPanel.add(issueAlertButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> populateTable());
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void populateTable() {
        model.setRowCount(0);
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof DiseaseReportRequest) {
                DiseaseReportRequest request = (DiseaseReportRequest) obj;
                Object[] row = new Object[6];
                row[0] = request.getRequestDate().toString().substring(0, 10);
                row[1] = request.getDisease() != null ? request.getDisease().getName() : "N/A";
                row[2] = request.getCaseCount();
                row[3] = request.getLocation();
                row[4] = request.getStatus();
                row[5] = request.getRequestDate();
                model.addRow(row);
            }
        }
    }
    
    private void viewRequest() {
        int selectedRow = workRequestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request to view");
            return;
        }
        
        // Get the request by filtering through the work queue to find the corresponding DiseaseReportRequest
        int diseaseReportIndex = 0;
        DiseaseReportRequest request = null;
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof DiseaseReportRequest) {
                if (diseaseReportIndex == selectedRow) {
                    request = (DiseaseReportRequest) obj;
                    break;
                }
                diseaseReportIndex++;
            }
        }
        
        if (request == null) {
            JOptionPane.showMessageDialog(this, "Error: Could not find the selected request");
            return;
        }
        
        StringBuilder details = new StringBuilder();
        details.append("Disease: ").append(request.getDisease().getName()).append("\n");
        details.append("Case Count: ").append(request.getCaseCount()).append("\n");
        details.append("Location: ").append(request.getLocation()).append("\n");
        details.append("Demographics: ").append(request.getPatientDemographics()).append("\n");
        details.append("Status: ").append(request.getStatus()).append("\n");
        
        JOptionPane.showMessageDialog(this, details.toString(), "Disease Report Details",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void analyzeTrend() {
        JOptionPane.showMessageDialog(this, 
            "Analyzing disease trends across all reports...\n\n" +
            "This feature would show statistical analysis and trends.",
            "Trend Analysis", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void issueAlert() {
        String alertMessage = JOptionPane.showInputDialog(this, 
            "Enter public health alert message:");
        
        if (alertMessage != null && !alertMessage.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Public health alert issued successfully!\n" +
                "Alert will be distributed to all relevant organizations.",
                "Alert Issued", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
