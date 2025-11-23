package ui.NursePractitionerRole;

import business.Business;
import business.Domain.Vaccine;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.VaccineShipmentRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for nurses to administer vaccinations
 */
public class AdministerVaccinationsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JTable vaccineTable;
    private DefaultTableModel tableModel;
    private JTextField patientNameField;
    private JComboBox<Vaccine> vaccineComboBox;
    private JTextField doseNumberField;
    
    public AdministerVaccinationsJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Administer Vaccinations");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left: Vaccination records table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Recent Vaccinations"));
        
        String[] columns = {"Date", "Patient", "Vaccine", "Dose", "Administered By"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vaccineTable = new JTable(tableModel);
        leftPanel.add(new JScrollPane(vaccineTable), BorderLayout.CENTER);
        
        // Right: Administer form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Administer Vaccination"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Patient Name
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        patientNameField = new JTextField(20);
        rightPanel.add(patientNameField, gbc);
        
        // Vaccine
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Vaccine:"), gbc);
        gbc.gridx = 1;
        vaccineComboBox = new JComboBox<>();
        populateVaccines();
        rightPanel.add(vaccineComboBox, gbc);
        
        // Dose Number
        gbc.gridx = 0; gbc.gridy = 2;
        rightPanel.add(new JLabel("Dose Number:"), gbc);
        gbc.gridx = 1;
        doseNumberField = new JTextField("1", 20);
        rightPanel.add(doseNumberField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton administerButton = new JButton("Administer");
        administerButton.addActionListener(e -> administerVaccination());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(administerButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(400);
        
        add(splitPane, BorderLayout.CENTER);
        
        loadVaccinationRecords();
    }
    
    private void populateVaccines() {
        vaccineComboBox.removeAllItems();
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            if (vaccine.isActive()) {
                vaccineComboBox.addItem(vaccine);
            }
        }
    }
    
    private void loadVaccinationRecords() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        // Show recent vaccination records from work queue
        for (Object obj : account.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof VaccineShipmentRequest) {
                VaccineShipmentRequest request = (VaccineShipmentRequest) obj;
                if ("Completed".equals(request.getStatus())) {
                    tableModel.addRow(new Object[]{
                        sdf.format(request.getResolveDate()),
                        request.getMessage() != null ? request.getMessage() : "N/A",
                        "Vaccination Completed",
                        "N/A",
                        account.getEmployee().getName()
                    });
                }
            }
        }
    }
    
    private void administerVaccination() {
        try {
            String patientName = patientNameField.getText().trim();
            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter patient name", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Vaccine vaccine = (Vaccine) vaccineComboBox.getSelectedItem();
            if (vaccine == null) {
                JOptionPane.showMessageDialog(this, "Please select a vaccine", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String doseStr = doseNumberField.getText().trim();
            if (doseStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter dose number", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int doseNumber = Integer.parseInt(doseStr);
            if (doseNumber < 1 || doseNumber > vaccine.getDosesRequired()) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid dose number. " + vaccine.getName() + " requires " + 
                    vaccine.getDosesRequired() + " dose(s)", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Record vaccination
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            tableModel.insertRow(0, new Object[]{
                sdf.format(new java.util.Date()),
                patientName,
                vaccine.getName(),
                doseNumber + " of " + vaccine.getDosesRequired(),
                account.getEmployee().getName()
            });
            
            JOptionPane.showMessageDialog(this,
                "Vaccination administered successfully!\nPatient: " + patientName + 
                "\nVaccine: " + vaccine.getName() + "\nDose: " + doseNumber,
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            patientNameField.setText("");
            doseNumberField.setText("1");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dose number must be a valid number", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error administering vaccination: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
