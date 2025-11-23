package ui.NursePractitionerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

/**
 * Work area for Nurse Practitioner role
 */
public class NursePractitionerWorkAreaJPanel extends JPanel {
    
    public NursePractitionerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                          Organization organization, Business business) {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Nurse Practitioner - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(createButton("Administer Vaccinations"));
        mainPanel.add(createButton("Report Disease Cases"));
        mainPanel.add(createButton("View Patient Records"));
        mainPanel.add(createButton("Schedule Appointments"));
        mainPanel.add(createButton("View Vaccine Availability"));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }
}
