package ui.DistributionCoordinatorRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.VaccineAllocationRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Work area for Distribution Coordinator role
 */
public class DistributionCoordinatorWorkAreaJPanel extends JPanel {
    
    private JTable requestTable;
    private DefaultTableModel model;
    private Organization organization;
    
    public DistributionCoordinatorWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                                 Organization organization, Business business) {
        this.organization = organization;
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 139, 34));
        JLabel titleLabel = new JLabel("Distribution Coordinator - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"Request ID", "Vaccine", "Quantity", "Priority", "Status", "Date"};
        model = new DefaultTableModel(columnNames, 0);
        requestTable = new JTable(model);
        add(new JScrollPane(requestTable), BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton approveBtn = new JButton("Approve Request");
        JButton scheduleShipmentBtn = new JButton("Schedule Shipment");
        JButton viewInventoryBtn = new JButton("View Vaccine Inventory");
        
        buttonPanel.add(approveBtn);
        buttonPanel.add(scheduleShipmentBtn);
        buttonPanel.add(viewInventoryBtn);
        add(buttonPanel, BorderLayout.SOUTH);
        
        populateTable();
    }
    
    private void populateTable() {
        model.setRowCount(0);
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof VaccineAllocationRequest) {
                VaccineAllocationRequest req = (VaccineAllocationRequest) obj;
                Object[] row = {
                    req.getRequestDate().toString().substring(0, 10),
                    req.getVaccine() != null ? req.getVaccine().getName() : "N/A",
                    req.getQuantityRequested(),
                    req.getPriority(),
                    req.getStatus(),
                    req.getRequestDate()
                };
                model.addRow(row);
            }
        }
    }
}
