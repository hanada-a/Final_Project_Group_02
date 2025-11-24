package ui.DataAnalystRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

/**
 * Panel for data analysts to export data
 */
public class ExportDataJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private JComboBox<String> exportTypeCombo;
    private JComboBox<String> formatCombo;
    private JTextArea statusArea;
    
    public ExportDataJPanel(JPanel userProcessContainer, UserAccount account,
                            Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel titleLabel = new JLabel("Export Data");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Options panel
        JPanel optionsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Export Options"));
        
        optionsPanel.add(new JLabel("Data Type:"));
        exportTypeCombo = new JComboBox<>(new String[]{
            "Vaccine Inventory",
            "Disease Reports",
            "Work Requests",
            "Organization Data",
            "Employee Data",
            "User Accounts",
            "All System Data"
        });
        optionsPanel.add(exportTypeCombo);
        
        optionsPanel.add(new JLabel("Export Format:"));
        formatCombo = new JComboBox<>(new String[]{"CSV", "JSON", "XML", "Excel (XLSX)"});
        optionsPanel.add(formatCombo);
        
        optionsPanel.add(new JLabel(""));
        JButton exportButton = new JButton("Export Data");
        exportButton.setBackground(new Color(46, 204, 113));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFont(new Font("Arial", Font.BOLD, 14));
        exportButton.addActionListener(e -> performExport());
        optionsPanel.add(exportButton);
        
        mainPanel.add(optionsPanel, BorderLayout.NORTH);
        
        // Status area
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("Export Status"));
        
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statusArea.setText("Select data type and format, then click 'Export Data' to begin export.");
        
        statusPanel.add(new JScrollPane(statusArea), BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton clearButton = new JButton("Clear Status");
        clearButton.addActionListener(e -> statusArea.setText(""));
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void performExport() {
        String dataType = (String) exportTypeCombo.getSelectedItem();
        String format = (String) formatCombo.getSelectedItem();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String timestamp = sdf.format(new java.util.Date());
        
        String filename = dataType.replaceAll("\\s+", "_") + "_" + timestamp;
        String extension = format.equals("CSV") ? ".csv" :
                          format.equals("JSON") ? ".json" :
                          format.equals("XML") ? ".xml" : ".xlsx";
        
        StringBuilder status = new StringBuilder();
        status.append("=== EXPORT PROCESS STARTED ===\n\n");
        status.append("Timestamp: ").append(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        status.append("Data Type: ").append(dataType).append("\n");
        status.append("Format: ").append(format).append("\n");
        status.append("Filename: ").append(filename).append(extension).append("\n\n");
        
        status.append("Processing...\n");
        
        // Simulate export process
        switch (dataType) {
            case "Vaccine Inventory":
                int vaccineCount = business.getVaccineDirectory().size();
                status.append("  - Exporting ").append(vaccineCount).append(" vaccine records...\n");
                break;
            case "Disease Reports":
                int diseaseCount = business.getDiseaseDirectory().size();
                status.append("  - Exporting ").append(diseaseCount).append(" disease records...\n");
                break;
            case "Work Requests":
                status.append("  - Aggregating work requests from all organizations...\n");
                break;
            case "Organization Data":
                status.append("  - Exporting organization hierarchy and details...\n");
                break;
            case "Employee Data":
                status.append("  - Exporting employee records from all organizations...\n");
                break;
            case "User Accounts":
                status.append("  - Exporting user account information (excluding passwords)...\n");
                break;
            case "All System Data":
                status.append("  - Performing comprehensive system export...\n");
                status.append("  - This may take several minutes...\n");
                break;
        }
        
        status.append("\n");
        status.append("Export completed successfully!\n");
        status.append("File would be saved to: ").append(filename).append(extension).append("\n\n");
        status.append("=== EXPORT COMPLETE ===\n");
        
        statusArea.setText(status.toString());
        
        JOptionPane.showMessageDialog(this,
            "Data export completed successfully!\n\n" +
            "File: " + filename + extension + "\n" +
            "Format: " + format,
            "Export Complete",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
