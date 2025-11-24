package ui.ProviderCoordinatorRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.VaccineShipmentRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProcessVaccineShipmentRequestsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private JTable requestTable;
    private DefaultTableModel tableModel;
    
    public ProcessVaccineShipmentRequestsJPanel(JPanel userProcessContainer, UserAccount account,
                                                Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(75, 0, 130));
        JLabel titleLabel = new JLabel("Process Vaccine Shipment Requests");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"Date", "Vaccine", "Quantity", "Clinic", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        requestTable = new JTable(tableModel);
        requestTable.setRowHeight(25);
        
        loadRequests();
        mainPanel.add(new JScrollPane(requestTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton approveButton = new JButton("Approve Request");
        approveButton.addActionListener(e -> approveRequest());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadRequests());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(approveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadRequests() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof VaccineShipmentRequest) {
                VaccineShipmentRequest request = (VaccineShipmentRequest) obj;
                String vaccineName = request.getVaccine() != null ? request.getVaccine().getName() : "N/A";
                String clinic = request.getSender() != null ? request.getSender().getUsername() : "N/A";
                
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    vaccineName,
                    request.getQuantity(),
                    clinic,
                    request.getStatus()
                });
            }
        }
    }
    
    private void approveRequest() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a request", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String status = (String) tableModel.getValueAt(selectedRow, 4);
        if ("Approved".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "This request is already approved", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        tableModel.setValueAt("Approved", selectedRow, 4);
        JOptionPane.showMessageDialog(this, 
            "Vaccine shipment request approved.\nDistribution coordinator will schedule the shipment.",
            "Approved", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
