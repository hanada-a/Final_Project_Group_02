package ui.PublicHealthOfficerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

/**
 * Work area for Public Health Officer role
 */
public class PublicHealthOfficerWorkAreaJPanel extends JPanel {
    
    public PublicHealthOfficerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                            Organization organization, Business business) {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 20, 60));
        JLabel titleLabel = new JLabel("Public Health Officer - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(createButton("View Disease Reports"));
        mainPanel.add(createButton("Coordinate State Response"));
        mainPanel.add(createButton("Forward to CDC"));
        mainPanel.add(createButton("Manage Provider Registry"));
        mainPanel.add(createButton("Issue State Health Alert"));
        mainPanel.add(createButton("View Compliance Reports"));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 45));
        return button;
    }
}
