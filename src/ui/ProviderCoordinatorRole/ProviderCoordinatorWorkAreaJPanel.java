package ui.ProviderCoordinatorRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

/**
 * Work area for Provider Coordinator role
 * 
 * @author Akira Hanada
 * @author Maxwell Sowell
 */
public class ProviderCoordinatorWorkAreaJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    
    public ProviderCoordinatorWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                            Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
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
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(createButton("Manage Healthcare Providers", this::manageHealthcareProviders));
        mainPanel.add(createButton("Process Vaccine Shipment Requests", this::processVaccineShipmentRequests));
        mainPanel.add(createButton("Schedule Compliance Audits", this::scheduleComplianceAudits));
        mainPanel.add(createButton("View Provider Registry", this::viewProviderRegistry));
        mainPanel.add(createButton("Request Vaccine Allocation from CDC", this::requestVaccineAllocationFromCDC));
        mainPanel.add(createButton("Process Cold Chain Failure Reports", this::processColdChainFailureReports));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(350, 50));
        button.addActionListener(e -> action.run());
        return button;
    }
    
    private void manageHealthcareProviders() {
        ManageHealthcareProvidersJPanel panel = new ManageHealthcareProvidersJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ManageHealthcareProvidersJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void processVaccineShipmentRequests() {
        ProcessVaccineShipmentRequestsJPanel panel = new ProcessVaccineShipmentRequestsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ProcessVaccineShipmentRequestsJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void scheduleComplianceAudits() {
        ScheduleComplianceAuditsJPanel panel = new ScheduleComplianceAuditsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ScheduleComplianceAuditsJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void viewProviderRegistry() {
        ViewProviderRegistryJPanel panel = new ViewProviderRegistryJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewProviderRegistryJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void requestVaccineAllocationFromCDC() {
        RequestVaccineAllocationFromCDCJPanel panel = new RequestVaccineAllocationFromCDCJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("RequestVaccineAllocationFromCDCJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void processColdChainFailureReports() {
        ProcessColdChainFailureRequestsJPanel panel = new ProcessColdChainFailureRequestsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ProcessColdChainFailureRequestsJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
}
