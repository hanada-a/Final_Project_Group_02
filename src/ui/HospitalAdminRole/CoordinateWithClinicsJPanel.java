package ui.HospitalAdminRole;

import business.Business;
import business.Enterprise;
import business.Network;
import business.Organization.ClinicOrganization;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for hospital admins to coordinate with clinics
 */
public class CoordinateWithClinicsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private JTable clinicTable;
    private DefaultTableModel tableModel;
    
    public CoordinateWithClinicsJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("Coordinate with Clinics");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info label
        JLabel infoLabel = new JLabel("Registered clinics in the healthcare network");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(infoLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Clinic Name", "Enterprise", "Network", "Status", "Work Requests"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clinicTable = new JTable(tableModel);
        clinicTable.setRowHeight(25);
        
        loadClinics();
        
        mainPanel.add(new JScrollPane(clinicTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadClinics();
            JOptionPane.showMessageDialog(this, "Clinic list refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewClinicDetails());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadClinics() {
        tableModel.setRowCount(0);
        
        try {
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        if (org instanceof ClinicOrganization) {
                            int workRequests = org.getWorkQueue().getWorkRequestList().size();
                            tableModel.addRow(new Object[]{
                                org.getName(),
                                enterprise.getName(),
                                network.getName(),
                                "Active",
                                workRequests
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading clinics: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewClinicDetails() {
        int selectedRow = clinicTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a clinic", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String clinicName = (String) tableModel.getValueAt(selectedRow, 0);
        String enterprise = (String) tableModel.getValueAt(selectedRow, 1);
        String network = (String) tableModel.getValueAt(selectedRow, 2);
        int workRequests = (Integer) tableModel.getValueAt(selectedRow, 4);
        
        String details = String.format(
            "Clinic Details\n\n" +
            "Name: %s\n" +
            "Enterprise: %s\n" +
            "Network: %s\n" +
            "Status: Active\n" +
            "Pending Work Requests: %d\n\n" +
            "Note: Contact clinic manager for coordination",
            clinicName, enterprise, network, workRequests
        );
        
        JOptionPane.showMessageDialog(this, details, 
            "Clinic Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
