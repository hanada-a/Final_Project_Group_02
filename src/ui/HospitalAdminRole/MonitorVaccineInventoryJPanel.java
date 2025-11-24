package ui.HospitalAdminRole;

import business.Business;
import business.Domain.Vaccine;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for hospital admins to monitor vaccine inventory
 */
public class MonitorVaccineInventoryJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    
    public MonitorVaccineInventoryJPanel(JPanel userProcessContainer, UserAccount account,
                                         Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("Monitor Vaccine Inventory");
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
        
        int totalVaccineTypes = business.getVaccineDirectory().size();
        
        statsPanel.add(createStatCard("Total Vaccine Types", String.valueOf(totalVaccineTypes), new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Tracked Diseases", String.valueOf(business.getDiseaseDirectory().size()), new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Organizations", String.valueOf(organization.getEmployeeDirectory().getEmployeeList().size()), new Color(231, 76, 60)));
        
        mainPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Vaccine Name", "Manufacturer", "Lot Number", "Status", "Doses Required", "Storage Temp"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        inventoryTable = new JTable(tableModel);
        inventoryTable.setRowHeight(25);
        
        loadInventory();
        
        mainPanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadInventory();
            updateStats();
            JOptionPane.showMessageDialog(this, "Inventory refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
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
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void loadInventory() {
        tableModel.setRowCount(0);
        
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            String status = vaccine.isActive() ? "Active" : "Inactive";
            
            tableModel.addRow(new Object[]{
                vaccine.getName(),
                vaccine.getManufacturer(),
                vaccine.getLotNumber() != null ? vaccine.getLotNumber() : "N/A",
                status,
                vaccine.getDosesRequired(),
                vaccine.getStorageTemperature() != null ? vaccine.getStorageTemperature() : "N/A"
            });
        }
    }
    
    private void updateStats() {
        loadInventory();
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
