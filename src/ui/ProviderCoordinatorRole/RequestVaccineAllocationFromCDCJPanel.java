package ui.ProviderCoordinatorRole;

import business.Business;
import business.Domain.Vaccine;
import business.Enterprise;
import business.Network;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.VaccineAllocationRequest;
import java.awt.*;
import javax.swing.*;

public class RequestVaccineAllocationFromCDCJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JComboBox<String> vaccineCombo;
    private JTextField quantityField;
    private JTextArea justificationArea;
    
    public RequestVaccineAllocationFromCDCJPanel(JPanel userProcessContainer, UserAccount account,
                                                 Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(75, 0, 130));
        JLabel titleLabel = new JLabel("Request Vaccine Allocation from CDC");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Allocation Request Details"));
        
        formPanel.add(new JLabel("Vaccine:"));
        vaccineCombo = new JComboBox<>();
        vaccineCombo.addItem("-- Select Vaccine --");
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            vaccineCombo.addItem(vaccine.getName());
        }
        formPanel.add(vaccineCombo);
        
        formPanel.add(new JLabel("Quantity Requested:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);
        
        formPanel.add(new JLabel("Priority:"));
        JComboBox<String> priorityCombo = new JComboBox<>(new String[]{"Normal", "High", "Urgent"});
        formPanel.add(priorityCombo);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // Justification panel
        JPanel justificationPanel = new JPanel(new BorderLayout());
        justificationPanel.setBorder(BorderFactory.createTitledBorder("Request Justification"));
        
        justificationArea = new JTextArea(8, 40);
        justificationArea.setLineWrap(true);
        justificationArea.setWrapStyleWord(true);
        justificationArea.setFont(new Font("Arial", Font.PLAIN, 12));
        justificationPanel.add(new JScrollPane(justificationArea), BorderLayout.CENTER);
        
        mainPanel.add(justificationPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton submitButton = new JButton("Submit Request");
        submitButton.setBackground(new Color(75, 0, 130));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.addActionListener(e -> submitRequest(priorityCombo));
        
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearForm(priorityCombo));
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void submitRequest(JComboBox<String> priorityCombo) {
        // Validate inputs
        if (vaccineCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a vaccine", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (quantityField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (justificationArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide justification", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String vaccineName = (String) vaccineCombo.getSelectedItem();
        String priority = (String) priorityCombo.getSelectedItem();
        
        // Find CDC organization and create request
        try {
            Organization cdcOrg = null;
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    if (enterprise.getName().contains("Centers for Disease Control") || 
                        enterprise.getName().contains("CDC")) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            if (org.getName().contains("Vaccine Distribution") || 
                                org.getName().contains("Distribution Coordination")) {
                                cdcOrg = org;
                                break;
                            }
                        }
                        if (cdcOrg != null) break;
                    }
                }
                if (cdcOrg != null) break;
            }
            
            if (cdcOrg != null) {
                // Find vaccine
                Vaccine selectedVaccine = null;
                for (Vaccine v : business.getVaccineDirectory()) {
                    if (v.getName().equals(vaccineName)) {
                        selectedVaccine = v;
                        break;
                    }
                }
                
                if (selectedVaccine != null) {
                    VaccineAllocationRequest request = new VaccineAllocationRequest();
                    request.setSender(account);
                    request.setReceiver(null);
                    request.setStatus("Sent");
                    request.setVaccine(selectedVaccine);
                    request.setQuantityRequested(quantity);
                    request.setPriority(priority);
                    request.setMessage("Allocation request: " + vaccineName + " - Qty: " + quantity + " - " + justificationArea.getText().trim());
                    
                    account.getWorkQueue().getWorkRequestList().add(request);
                    organization.getWorkQueue().getWorkRequestList().add(request);
                    cdcOrg.getWorkQueue().getWorkRequestList().add(request);
                    
                    JOptionPane.showMessageDialog(this,
                        "Vaccine allocation request submitted to CDC!\n\n" +
                        "Vaccine: " + vaccineName + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Priority: " + priority + "\n\n" +
                        "CDC will review and respond to your request.",
                        "Request Submitted",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    clearForm(priorityCombo);
                }
            } else {
                JOptionPane.showMessageDialog(this, "CDC organization not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error submitting request: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm(JComboBox<String> priorityCombo) {
        vaccineCombo.setSelectedIndex(0);
        quantityField.setText("");
        priorityCombo.setSelectedIndex(0);
        justificationArea.setText("");
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
