package ui.ClinicManagerRole;

import business.Business;
import business.Domain.Vaccine;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for clinic managers to view vaccine inventory
 */
public class ViewVaccineInventoryJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private JTable vaccineTable;
    private DefaultTableModel tableModel;
    private Business business;
    
    public ViewVaccineInventoryJPanel(JPanel userProcessContainer, UserAccount account,
                                     Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        JLabel titleLabel = new JLabel("View Vaccine Inventory");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel titleInfoLabel = new JLabel("Available Vaccines at Clinic");
        titleInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(titleInfoLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        JLabel descLabel = new JLabel("This inventory shows all vaccines available for administration at your clinic");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoPanel.add(descLabel);
        infoPanel.add(Box.createVerticalStrut(10));
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
        
        loadInventory();
        
        mainPanel.add(new JScrollPane(vaccineTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh Inventory");
        refreshButton.addActionListener(e -> {
            loadInventory();
            JOptionPane.showMessageDialog(this, "Inventory refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton requestButton = new JButton("Request Shipment");
        requestButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Please use 'Request Vaccine Shipment' from the main menu to order vaccines", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(requestButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadInventory() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            String expirationDate = vaccine.getExpirationDate() != null ? 
                sdf.format(vaccine.getExpirationDate()) : "N/A";
            String status = vaccine.isActive() ? "In Stock" : "Out of Stock";
            
            // Color code by expiration
            tableModel.addRow(new Object[]{
                vaccine.getName(),
                vaccine.getManufacturer(),
                vaccine.getDosesRequired() + " dose(s)",
                vaccine.getStorageTemperature() != null ? vaccine.getStorageTemperature() : "Standard (2-8°C)",
                expirationDate,
                status
            });
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
