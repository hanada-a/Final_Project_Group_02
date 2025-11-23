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
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    
    public NursePractitionerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account,
                                          Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
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
        
        mainPanel.add(createButton("Administer Vaccinations", this::administerVaccinations));
        mainPanel.add(createButton("Report Disease Cases", this::reportDiseaseCases));
        mainPanel.add(createButton("View Patient Records", this::viewPatientRecords));
        mainPanel.add(createButton("Schedule Appointments", this::scheduleAppointments));
        mainPanel.add(createButton("View Vaccine Availability", this::viewVaccineAvailability));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(300, 50));
        button.addActionListener(e -> action.run());
        return button;
    }
    
    private void administerVaccinations() {
        AdministerVaccinationsJPanel panel = new AdministerVaccinationsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("AdministerVaccinations", panel);
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
    
    private void viewPatientRecords() {
        ViewPatientRecordsJPanel panel = new ViewPatientRecordsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewPatientRecords", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void scheduleAppointments() {
        ScheduleAppointmentsJPanel panel = new ScheduleAppointmentsJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ScheduleAppointments", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
    
    private void viewVaccineAvailability() {
        ViewVaccineAvailabilityJPanel panel = new ViewVaccineAvailabilityJPanel(
            userProcessContainer, account, organization, business);
        userProcessContainer.add("ViewVaccineAvailability", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
