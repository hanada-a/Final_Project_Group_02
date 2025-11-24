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
        
        JOptionPane.showMessageDialog(this,
            String.format("Forwarding %d disease report(s) to CDC...\n\n" +
            "Reports will be added to CDC's Disease Surveillance Organization for national tracking.",
            selectedCount),
            "Forwarding to CDC",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Mark as forwarded
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                tableModel.setValueAt("Forwarded to CDC", i, 4);
                tableModel.setValueAt(Boolean.FALSE, i, 0);
            }
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
