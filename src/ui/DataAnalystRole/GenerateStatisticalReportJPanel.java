package ui.DataAnalystRole;

import business.Business;
import business.Domain.Disease;
import business.Domain.Vaccine;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

/**
 * Panel for data analysts to generate statistical reports
 */
public class GenerateStatisticalReportJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private JTextArea reportArea;
    private JComboBox<String> reportTypeCombo;
    
    public GenerateStatisticalReportJPanel(JPanel userProcessContainer, UserAccount account,
                                           Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel titleLabel = new JLabel("Generate Statistical Report");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Report Type:"));
        
        reportTypeCombo = new JComboBox<>(new String[]{
            "Vaccine Distribution Statistics",
            "Disease Outbreak Analysis",
            "Healthcare Provider Summary",
            "Compliance Overview",
            "Comprehensive System Report"
        });
        selectionPanel.add(reportTypeCombo);
        
        JButton generateButton = new JButton("Generate Report");
        generateButton.addActionListener(e -> generateReport());
        selectionPanel.add(generateButton);
        
        mainPanel.add(selectionPanel, BorderLayout.NORTH);
        
        // Report area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setText("Select a report type and click 'Generate Report' to view statistical analysis.");
        
        mainPanel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton exportButton = new JButton("Export as CSV");
        exportButton.addActionListener(e -> exportReport());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(exportButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void generateReport() {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
        
        StringBuilder report = new StringBuilder();
        report.append("=".repeat(80)).append("\n");
        report.append(reportType.toUpperCase()).append("\n");
        report.append("Generated: ").append(sdf.format(new java.util.Date())).append("\n");
        report.append("=".repeat(80)).append("\n\n");
        
        switch (reportType) {
            case "Vaccine Distribution Statistics":
                generateVaccineReport(report);
                break;
            case "Disease Outbreak Analysis":
                generateDiseaseReport(report);
                break;
            case "Healthcare Provider Summary":
                generateProviderReport(report);
                break;
            case "Compliance Overview":
                generateComplianceReport(report);
                break;
            case "Comprehensive System Report":
                generateComprehensiveReport(report);
                break;
        }
        
        report.append("\n").append("=".repeat(80)).append("\n");
        report.append("END OF REPORT\n");
        
        reportArea.setText(report.toString());
        reportArea.setCaretPosition(0);
    }
    
    private void generateVaccineReport(StringBuilder report) {
        report.append("VACCINE INVENTORY ANALYSIS\n\n");
        
        int totalVaccines = 0;
        
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            totalVaccines++;
            report.append(String.format("%-30s | Doses Req: %d | Mfr: %s\n",
                vaccine.getName(),
                vaccine.getDosesRequired(),
                vaccine.getManufacturer()
            ));
        }
        
        report.append("\n").append("-".repeat(80)).append("\n");
        report.append(String.format("Total Vaccine Types: %d\n", totalVaccines));
    }
    
    private void generateDiseaseReport(StringBuilder report) {
        report.append("DISEASE TRACKING SUMMARY\n\n");
        
        int trackedDiseases = business.getDiseaseDirectory().size();
        
        for (Disease disease : business.getDiseaseDirectory()) {
            report.append(String.format("Disease: %-30s | Severity: %s\n",
                disease.getName(),
                disease.getSeverity()
            ));
        }
        
        report.append("\n").append("-".repeat(80)).append("\n");
        report.append(String.format("Total Diseases Tracked: %d\n", trackedDiseases));
    }
    
    private void generateProviderReport(StringBuilder report) {
        report.append("HEALTHCARE PROVIDER SUMMARY\n\n");
        report.append("Provider statistics aggregated across all networks and enterprises.\n\n");
        report.append("Note: Detailed provider counts available in the Analytics Dashboard.\n");
    }
    
    private void generateComplianceReport(StringBuilder report) {
        report.append("COMPLIANCE OVERVIEW\n\n");
        report.append("System-wide compliance metrics and audit status.\n\n");
        report.append("Note: Detailed compliance data available through compliance audits.\n");
    }
    
    private void generateComprehensiveReport(StringBuilder report) {
        report.append("COMPREHENSIVE SYSTEM REPORT\n\n");
        generateVaccineReport(report);
        report.append("\n\n");
        generateDiseaseReport(report);
        report.append("\n\n");
        report.append("Additional metrics available through specialized reports.\n");
    }
    
    private void exportReport() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        String filename = "StatisticalReport_" + sdf.format(new java.util.Date()) + ".csv";
        
        JOptionPane.showMessageDialog(this,
            "Report would be exported to:\n" + filename + "\n\n" +
            "This would generate a CSV file with the statistical data.",
            "Export Report",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
