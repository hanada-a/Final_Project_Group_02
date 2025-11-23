package ui.ProviderCoordinatorRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

/**
 * Work area for Provider Coordinator role
 */
public class ProviderCoordinatorWorkAreaJPanel extends JPanel {
    
    public ProviderCoordinatorWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                            Organization organization, Business business) {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(75, 0, 130));
        JLabel titleLabel = new JLabel("Provider Coordinator - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(createButton("Manage Healthcare Providers"));
        mainPanel.add(createButton("Process Vaccine Shipment Requests"));
        mainPanel.add(createButton("Schedule Compliance Audits"));
        mainPanel.add(createButton("View Provider Registry"));
        mainPanel.add(createButton("Request Vaccine Allocation from CDC"));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(350, 50));
        return button;
    }
}
