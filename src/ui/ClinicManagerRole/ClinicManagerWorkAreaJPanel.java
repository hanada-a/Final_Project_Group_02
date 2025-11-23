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
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    
    public ClinicManagerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
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
        
        mainPanel.add(createButton("Request Vaccine Shipment", this::requestVaccineShipment));
        mainPanel.add(createButton("Manage Clinic Staff", this::manageClinicStaff));
        mainPanel.add(createButton("Schedule Patient Appointments", this::scheduleAppointments));
        mainPanel.add(createButton("View Vaccine Inventory", this::viewVaccineInventory));
        mainPanel.add(createButton("Report Disease Cases", this::reportDiseaseCases));
        mainPanel.add(createButton("View Clinic Statistics", this::viewClinicStatistics));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 45));
        button.addActionListener(e -> action.run());
        return button;
    }
    
    private void requestVaccineShipment() {
        RequestVaccineShipmentJPanel panel = new RequestVaccineShipmentJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("RequestVaccineShipment", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void manageClinicStaff() {
        ManageClinicStaffJPanel panel = new ManageClinicStaffJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ManageClinicStaff", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void scheduleAppointments() {
        SchedulePatientAppointmentsJPanel panel = new SchedulePatientAppointmentsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("SchedulePatientAppointments", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void viewVaccineInventory() {
        ViewVaccineInventoryJPanel panel = new ViewVaccineInventoryJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewVaccineInventory", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void reportDiseaseCases() {
        ReportDiseaseCasesJPanel panel = new ReportDiseaseCasesJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ReportDiseaseCases", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void viewClinicStatistics() {
        ViewClinicStatisticsJPanel panel = new ViewClinicStatisticsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewClinicStatistics", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
