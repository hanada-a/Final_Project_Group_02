package ui.NursePractitionerRole;

import business.Business;
import business.Domain.Vaccine;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for nurses to view vaccine availability
 */
public class ViewVaccineAvailabilityJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private JTable vaccineTable;
    private DefaultTableModel tableModel;
    
    public ViewVaccineAvailabilityJPanel(JPanel userProcessContainer, UserAccount account,
                                        Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("View Vaccine Availability");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(new JLabel("Available vaccines at your facility:"));
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Vaccine Name", "Manufacturer", "Doses Required", "Storage Temp", "Expiration Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vaccineTable = new JTable(tableModel);
        vaccineTable.setRowHeight(25);
        
        // Load vaccines
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            String expirationDate = vaccine.getExpirationDate() != null ? 
                sdf.format(vaccine.getExpirationDate()) : "N/A";
            String status = vaccine.isActive() ? "Available" : "Inactive";
            
            tableModel.addRow(new Object[]{
                vaccine.getName(),
                vaccine.getManufacturer(),
                vaccine.getDosesRequired(),
                vaccine.getStorageTemperature() != null ? vaccine.getStorageTemperature() : "Standard",
                expirationDate,
                status
            });
        }
        
        mainPanel.add(new JScrollPane(vaccineTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            tableModel.setRowCount(0);
            for (Vaccine vaccine : business.getVaccineDirectory()) {
                String expirationDate = vaccine.getExpirationDate() != null ? 
                    sdf.format(vaccine.getExpirationDate()) : "N/A";
                String status = vaccine.isActive() ? "Available" : "Inactive";
                
                tableModel.addRow(new Object[]{
                    vaccine.getName(),
                    vaccine.getManufacturer(),
                    vaccine.getDosesRequired(),
                    vaccine.getStorageTemperature() != null ? vaccine.getStorageTemperature() : "Standard",
                    expirationDate,
                    status
                });
            }
            JOptionPane.showMessageDialog(this, "Vaccine inventory refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
