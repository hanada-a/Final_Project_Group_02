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
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    
    public PublicHealthOfficerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                            Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
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
        
        mainPanel.add(createButton("View Disease Reports", this::viewDiseaseReports));
        mainPanel.add(createButton("Coordinate State Response", this::coordinateStateResponse));
        mainPanel.add(createButton("Forward to CDC", this::forwardToCDC));
        mainPanel.add(createButton("Manage Provider Registry", this::manageProviderRegistry));
        mainPanel.add(createButton("Issue State Health Alert", this::issueStateHealthAlert));
        mainPanel.add(createButton("View Compliance Reports", this::viewComplianceReports));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 45));
        button.addActionListener(e -> action.run());
        return button;
    }
    
    private void viewDiseaseReports() {
        ViewDiseaseReportsJPanel panel = new ViewDiseaseReportsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewDiseaseReportsJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void coordinateStateResponse() {
        CoordinateStateResponseJPanel panel = new CoordinateStateResponseJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("CoordinateStateResponseJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void forwardToCDC() {
        ForwardToCDCJPanel panel = new ForwardToCDCJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ForwardToCDCJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void manageProviderRegistry() {
        ManageProviderRegistryJPanel panel = new ManageProviderRegistryJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ManageProviderRegistryJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void issueStateHealthAlert() {
        IssueStateHealthAlertJPanel panel = new IssueStateHealthAlertJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("IssueStateHealthAlertJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void viewComplianceReports() {
        ViewComplianceReportsJPanel panel = new ViewComplianceReportsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewComplianceReportsJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
