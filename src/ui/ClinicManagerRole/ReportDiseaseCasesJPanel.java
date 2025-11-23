package ui.ClinicManagerRole;

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
 * Panel for clinic managers to report disease cases
 */
public class ReportDiseaseCasesJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JComboBox<Disease> diseaseComboBox;
    private JTextField caseCountField;
    private JTextArea detailsArea;
    
    public ReportDiseaseCasesJPanel(JPanel userProcessContainer, UserAccount account,
                                   Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        JLabel titleLabel = new JLabel("Report Disease Cases");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left: Reports table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Disease Reports"));
        
        String[] columns = {"Date", "Disease", "Cases", "Status"};
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
        
        // Disease selection
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Disease:"), gbc);
        gbc.gridx = 1;
        diseaseComboBox = new JComboBox<>();
        populateDiseases();
        rightPanel.add(diseaseComboBox, gbc);
        
        // Case count
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Number of Cases:"), gbc);
        gbc.gridx = 1;
        caseCountField = new JTextField("1", 20);
        rightPanel.add(caseCountField, gbc);
        
        // Details
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        rightPanel.add(new JLabel("Details:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        detailsArea = new JTextArea(5, 20);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setText("Cases observed at clinic. Symptoms include fever, cough, and fatigue.");
        rightPanel.add(new JScrollPane(detailsArea), gbc);
        
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
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
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
        
        for (Object obj : account.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof DiseaseReportRequest) {
                DiseaseReportRequest request = (DiseaseReportRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "";
                String disease = message.contains("Disease:") ? 
                    message.split("Disease: ")[1].split(",")[0] : "Unknown";
                String cases = message.contains("Cases:") ? 
                    message.split("Cases: ")[1].split(" ")[0] : "1";
                
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    disease,
                    cases,
                    request.getStatus()
                });
            }
        }
    }
    
    private void submitReport() {
        try {
            Disease disease = (Disease) diseaseComboBox.getSelectedItem();
            if (disease == null) {
                JOptionPane.showMessageDialog(this, "Please select a disease", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String caseCountStr = caseCountField.getText().trim();
            if (caseCountStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter number of cases", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int caseCount = Integer.parseInt(caseCountStr);
            if (caseCount <= 0) {
                JOptionPane.showMessageDialog(this, "Number of cases must be greater than 0", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String details = detailsArea.getText().trim();
            if (details.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter case details", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Create disease report request
            DiseaseReportRequest request = new DiseaseReportRequest();
            request.setSender(account);
            request.setStatus("Pending");
            request.setMessage("Disease: " + disease.getName() + ", Cases: " + caseCount + 
                             ", Details: " + details + ", Location: " + organization.getName());
            
            account.getWorkQueue().getWorkRequestList().add(request);
            organization.getWorkQueue().getWorkRequestList().add(request);
            
            // Update table
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            tableModel.insertRow(0, new Object[]{
                sdf.format(request.getRequestDate()),
                disease.getName(),
                caseCount,
                request.getStatus()
            });
            
            JOptionPane.showMessageDialog(this,
                "Disease report submitted successfully!\n\n" +
                "Disease: " + disease.getName() + "\n" +
                "Cases: " + caseCount + "\n" +
                "The report will be forwarded to public health authorities for review.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            caseCountField.setText("1");
            detailsArea.setText("Cases observed at clinic. Symptoms include fever, cough, and fatigue.");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Number of cases must be a valid number", 
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
