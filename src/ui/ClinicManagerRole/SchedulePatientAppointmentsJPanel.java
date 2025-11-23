package ui.ClinicManagerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.PatientAppointmentRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for clinic managers to schedule patient appointments
 */
public class SchedulePatientAppointmentsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JTextField patientNameField;
    private JComboBox<String> serviceTypeComboBox;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField contactField;
    
    public SchedulePatientAppointmentsJPanel(JPanel userProcessContainer, UserAccount account,
                                            Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        JLabel titleLabel = new JLabel("Schedule Patient Appointments");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left: Appointments table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Scheduled Appointments"));
        
        String[] columns = {"Date", "Time", "Patient", "Service", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentTable = new JTable(tableModel);
        leftPanel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        
        // Right: Schedule form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("New Appointment"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Patient Name
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        patientNameField = new JTextField(20);
        rightPanel.add(patientNameField, gbc);
        
        // Contact
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Contact Phone:"), gbc);
        gbc.gridx = 1;
        contactField = new JTextField(20);
        rightPanel.add(contactField, gbc);
        
        // Service Type
        gbc.gridx = 0; gbc.gridy = 2;
        rightPanel.add(new JLabel("Service Type:"), gbc);
        gbc.gridx = 1;
        serviceTypeComboBox = new JComboBox<>(new String[]{
            "COVID-19 Vaccination",
            "Flu Shot",
            "MMR Vaccination",
            "General Consultation",
            "Disease Screening",
            "Follow-up Visit"
        });
        rightPanel.add(serviceTypeComboBox, gbc);
        
        // Date
        gbc.gridx = 0; gbc.gridy = 3;
        rightPanel.add(new JLabel("Date (MM/DD/YYYY):"), gbc);
        gbc.gridx = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateField = new JTextField(sdf.format(new java.util.Date()), 20);
        rightPanel.add(dateField, gbc);
        
        // Time
        gbc.gridx = 0; gbc.gridy = 4;
        rightPanel.add(new JLabel("Time (HH:MM):"), gbc);
        gbc.gridx = 1;
        timeField = new JTextField("10:00", 20);
        rightPanel.add(timeField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton scheduleButton = new JButton("Schedule");
        scheduleButton.addActionListener(e -> scheduleAppointment());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(scheduleButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(400);
        
        add(splitPane, BorderLayout.CENTER);
        
        loadAppointments();
    }
    
    private void loadAppointments() {
        tableModel.setRowCount(0);
        
        for (Object obj : account.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof PatientAppointmentRequest) {
                PatientAppointmentRequest request = (PatientAppointmentRequest) obj;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    "10:00",
                    request.getMessage() != null ? request.getMessage().split(" - ")[0] : "N/A",
                    request.getMessage() != null && request.getMessage().contains(" - ") ? 
                        request.getMessage().split(" - ")[1] : "N/A",
                    request.getStatus()
                });
            }
        }
    }
    
    private void scheduleAppointment() {
        try {
            String patientName = patientNameField.getText().trim();
            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter patient name", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String contact = contactField.getText().trim();
            if (contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter contact phone", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String serviceType = (String) serviceTypeComboBox.getSelectedItem();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            
            if (date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter date and time", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Create appointment request
            PatientAppointmentRequest request = new PatientAppointmentRequest();
            request.setSender(account);
            request.setStatus("Scheduled");
            request.setMessage(patientName + " - " + serviceType + " - Contact: " + contact);
            
            account.getWorkQueue().getWorkRequestList().add(request);
            organization.getWorkQueue().getWorkRequestList().add(request);
            
            // Update table
            tableModel.insertRow(0, new Object[]{
                date,
                time,
                patientName,
                serviceType,
                "Scheduled"
            });
            
            JOptionPane.showMessageDialog(this,
                "Appointment scheduled successfully!\n\n" +
                "Patient: " + patientName + "\n" +
                "Service: " + serviceType + "\n" +
                "Date: " + date + " at " + time + "\n" +
                "Contact: " + contact,
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            patientNameField.setText("");
            contactField.setText("");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            dateField.setText(sdf.format(new java.util.Date()));
            timeField.setText("10:00");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error scheduling appointment: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
