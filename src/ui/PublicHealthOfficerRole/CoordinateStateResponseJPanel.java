package ui.PublicHealthOfficerRole;

import business.Business;
import business.Enterprise;
import business.Network;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CoordinateStateResponseJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private JTable orgTable;
    private DefaultTableModel tableModel;
    
    public CoordinateStateResponseJPanel(JPanel userProcessContainer, UserAccount account,
                                         Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 20, 60));
        JLabel titleLabel = new JLabel("Coordinate State Response");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel infoLabel = new JLabel("Coordinate public health response across state organizations");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(infoLabel, BorderLayout.NORTH);
        
        String[] columns = {"Organization", "Type", "Work Requests", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        orgTable = new JTable(tableModel);
        orgTable.setRowHeight(25);
        
        loadOrganizations();
        mainPanel.add(new JScrollPane(orgTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton coordinateButton = new JButton("Send Coordination Request");
        coordinateButton.addActionListener(e -> sendCoordinationRequest());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadOrganizations());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(coordinateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadOrganizations() {
        tableModel.setRowCount(0);
        
        for (Network network : business.getEcoSystem().getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseList()) {
                if (enterprise.getName().contains("State")) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        tableModel.addRow(new Object[]{
                            org.getName(),
                            org.getClass().getSimpleName().replace("Organization", ""),
                            org.getWorkQueue().getWorkRequestList().size(),
                            "Active"
                        });
                    }
                }
            }
        }
    }
    
    private void sendCoordinationRequest() {
        int selectedRow = orgTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an organization", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String orgName = (String) tableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this, 
            "Coordination request sent to " + orgName + "\n\nThe organization will be notified to coordinate response efforts.",
            "Request Sent", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
