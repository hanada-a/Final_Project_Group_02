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
    
    public DataAnalystWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                    Organization organization, Business business) {
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
        
        JButton viewDataRequestsBtn = createStyledButton("View Data Analysis Requests");
        JButton generateReportBtn = createStyledButton("Generate Statistical Report");
        JButton viewDashboardBtn = createStyledButton("View Analytics Dashboard");
        JButton exportDataBtn = createStyledButton("Export Data");
        JButton processRequestBtn = createStyledButton("Process Pending Requests");
        
        mainPanel.add(viewDataRequestsBtn);
        mainPanel.add(generateReportBtn);
        mainPanel.add(viewDashboardBtn);
        mainPanel.add(exportDataBtn);
        mainPanel.add(processRequestBtn);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }
}
