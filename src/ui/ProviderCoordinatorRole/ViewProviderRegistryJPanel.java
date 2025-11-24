package ui.ProviderCoordinatorRole;

import business.Business;
import business.Enterprise;
import business.Network;
import business.Organization.ProviderRegistryOrganization;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewProviderRegistryJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private JTable providerTable;
    private DefaultTableModel tableModel;
    
    public ViewProviderRegistryJPanel(JPanel userProcessContainer, UserAccount account,
                                      Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(75, 0, 130));
        JLabel titleLabel = new JLabel("View Provider Registry");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel infoLabel = new JLabel("Complete registry of healthcare providers");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(infoLabel, BorderLayout.NORTH);
        
        String[] columns = {"Provider Name", "Enterprise", "Network", "Type", "Employees"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        providerTable = new JTable(tableModel);
        providerTable.setRowHeight(25);
        
        loadRegistry();
        mainPanel.add(new JScrollPane(providerTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(e -> viewDetails());
        JButton exportButton = new JButton("Export Registry");
        exportButton.addActionListener(e -> exportRegistry());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(viewButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadRegistry() {
        tableModel.setRowCount(0);
        
        for (Network network : business.getEcoSystem().getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseList()) {
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    if (org instanceof ProviderRegistryOrganization ||
                        org.getName().contains("Hospital") ||
                        org.getName().contains("Clinic") ||
                        org.getName().contains("Lab")) {
                        
                        tableModel.addRow(new Object[]{
                            org.getName(),
                            enterprise.getName(),
                            network.getName(),
                            org.getClass().getSimpleName().replace("Organization", ""),
                            org.getEmployeeDirectory().getEmployeeList().size()
                        });
                    }
                }
            }
        }
    }
    
    private void viewDetails() {
        int selectedRow = providerTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a provider", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String details = String.format("Provider Registry Details\n\nName: %s\nEnterprise: %s\nNetwork: %s\nType: %s\nEmployees: %s",
            tableModel.getValueAt(selectedRow, 0),
            tableModel.getValueAt(selectedRow, 1),
            tableModel.getValueAt(selectedRow, 2),
            tableModel.getValueAt(selectedRow, 3),
            tableModel.getValueAt(selectedRow, 4));
        
        JOptionPane.showMessageDialog(this, details, "Provider Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportRegistry() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String filename = "ProviderRegistry_" + sdf.format(new java.util.Date()) + ".csv";
        
        JOptionPane.showMessageDialog(this,
            "Provider registry would be exported to:\n" + filename + "\n\n" +
            String.format("Total providers: %d", tableModel.getRowCount()),
            "Export Registry",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
