package ui.HospitalAdminRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

/**
 * Work area for Hospital Administrator role
 */
public class HospitalAdminWorkAreaJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    
    public HospitalAdminWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("Hospital Administrator - " + account.getEmployee().getName());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(createButton("Manage Hospital Staff", this::manageHospitalStaff));
        mainPanel.add(createButton("View Patient Appointments", this::viewPatientAppointments));
        mainPanel.add(createButton("Monitor Vaccine Inventory", this::monitorVaccineInventory));
        mainPanel.add(createButton("Submit Prescription Request", this::submitPrescriptionRequest));
        mainPanel.add(createButton("Coordinate with Clinics", this::coordinateWithClinics));
        mainPanel.add(createButton("Review Compliance Status", this::reviewComplianceStatus));
        mainPanel.add(createButton("Generate Hospital Reports", this::generateHospitalReports));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 45));
        button.addActionListener(e -> action.run());
        return button;
    }
    
    private void manageHospitalStaff() {
        ManageHospitalStaffJPanel panel = new ManageHospitalStaffJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ManageHospitalStaff", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void viewPatientAppointments() {
        ViewPatientAppointmentsJPanel panel = new ViewPatientAppointmentsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewPatientAppointments", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void monitorVaccineInventory() {
        MonitorVaccineInventoryJPanel panel = new MonitorVaccineInventoryJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("MonitorVaccineInventory", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void submitPrescriptionRequest() {
        SubmitPrescriptionJPanel panel = new SubmitPrescriptionJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("SubmitPrescriptionRequest", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void coordinateWithClinics() {
        CoordinateWithClinicsJPanel panel = new CoordinateWithClinicsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("CoordinateWithClinics", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void reviewComplianceStatus() {
        ReviewComplianceStatusJPanel panel = new ReviewComplianceStatusJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ReviewComplianceStatus", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void generateHospitalReports() {
        GenerateHospitalReportsJPanel panel = new GenerateHospitalReportsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("GenerateHospitalReports", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
