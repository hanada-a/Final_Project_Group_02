package ui.SystemDashboard;

import business.Business;
import business.Domain.Disease;
import business.Domain.Vaccine;
import business.Employee.Employee;
import business.Enterprise;
import business.Network;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.WorkRequest;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * System-Level Reporting Dashboard
 * Provides comprehensive analytics and statistics at the ecosystem level
 * 
 * @author Akira Hanada
 */
public class SystemReportingDashboardJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private UserAccount account;
    
    private JTabbedPane tabbedPane;
    
    public SystemReportingDashboardJPanel(JPanel userProcessContainer, UserAccount account, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        this.account = account;
        
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(46, 125, 50));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        JLabel titleLabel = new JLabel("System Analytics Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed pane for different reports
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Overview", createOverviewPanel());
        tabbedPane.addTab("Work Requests", createWorkRequestAnalysisPanel());
        tabbedPane.addTab("Employees", createEmployeeAnalysisPanel());
        tabbedPane.addTab("Organizations", createOrganizationAnalysisPanel());
        tabbedPane.addTab("Health Data", createHealthDataPanel());
        tabbedPane.addTab("Export", createExportPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Footer with refresh button
        JPanel footerPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh Data");
        refreshBtn.addActionListener(e -> loadData());
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> navigateBack());
        footerPanel.add(refreshBtn);
        footerPanel.add(backBtn);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Summary statistics
        JPanel statsPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("System Statistics"));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        int totalNetworks = business.getEcoSystem() != null ? 
                          business.getEcoSystem().getNetworkList().size() : 0;
        int totalEnterprises = 0;
        int totalOrganizations = 0;
        int totalEmployees = 0;
        int totalUserAccounts = 0;
        int totalWorkRequests = 0;
        
        if (business.getEcoSystem() != null) {
            for (Network network : business.getEcoSystem().getNetworkList()) {
                totalEnterprises += network.getEnterpriseList().size();
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    totalOrganizations += enterprise.getOrganizationDirectory().getOrganizationList().size();
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        totalEmployees += org.getEmployeeDirectory().getEmployeeList().size();
                        totalUserAccounts += org.getUserAccountDirectory().getUserAccountList().size();
                        totalWorkRequests += org.getWorkQueue().getWorkRequestList().size();
                    }
                }
            }
        }
        
        int totalVaccines = business.getVaccineDirectory().size();
        int totalDiseases = business.getDiseaseDirectory().size();
        
        statsPanel.add(createStatCard("Networks", String.valueOf(totalNetworks), new Color(33, 150, 243)));
        statsPanel.add(createStatCard("Enterprises", String.valueOf(totalEnterprises), new Color(76, 175, 80)));
        statsPanel.add(createStatCard("Organizations", String.valueOf(totalOrganizations), new Color(255, 152, 0)));
        statsPanel.add(createStatCard("Employees", String.valueOf(totalEmployees), new Color(156, 39, 176)));
        statsPanel.add(createStatCard("User Accounts", String.valueOf(totalUserAccounts), new Color(233, 30, 99)));
        statsPanel.add(createStatCard("Work Requests", String.valueOf(totalWorkRequests), new Color(0, 150, 136)));
        statsPanel.add(createStatCard("Vaccines", String.valueOf(totalVaccines), new Color(63, 81, 181)));
        statsPanel.add(createStatCard("Diseases", String.valueOf(totalDiseases), new Color(244, 67, 54)));
        statsPanel.add(createStatCard("Active Users", String.valueOf(countActiveUsers()), new Color(96, 125, 139)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        // Key insights panel
        JPanel insightsPanel = new JPanel();
        insightsPanel.setLayout(new BoxLayout(insightsPanel, BoxLayout.Y_AXIS));
        insightsPanel.setBorder(BorderFactory.createTitledBorder("Key Insights"));
        insightsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea insightsText = new JTextArea(10, 50);
        insightsText.setEditable(false);
        insightsText.setLineWrap(true);
        insightsText.setWrapStyleWord(true);
        insightsText.setText(generateKeyInsights());
        
        JScrollPane scrollPane = new JScrollPane(insightsText);
        insightsPanel.add(scrollPane);
        
        panel.add(insightsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(Color.WHITE);
        
        JLabel labelText = new JLabel(label, SwingConstants.CENTER);
        labelText.setFont(new Font("Arial", Font.PLAIN, 14));
        labelText.setForeground(Color.WHITE);
        
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(labelText, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JPanel createWorkRequestAnalysisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Work request statistics by status
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Map<String, Integer> statusCounts = new HashMap<>();
        Map<String, Integer> typeCounts = new HashMap<>();
        
        if (business.getEcoSystem() != null) {
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        for (WorkRequest wr : org.getWorkQueue().getWorkRequestList()) {
                            String status = wr.getStatus();
                            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
                            
                            String type = wr.getClass().getSimpleName();
                            typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
                        }
                    }
                }
            }
        }
        
        int pending = statusCounts.getOrDefault("Pending", 0);
        int completed = statusCounts.getOrDefault("Completed", 0);
        int inProgress = statusCounts.getOrDefault("In Progress", 0);
        
        statsPanel.add(createStatCard("Pending", String.valueOf(pending), new Color(255, 152, 0)));
        statsPanel.add(createStatCard("In Progress", String.valueOf(inProgress), new Color(33, 150, 243)));
        statsPanel.add(createStatCard("Completed", String.valueOf(completed), new Color(76, 175, 80)));
        
        panel.add(statsPanel, BorderLayout.NORTH);
        
        // Table with work request breakdown by type
        String[] columnNames = {"Request Type", "Count", "Percentage"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        int total = typeCounts.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
            double percentage = total > 0 ? (entry.getValue() * 100.0 / total) : 0;
            model.addRow(new Object[]{
                entry.getKey(),
                entry.getValue(),
                String.format("%.1f%%", percentage)
            });
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Work Request Type Distribution"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createEmployeeAnalysisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Employee distribution by enterprise
        Map<String, Integer> enterpriseEmployees = new HashMap<>();
        Map<String, Integer> roleDistribution = new HashMap<>();
        
        if (business.getEcoSystem() != null) {
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    int count = 0;
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        count += org.getEmployeeDirectory().getEmployeeList().size();
                        
                        // Count roles
                        for (UserAccount ua : org.getUserAccountDirectory().getUserAccountList()) {
                            if (ua.getRole() != null) {
                                String roleName = ua.getRole().toString();
                                roleDistribution.put(roleName, roleDistribution.getOrDefault(roleName, 0) + 1);
                            }
                        }
                    }
                    enterpriseEmployees.put(enterprise.getName(), count);
                }
            }
        }
        
        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        // Top: Employee distribution by enterprise
        String[] columns1 = {"Enterprise", "Employee Count"};
        DefaultTableModel model1 = new DefaultTableModel(columns1, 0);
        for (Map.Entry<String, Integer> entry : enterpriseEmployees.entrySet()) {
            model1.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
        JTable table1 = new JTable(model1);
        JScrollPane scroll1 = new JScrollPane(table1);
        scroll1.setBorder(BorderFactory.createTitledBorder("Employee Distribution by Enterprise"));
        
        // Bottom: Role distribution
        String[] columns2 = {"Role", "Count"};
        DefaultTableModel model2 = new DefaultTableModel(columns2, 0);
        for (Map.Entry<String, Integer> entry : roleDistribution.entrySet()) {
            model2.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
        JTable table2 = new JTable(model2);
        JScrollPane scroll2 = new JScrollPane(table2);
        scroll2.setBorder(BorderFactory.createTitledBorder("Role Distribution"));
        
        splitPane.setTopComponent(scroll1);
        splitPane.setBottomComponent(scroll2);
        splitPane.setDividerLocation(200);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createOrganizationAnalysisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Organization details table
        String[] columnNames = {"Enterprise", "Organization", "Type", "Employees", "Users", "Work Requests"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        if (business.getEcoSystem() != null) {
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        model.addRow(new Object[]{
                            enterprise.getName(),
                            org.getName(),
                            org.getClass().getSimpleName(),
                            org.getEmployeeDirectory().getEmployeeList().size(),
                            org.getUserAccountDirectory().getUserAccountList().size(),
                            org.getWorkQueue().getWorkRequestList().size()
                        });
                    }
                }
            }
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Organization Details"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createHealthDataPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        // Vaccines table
        String[] vaccineColumns = {"Vaccine Name", "Manufacturer", "Doses Required", "Storage", "Expiration"};
        DefaultTableModel vaccineModel = new DefaultTableModel(vaccineColumns, 0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (Vaccine vaccine : business.getVaccineDirectory()) {
            vaccineModel.addRow(new Object[]{
                vaccine.getName(),
                vaccine.getManufacturer(),
                vaccine.getDosesRequired(),
                vaccine.getStorageTemperature(),
                vaccine.getExpirationDate() != null ? sdf.format(vaccine.getExpirationDate()) : "N/A"
            });
        }
        
        JTable vaccineTable = new JTable(vaccineModel);
        JScrollPane vaccineScroll = new JScrollPane(vaccineTable);
        vaccineScroll.setBorder(BorderFactory.createTitledBorder("Vaccine Inventory"));
        
        // Diseases table
        String[] diseaseColumns = {"Disease", "Severity", "Reportable", "Symptoms", "Incubation (days)"};
        DefaultTableModel diseaseModel = new DefaultTableModel(diseaseColumns, 0);
        
        for (Disease disease : business.getDiseaseDirectory()) {
            diseaseModel.addRow(new Object[]{
                disease.getName(),
                disease.getSeverity(),
                disease.isRequiresReporting() ? "Yes" : "No",
                disease.getSymptoms(),
                disease.getIncubationPeriod()
            });
        }
        
        JTable diseaseTable = new JTable(diseaseModel);
        JScrollPane diseaseScroll = new JScrollPane(diseaseTable);
        diseaseScroll.setBorder(BorderFactory.createTitledBorder("Disease Registry"));
        
        splitPane.setTopComponent(vaccineScroll);
        splitPane.setBottomComponent(diseaseScroll);
        splitPane.setDividerLocation(200);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createExportPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Export System Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        
        JButton exportOverviewBtn = createStyledButton("Export System Overview (CSV)");
        exportOverviewBtn.addActionListener(e -> exportSystemOverview());
        exportOverviewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exportOverviewBtn);
        panel.add(Box.createVerticalStrut(10));
        
        JButton exportWorkRequestsBtn = createStyledButton("Export Work Requests Analysis (CSV)");
        exportWorkRequestsBtn.addActionListener(e -> exportWorkRequestsAnalysis());
        exportWorkRequestsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exportWorkRequestsBtn);
        panel.add(Box.createVerticalStrut(10));
        
        JButton exportEmployeesBtn = createStyledButton("Export Employee List (CSV)");
        exportEmployeesBtn.addActionListener(e -> exportEmployeeList());
        exportEmployeesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exportEmployeesBtn);
        panel.add(Box.createVerticalStrut(10));
        
        JButton exportOrganizationsBtn = createStyledButton("Export Organization Details (CSV)");
        exportOrganizationsBtn.addActionListener(e -> exportOrganizationDetails());
        exportOrganizationsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exportOrganizationsBtn);
        panel.add(Box.createVerticalStrut(10));
        
        JButton exportHealthDataBtn = createStyledButton("Export Health Data (CSV)");
        exportHealthDataBtn.addActionListener(e -> exportHealthData());
        exportHealthDataBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exportHealthDataBtn);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 40));
        button.setMaximumSize(new Dimension(300, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }
    
    private void loadData() {
        // Reload all tab data
        tabbedPane.removeAll();
        tabbedPane.addTab("Overview", createOverviewPanel());
        tabbedPane.addTab("Work Requests", createWorkRequestAnalysisPanel());
        tabbedPane.addTab("Employees", createEmployeeAnalysisPanel());
        tabbedPane.addTab("Organizations", createOrganizationAnalysisPanel());
        tabbedPane.addTab("Health Data", createHealthDataPanel());
        tabbedPane.addTab("Export", createExportPanel());
    }
    
    private int countActiveUsers() {
        int count = 0;
        if (business.getEcoSystem() != null) {
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        for (UserAccount ua : org.getUserAccountDirectory().getUserAccountList()) {
                            if (ua.isEnabled()) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
    
    private String generateKeyInsights() {
        StringBuilder insights = new StringBuilder();
        insights.append("SYSTEM HEALTH INSIGHTS\n\n");
        
        int totalWorkRequests = 0;
        int pendingRequests = 0;
        int completedRequests = 0;
        
        if (business.getEcoSystem() != null) {
            for (Network network : business.getEcoSystem().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseList()) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        for (WorkRequest wr : org.getWorkQueue().getWorkRequestList()) {
                            totalWorkRequests++;
                            if ("Pending".equals(wr.getStatus())) pendingRequests++;
                            if ("Completed".equals(wr.getStatus())) completedRequests++;
                        }
                    }
                }
            }
        }
        
        double completionRate = totalWorkRequests > 0 ? (completedRequests * 100.0 / totalWorkRequests) : 0;
        insights.append(String.format("✓ Work Request Completion Rate: %.1f%%\n", completionRate));
        insights.append(String.format("✓ Pending Requests Requiring Attention: %d\n", pendingRequests));
        insights.append(String.format("✓ Total Vaccines Available: %d\n", business.getVaccineDirectory().size()));
        insights.append(String.format("✓ Diseases Under Surveillance: %d\n", business.getDiseaseDirectory().size()));
        insights.append(String.format("✓ Active User Accounts: %d\n\n", countActiveUsers()));
        
        insights.append("SYSTEM STATUS: ");
        if (completionRate >= 80) {
            insights.append("EXCELLENT - System operating at optimal efficiency\n");
        } else if (completionRate >= 60) {
            insights.append("GOOD - System functioning normally\n");
        } else if (completionRate >= 40) {
            insights.append("FAIR - Some backlogs detected, attention recommended\n");
        } else {
            insights.append("NEEDS ATTENTION - High number of pending requests\n");
        }
        
        return insights.toString();
    }
    
    private void exportSystemOverview() {
        try {
            String filename = "SystemOverview_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv";
            FileWriter writer = new FileWriter(filename);
            
            writer.append("Public Health Information Management System - System Overview Report\n");
            writer.append("Generated: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()) + "\n\n");
            
            writer.append("Metric,Value\n");
            
            int totalNetworks = business.getEcoSystem() != null ? business.getEcoSystem().getNetworkList().size() : 0;
            int totalEnterprises = 0;
            int totalOrganizations = 0;
            int totalEmployees = 0;
            int totalWorkRequests = 0;
            
            if (business.getEcoSystem() != null) {
                for (Network network : business.getEcoSystem().getNetworkList()) {
                    totalEnterprises += network.getEnterpriseList().size();
                    for (Enterprise enterprise : network.getEnterpriseList()) {
                        totalOrganizations += enterprise.getOrganizationDirectory().getOrganizationList().size();
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            totalEmployees += org.getEmployeeDirectory().getEmployeeList().size();
                            totalWorkRequests += org.getWorkQueue().getWorkRequestList().size();
                        }
                    }
                }
            }
            
            writer.append(String.format("Networks,%d\n", totalNetworks));
            writer.append(String.format("Enterprises,%d\n", totalEnterprises));
            writer.append(String.format("Organizations,%d\n", totalOrganizations));
            writer.append(String.format("Employees,%d\n", totalEmployees));
            writer.append(String.format("Work Requests,%d\n", totalWorkRequests));
            writer.append(String.format("Vaccines,%d\n", business.getVaccineDirectory().size()));
            writer.append(String.format("Diseases,%d\n", business.getDiseaseDirectory().size()));
            
            writer.flush();
            writer.close();
            
            JOptionPane.showMessageDialog(this, 
                "System overview exported successfully to:\n" + filename, 
                "Export Successful", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting data: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportWorkRequestsAnalysis() {
        try {
            String filename = "WorkRequestsAnalysis_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv";
            FileWriter writer = new FileWriter(filename);
            
            writer.append("Public Health Information Management System - Work Requests Analysis\n");
            writer.append("Generated: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()) + "\n\n");
            
            writer.append("Type,Status,Count\n");
            
            Map<String, Map<String, Integer>> analysis = new HashMap<>();
            
            if (business.getEcoSystem() != null) {
                for (Network network : business.getEcoSystem().getNetworkList()) {
                    for (Enterprise enterprise : network.getEnterpriseList()) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            for (WorkRequest wr : org.getWorkQueue().getWorkRequestList()) {
                                String type = wr.getClass().getSimpleName();
                                String status = wr.getStatus();
                                
                                analysis.putIfAbsent(type, new HashMap<>());
                                analysis.get(type).put(status, analysis.get(type).getOrDefault(status, 0) + 1);
                            }
                        }
                    }
                }
            }
            
            for (Map.Entry<String, Map<String, Integer>> typeEntry : analysis.entrySet()) {
                for (Map.Entry<String, Integer> statusEntry : typeEntry.getValue().entrySet()) {
                    writer.append(String.format("%s,%s,%d\n", 
                        typeEntry.getKey(), 
                        statusEntry.getKey(), 
                        statusEntry.getValue()));
                }
            }
            
            writer.flush();
            writer.close();
            
            JOptionPane.showMessageDialog(this, 
                "Work requests analysis exported successfully to:\n" + filename, 
                "Export Successful", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting data: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportEmployeeList() {
        try {
            String filename = "EmployeeList_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv";
            FileWriter writer = new FileWriter(filename);
            
            writer.append("Public Health Information Management System - Employee List\n");
            writer.append("Generated: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()) + "\n\n");
            
            writer.append("Enterprise,Organization,Employee ID,Name,Email,Phone,Position\n");
            
            if (business.getEcoSystem() != null) {
                for (Network network : business.getEcoSystem().getNetworkList()) {
                    for (Enterprise enterprise : network.getEnterpriseList()) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            for (Employee emp : org.getEmployeeDirectory().getEmployeeList()) {
                                writer.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                                    enterprise.getName(),
                                    org.getName(),
                                    emp.getEmployeeID(),
                                    emp.getName(),
                                    emp.getEmail(),
                                    emp.getPhoneNumber(),
                                    emp.getPosition()));
                            }
                        }
                    }
                }
            }
            
            writer.flush();
            writer.close();
            
            JOptionPane.showMessageDialog(this, 
                "Employee list exported successfully to:\n" + filename, 
                "Export Successful", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting data: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportOrganizationDetails() {
        try {
            String filename = "OrganizationDetails_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv";
            FileWriter writer = new FileWriter(filename);
            
            writer.append("Public Health Information Management System - Organization Details\n");
            writer.append("Generated: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()) + "\n\n");
            
            writer.append("Enterprise,Organization,Organization ID,Type,Employees,User Accounts,Work Requests\n");
            
            if (business.getEcoSystem() != null) {
                for (Network network : business.getEcoSystem().getNetworkList()) {
                    for (Enterprise enterprise : network.getEnterpriseList()) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            writer.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",%d,%d,%d\n",
                                enterprise.getName(),
                                org.getName(),
                                org.getOrganizationID(),
                                org.getClass().getSimpleName(),
                                org.getEmployeeDirectory().getEmployeeList().size(),
                                org.getUserAccountDirectory().getUserAccountList().size(),
                                org.getWorkQueue().getWorkRequestList().size()));
                        }
                    }
                }
            }
            
            writer.flush();
            writer.close();
            
            JOptionPane.showMessageDialog(this, 
                "Organization details exported successfully to:\n" + filename, 
                "Export Successful", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting data: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportHealthData() {
        try {
            String filename = "HealthData_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv";
            FileWriter writer = new FileWriter(filename);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            
            writer.append("Public Health Information Management System - Health Data Export\n");
            writer.append("Generated: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()) + "\n\n");
            
            writer.append("VACCINES\n");
            writer.append("Name,Manufacturer,Doses Required,Storage Temperature,Target Diseases,Expiration Date\n");
            
            for (Vaccine vaccine : business.getVaccineDirectory()) {
                writer.append(String.format("\"%s\",\"%s\",%d,\"%s\",\"%s\",\"%s\"\n",
                    vaccine.getName(),
                    vaccine.getManufacturer(),
                    vaccine.getDosesRequired(),
                    vaccine.getStorageTemperature(),
                    vaccine.getTargetDiseases(),
                    vaccine.getExpirationDate() != null ? sdf.format(vaccine.getExpirationDate()) : "N/A"));
            }
            
            writer.append("\nDISEASES\n");
            writer.append("Name,Severity,Reportable,Description,Symptoms,Incubation Period (days)\n");
            
            for (Disease disease : business.getDiseaseDirectory()) {
                writer.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d\n",
                    disease.getName(),
                    disease.getSeverity(),
                    disease.isRequiresReporting() ? "Yes" : "No",
                    disease.getDescription(),
                    disease.getSymptoms(),
                    disease.getIncubationPeriod()));
            }
            
            writer.flush();
            writer.close();
            
            JOptionPane.showMessageDialog(this, 
                "Health data exported successfully to:\n" + filename, 
                "Export Successful", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting data: " + e.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void navigateBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
