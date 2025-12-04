package ui.NursePractitionerRole;

import business.Business;
import business.Domain.Disease;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.DiseaseReportRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for nurses to report disease cases
 */
public class ReportDiseaseCasesJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextField patientNameField;
    private JComboBox<Disease> diseaseComboBox;
    private JTextArea symptomsArea;
    private JTextField ageField;
    
    public ReportDiseaseCasesJPanel(JPanel userProcessContainer, UserAccount account,
                                   Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Report Disease Cases");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left: Reports table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Submitted Reports"));
        
        String[] columns = {"Date", "Patient", "Disease", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(tableModel);
        leftPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        
        // Right: Report form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Submit Disease Report"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Patient Name
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        patientNameField = new JTextField(20);
        rightPanel.add(patientNameField, gbc);
        
        // Patient Age
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Patient Age:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(20);
        rightPanel.add(ageField, gbc);
        
        // Disease
        gbc.gridx = 0; gbc.gridy = 2;
        rightPanel.add(new JLabel("Disease:"), gbc);
        gbc.gridx = 1;
        diseaseComboBox = new JComboBox<>();
        populateDiseases();
        rightPanel.add(diseaseComboBox, gbc);
        
        // Symptoms
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        rightPanel.add(new JLabel("Symptoms:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        symptomsArea = new JTextArea(5, 20);
        symptomsArea.setLineWrap(true);
        symptomsArea.setWrapStyleWord(true);
        rightPanel.add(new JScrollPane(symptomsArea), gbc);
        
        // Buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Submit Report");
        submitButton.addActionListener(e -> submitReport());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(400);
        
        add(splitPane, BorderLayout.CENTER);
        
        loadReports();
    }
    
    private void populateDiseases() {
        diseaseComboBox.removeAllItems();
        for (Disease disease : business.getDiseaseDirectory()) {
            diseaseComboBox.addItem(disease);
        }
    }
    
    private void loadReports() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof DiseaseReportRequest) {
                DiseaseReportRequest request = (DiseaseReportRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "";
                String patient = message.contains("Patient:") ? 
                    message.split("Patient: ")[1].split(",")[0] : "N/A";
                String disease = request.getDisease() != null ? 
                    request.getDisease().getName() : "Unknown";
                
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    patient,
                    disease,
                    request.getStatus()
                });
            }
        }
    }
    
    private void submitReport() {
        try {
            String patientName = patientNameField.getText().trim();
            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter patient name", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String ageStr = ageField.getText().trim();
            if (ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter patient age", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int age = Integer.parseInt(ageStr);
            if (age < 0 || age > 120) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age (0-120)", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Disease disease = (Disease) diseaseComboBox.getSelectedItem();
            if (disease == null) {
                JOptionPane.showMessageDialog(this, "Please select a disease", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String symptoms = symptomsArea.getText().trim();
            if (symptoms.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter symptoms", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Create disease report request
            DiseaseReportRequest request = new DiseaseReportRequest();
            request.setSender(account);
            request.setStatus("Pending");
            request.setMessage("Patient: " + patientName + ", Age: " + age + 
                             ", Disease: " + disease.getName() + ", Symptoms: " + symptoms);
            
            account.getWorkQueue().getWorkRequestList().add(request);
            organization.getWorkQueue().getWorkRequestList().add(request);
            
            // Update table
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            tableModel.insertRow(0, new Object[]{
                sdf.format(request.getRequestDate()),
                patientName,
                disease.getName(),
                request.getStatus()
            });
            
            JOptionPane.showMessageDialog(this,
                "Disease report submitted successfully!\nThe report will be reviewed by public health authorities.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            patientNameField.setText("");
            ageField.setText("");
            symptomsArea.setText("");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error submitting report: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
