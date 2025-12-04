package ui.NursePractitionerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.PatientAppointmentRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for nurses to schedule patient appointments
 */
public class ScheduleAppointmentsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private Business business;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JTextField patientNameField;
    private JComboBox<String> appointmentTypeComboBox;
    private JTextField dateField;
    private JTextField timeField;
    
    public ScheduleAppointmentsJPanel(JPanel userProcessContainer, UserAccount account,
                                     Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Schedule Appointments");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left: Appointments table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Scheduled Appointments"));
        
        String[] columns = {"Date", "Time", "Patient", "Type", "Status"};
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
        rightPanel.setBorder(BorderFactory.createTitledBorder("Schedule New Appointment"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Patient Name
        gbc.gridx = 0; gbc.gridy = 0;
        rightPanel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        patientNameField = new JTextField(20);
        rightPanel.add(patientNameField, gbc);
        
        // Appointment Type
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(new JLabel("Appointment Type:"), gbc);
        gbc.gridx = 1;
        appointmentTypeComboBox = new JComboBox<>(new String[]{
            "Vaccination - First Dose",
            "Vaccination - Second Dose",
            "Vaccination - Booster",
            "Follow-up Consultation",
            "Disease Screening",
            "General Checkup"
        });
        rightPanel.add(appointmentTypeComboBox, gbc);
        
        // Date
        gbc.gridx = 0; gbc.gridy = 2;
        rightPanel.add(new JLabel("Date (MM/DD/YYYY):"), gbc);
        gbc.gridx = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateField = new JTextField(sdf.format(new java.util.Date()), 20);
        rightPanel.add(dateField, gbc);
        
        // Time
        gbc.gridx = 0; gbc.gridy = 3;
        rightPanel.add(new JLabel("Time (HH:MM):"), gbc);
        gbc.gridx = 1;
        timeField = new JTextField("09:00", 20);
        rightPanel.add(timeField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton scheduleButton = new JButton("Schedule");
        scheduleButton.addActionListener(e -> scheduleAppointment());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(scheduleButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        rightPanel.add(buttonPanel, gbc);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(400);
        
        add(splitPane, BorderLayout.CENTER);
        
        loadAppointments();
    }
    
    private void loadAppointments() {
        tableModel.setRowCount(0);
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof PatientAppointmentRequest) {
                PatientAppointmentRequest request = (PatientAppointmentRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "";
                String patient = message.contains(" - ") ? message.split(" - ")[0] : "N/A";
                String type = request.getAppointmentType() != null ? request.getAppointmentType() : 
                    (message.contains(" - ") && message.split(" - ").length > 1 ? message.split(" - ")[1] : "Appointment");
                
                tableModel.addRow(new Object[]{
                    request.getRequestDate() != null ? new SimpleDateFormat("MM/dd/yyyy").format(request.getRequestDate()) : "N/A",
                    "09:00",
                    patient,
                    type,
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
            
            String appointmentType = (String) appointmentTypeComboBox.getSelectedItem();
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
            request.setMessage(patientName + " - " + appointmentType);
            
            account.getWorkQueue().getWorkRequestList().add(request);
            organization.getWorkQueue().getWorkRequestList().add(request);
            
            // Update table
            tableModel.insertRow(0, new Object[]{
                date,
                time,
                patientName,
                appointmentType,
                "Scheduled"
            });
            
            JOptionPane.showMessageDialog(this,
                "Appointment scheduled successfully!\n" +
                "Patient: " + patientName + "\n" +
                "Type: " + appointmentType + "\n" +
                "Date: " + date + " at " + time,
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            patientNameField.setText("");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            dateField.setText(sdf.format(new java.util.Date()));
            timeField.setText("09:00");
            
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
