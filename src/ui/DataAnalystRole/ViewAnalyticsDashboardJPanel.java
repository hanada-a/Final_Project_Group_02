package ui.DataAnalystRole;

import business.Business;
import business.Domain.Disease;
import business.Domain.Vaccine;
import business.Enterprise;
import business.Network;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.WorkRequest;
import java.awt.*;
import javax.swing.*;

/**
 * Panel for data analysts to view analytics dashboard
 */
public class ViewAnalyticsDashboardJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    
    public ViewAnalyticsDashboardJPanel(JPanel userProcessContainer, UserAccount account,
                                        Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel titleLabel = new JLabel("Analytics Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("System Overview", createSystemOverviewPanel());
        tabbedPane.addTab("Vaccine Analytics", createVaccineAnalyticsPanel());
        tabbedPane.addTab("Disease Analytics", createDiseaseAnalyticsPanel());
        tabbedPane.addTab("Network Analytics", createNetworkAnalyticsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> refreshDashboard());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createSystemOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Calculate system metrics
        int totalNetworks = business.getEcoSystem().getNetworkList().size();
        int totalEnterprises = 0;
        int totalOrganizations = 0;
        int totalWorkRequests = 0;
        
        for (Network network : business.getEcoSystem().getNetworkList()) {
            totalEnterprises += network.getEnterpriseList().size();
            for (Enterprise enterprise : network.getEnterpriseList()) {
                totalOrganizations += enterprise.getOrganizationDirectory().getOrganizationList().size();
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    totalWorkRequests += org.getWorkQueue().getWorkRequestList().size();
                }
            }
        }
        
        int totalVaccines = business.getVaccineDirectory().size();
        int totalDiseases = business.getDiseaseDirectory().size();
        
        // Stats grid
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        statsPanel.add(createStatCard("Networks", String.valueOf(totalNetworks), new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Enterprises", String.valueOf(totalEnterprises), new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Organizations", String.valueOf(totalOrganizations), new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Work Requests", String.valueOf(totalWorkRequests), new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Vaccine Types", String.valueOf(totalVaccines), new Color(26, 188, 156)));
        statsPanel.add(createStatCard("Tracked Diseases", String.valueOf(totalDiseases), new Color(231, 76, 60)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createVaccineAnalyticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        sb.append("VACCINE ANALYTICS\n\n");
        
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            sb.append(String.format("%-35s | Mfr: %-20s | Doses Req: %d\n",
                vaccine.getName(),
                vaccine.getManufacturer(),
                vaccine.getDosesRequired()
            ));
        }
        
        sb.append("\n").append("-".repeat(80)).append("\n");
        sb.append(String.format("Total Vaccine Types: %d\n", business.getVaccineDirectory().size()));
        
        textArea.setText(sb.toString());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createDiseaseAnalyticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        sb.append("DISEASE ANALYTICS\n\n");
        
        for (Disease disease : business.getDiseaseDirectory()) {
            sb.append(String.format("%-40s | Severity: %-10s\n",
                disease.getName(),
                disease.getSeverity()
            ));
        }
        
        sb.append("\n").append("-".repeat(80)).append("\n");
        sb.append(String.format("Total Diseases Tracked: %d\n", 
            business.getDiseaseDirectory().size()));
        
        textArea.setText(sb.toString());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createNetworkAnalyticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        sb.append("NETWORK ANALYTICS\n\n");
        
        for (Network network : business.getEcoSystem().getNetworkList()) {
            sb.append(String.format("Network: %s\n", network.getName()));
            sb.append("-".repeat(80)).append("\n");
            
            for (Enterprise enterprise : network.getEnterpriseList()) {
                int orgCount = enterprise.getOrganizationDirectory().getOrganizationList().size();
                int workRequestCount = 0;
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    workRequestCount += org.getWorkQueue().getWorkRequestList().size();
                }
                
                sb.append(String.format("  %-40s | Orgs: %3d | Work Requests: %5d\n",
                    enterprise.getName(),
                    orgCount,
                    workRequestCount
                ));
            }
            sb.append("\n");
        }
        
        textArea.setText(sb.toString());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private void refreshDashboard() {
        userProcessContainer.remove(this);
        ViewAnalyticsDashboardJPanel panel = new ViewAnalyticsDashboardJPanel(userProcessContainer, null, null, business);
        userProcessContainer.add("ViewAnalyticsDashboardJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
        JOptionPane.showMessageDialog(this, "Dashboard data refreshed", 
            "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
