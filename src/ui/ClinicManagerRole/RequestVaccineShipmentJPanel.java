package ui.ClinicManagerRole;

import business.Business;
import business.Domain.Vaccine;
import business.Enterprise;
import business.Network;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.VaccineShipmentRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for clinic managers to request vaccine shipments
 */
public class RequestVaccineShipmentJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JTable requestTable;
    private DefaultTableModel tableModel;
    private JComboBox<Vaccine> vaccineComboBox;
    private JTextField quantityField;
    private JTextArea reasonArea;
    
    public RequestVaccineShipmentJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        JLabel titleLabel = new JLabel("Request Vaccine Shipment");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left: Requests table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Vaccine Shipment Requests"));
        
        String[] columns = {"Request Date", "Vaccine", "Quantity", "Status", "Response"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        requestTable = new JTable(tableModel);
        leftPanel.add(new JScrollPane(requestTable), BorderLayout.CENTER);
        
        // Right: Request form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("New Shipment Request"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Vaccine selection
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Vaccine:"), gbc);
        gbc.gridx = 1;
        vaccineComboBox = new JComboBox<>();
        populateVaccines();
        rightPanel.add(vaccineComboBox, gbc);
        
        // Quantity
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Quantity (doses):"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField("100", 20);
        rightPanel.add(quantityField, gbc);
        
        // Reason
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        rightPanel.add(new JLabel("Reason:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        reasonArea = new JTextArea(5, 20);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        reasonArea.setText("Regular inventory replenishment for upcoming vaccination campaign");
        rightPanel.add(new JScrollPane(reasonArea), gbc);
        
        // Buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Submit Request");
        submitButton.addActionListener(e -> submitRequest());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(450);
        
        add(splitPane, BorderLayout.CENTER);
        
        loadRequests();
    }
    
    private void populateVaccines() {
        vaccineComboBox.removeAllItems();
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            if (vaccine.isActive()) {
                vaccineComboBox.addItem(vaccine);
            }
        }
    }
    
    private void loadRequests() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : account.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof VaccineShipmentRequest) {
                VaccineShipmentRequest request = (VaccineShipmentRequest) obj;
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    request.getMessage() != null ? request.getMessage().split(" - ")[0] : "N/A",
                    request.getMessage() != null && request.getMessage().contains("Qty:") ? 
                        request.getMessage().split("Qty: ")[1].split(" ")[0] : "N/A",
                    request.getStatus(),
                    request.getStatus().equals("Completed") ? "Delivered" : "Processing"
                });
            }
        }
    }
    
    private void submitRequest() {
        try {
            Vaccine vaccine = (Vaccine) vaccineComboBox.getSelectedItem();
            if (vaccine == null) {
                JOptionPane.showMessageDialog(this, "Please select a vaccine", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String quantityStr = quantityField.getText().trim();
            if (quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter quantity", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String reason = reasonArea.getText().trim();
            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter reason for request", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Find state health department to send request to
            Organization targetOrg = null;
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    if (enterprise.getName().contains("State")) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            if (org.getName().contains("Vaccine") || org.getName().contains("Distribution")) {
                                targetOrg = org;
                                break;
                            }
                        }
                    }
                }
            }
            
            // Create shipment request
            VaccineShipmentRequest request = new VaccineShipmentRequest();
            request.setSender(account);
            request.setStatus("Pending");
            request.setMessage(vaccine.getName() + " - Qty: " + quantity + " doses - Reason: " + reason);
            
            account.getWorkQueue().getWorkRequestList().add(request);
            organization.getWorkQueue().getWorkRequestList().add(request);
            
            if (targetOrg != null) {
                targetOrg.getWorkQueue().getWorkRequestList().add(request);
            }
            
            // Update table
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            tableModel.insertRow(0, new Object[]{
                sdf.format(request.getRequestDate()),
                vaccine.getName(),
                quantity,
                request.getStatus(),
                "Pending approval"
            });
            
            JOptionPane.showMessageDialog(this,
                "Vaccine shipment request submitted successfully!\n" +
                "Vaccine: " + vaccine.getName() + "\n" +
                "Quantity: " + quantity + " doses\n" +
                "The request will be reviewed by the state health department.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            quantityField.setText("100");
            reasonArea.setText("Regular inventory replenishment for upcoming vaccination campaign");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error submitting request: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
