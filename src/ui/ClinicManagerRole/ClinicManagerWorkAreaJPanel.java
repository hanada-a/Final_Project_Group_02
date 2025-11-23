package ui.ClinicManagerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

/**
 * Work area for Clinic Manager role
 */
public class ClinicManagerWorkAreaJPanel extends JPanel {
    
    public ClinicManagerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        JLabel titleLabel = new JLabel("Clinic Manager - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(createButton("Request Vaccine Shipment"));
        mainPanel.add(createButton("Manage Clinic Staff"));
        mainPanel.add(createButton("Schedule Patient Appointments"));
        mainPanel.add(createButton("View Vaccine Inventory"));
        mainPanel.add(createButton("Report Disease Cases"));
        mainPanel.add(createButton("View Clinic Statistics"));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 45));
        return button;
    }
}
