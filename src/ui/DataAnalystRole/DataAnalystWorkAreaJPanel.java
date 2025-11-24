package ui.DataAnalystRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

/**
 * Work area for Data Analyst role
 */
public class DataAnalystWorkAreaJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    
    public DataAnalystWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                    Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(59, 89, 152));
        JLabel titleLabel = new JLabel("Data Analyst Work Area - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(createStyledButton("View Data Analysis Requests", this::viewDataRequests));
        mainPanel.add(createStyledButton("Generate Statistical Report", this::generateReport));
        mainPanel.add(createStyledButton("View Analytics Dashboard", this::viewDashboard));
        mainPanel.add(createStyledButton("Export Data", this::exportData));
        mainPanel.add(createStyledButton("Process Pending Requests", this::processPendingRequests));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 50));
        button.addActionListener(e -> action.run());
        return button;
    }
    
    private void viewDataRequests() {
        ViewDataAnalysisRequestsJPanel panel = new ViewDataAnalysisRequestsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewDataAnalysisRequestsJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void generateReport() {
        GenerateStatisticalReportJPanel panel = new GenerateStatisticalReportJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("GenerateStatisticalReportJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void viewDashboard() {
        ViewAnalyticsDashboardJPanel panel = new ViewAnalyticsDashboardJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewAnalyticsDashboardJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void exportData() {
        ExportDataJPanel panel = new ExportDataJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ExportDataJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void processPendingRequests() {
        ProcessPendingRequestsJPanel panel = new ProcessPendingRequestsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ProcessPendingRequestsJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
