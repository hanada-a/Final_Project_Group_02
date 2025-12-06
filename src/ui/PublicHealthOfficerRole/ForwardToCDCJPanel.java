package ui.PublicHealthOfficerRole;

import business.Business;
import business.Enterprise;
import business.Network;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.DiseaseReportRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ForwardToCDCJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private Business business;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    
    public ForwardToCDCJPanel(JPanel userProcessContainer, UserAccount account,
                              Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 20, 60));
        JLabel titleLabel = new JLabel("Forward Reports to CDC");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel infoLabel = new JLabel("Select disease reports to forward to CDC for national tracking");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(infoLabel, BorderLayout.NORTH);
        
        String[] columns = {"Select", "Disease", "Cases", "Location", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };
        reportTable = new JTable(tableModel);
        reportTable.setRowHeight(25);
        
        loadReports();
        mainPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton forwardButton = new JButton("Forward Selected to CDC");
        forwardButton.setBackground(new Color(220, 20, 60));
        forwardButton.setForeground(Color.WHITE);
        forwardButton.addActionListener(e -> forwardToCDC());
        JButton selectAllButton = new JButton("Select All");
        selectAllButton.addActionListener(e -> selectAll());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(selectAllButton);
        buttonPanel.add(forwardButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadReports() {
        tableModel.setRowCount(0);
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof DiseaseReportRequest) {
                DiseaseReportRequest request = (DiseaseReportRequest) obj;
                tableModel.addRow(new Object[]{
                    Boolean.FALSE,
                    request.getDisease() != null ? request.getDisease().getName() : "N/A",
                    request.getCaseCount(),
                    request.getLocation(),
                    request.getStatus()
                });
            }
        }
    }
    
    private void selectAll() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(Boolean.TRUE, i, 0);
        }
    }
    
    private void forwardToCDC() {
        int selectedCount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                selectedCount++;
            }
        }
        
        if (selectedCount == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one report to forward", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Find CDC's Disease Surveillance Organization
        Organization cdcDiseaseSurv = null;
        for (Network network : business.getEcoSystem().getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseList()) {
                if (enterprise.getName().contains("Centers for Disease Control") || 
                    enterprise.getName().contains("CDC")) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        if (org.getName().contains("Disease Surveillance")) {
                            cdcDiseaseSurv = org;
                            break;
                        }
                    }
                    if (cdcDiseaseSurv != null) break;
                }
            }
            if (cdcDiseaseSurv != null) break;
        }
        
        if (cdcDiseaseSurv == null) {
            JOptionPane.showMessageDialog(this, 
                "Error: CDC Disease Surveillance Organization not found", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Forward selected reports to CDC
        int forwardedCount = 0;
        int index = 0;
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof DiseaseReportRequest) {
                if (index < tableModel.getRowCount() && (Boolean) tableModel.getValueAt(index, 0)) {
                    DiseaseReportRequest request = (DiseaseReportRequest) obj;
                    
                    // Create a copy of the request for CDC
                    DiseaseReportRequest cdcRequest = new DiseaseReportRequest();
                    cdcRequest.setDisease(request.getDisease());
                    cdcRequest.setCaseCount(request.getCaseCount());
                    cdcRequest.setLocation(request.getLocation());
                    cdcRequest.setPatientDemographics(request.getPatientDemographics());
                    cdcRequest.setOnsetDate(request.getOnsetDate());
                    cdcRequest.setSender(request.getSender());
                    cdcRequest.setReceiver(cdcDiseaseSurv.getUserAccountDirectory().getUserAccountList().get(0));
                    cdcRequest.setMessage("Forwarded from State: " + request.getMessage());
                    cdcRequest.setStatus("Pending");
                    
                    // Add to CDC's work queue
                    cdcDiseaseSurv.getWorkQueue().getWorkRequestList().add(cdcRequest);
                    
                    // Update original request status
                    request.setStatus("Forwarded to CDC");
                    
                    forwardedCount++;
                }
                index++;
            }
        }
        
        JOptionPane.showMessageDialog(this,
            String.format("Successfully forwarded %d disease report(s) to CDC!\n\n" +
            "Reports have been added to CDC's Disease Surveillance Organization for national tracking.",
            forwardedCount),
            "Forwarding Complete",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Reload the table to reflect updated statuses
        loadReports();
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
