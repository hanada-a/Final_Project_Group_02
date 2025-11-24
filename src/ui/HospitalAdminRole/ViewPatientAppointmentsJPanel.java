package ui.HospitalAdminRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.PatientAppointmentRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for hospital admins to view patient appointments
 */
public class ViewPatientAppointmentsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private Organization organization;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    
    public ViewPatientAppointmentsJPanel(JPanel userProcessContainer, UserAccount account,
                                        Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("View Patient Appointments");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Patient appointments scheduled at the hospital");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        infoPanel.add(infoLabel);
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Date", "Time", "Patient", "Service Type", "Status", "Requested By"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentTable = new JTable(tableModel);
        appointmentTable.setRowHeight(25);
        
        loadAppointments();
        
        mainPanel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadAppointments();
            JOptionPane.showMessageDialog(this, "Appointments refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton confirmButton = new JButton("Confirm Selected");
        confirmButton.addActionListener(e -> confirmAppointment());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadAppointments() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof PatientAppointmentRequest) {
                PatientAppointmentRequest request = (PatientAppointmentRequest) obj;
                String message = request.getMessage() != null ? request.getMessage() : "";
                String patient = message.contains(" - ") ? message.split(" - ")[0] : "N/A";
                String service = message.contains(" - ") && message.split(" - ").length > 1 ? 
                    message.split(" - ")[1] : "General";
                
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    "10:00 AM",
                    patient,
                    service,
                    request.getStatus(),
                    request.getSender() != null ? request.getSender().getUsername() : "N/A"
                });
            }
        }
    }
    
    private void confirmAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to confirm", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String patient = (String) tableModel.getValueAt(selectedRow, 2);
        String service = (String) tableModel.getValueAt(selectedRow, 3);
        String date = (String) tableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirm appointment?\n\nPatient: " + patient + "\nService: " + service + "\nDate: " + date,
            "Confirm Appointment",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Confirmed", selectedRow, 4);
            JOptionPane.showMessageDialog(this, 
                "Appointment confirmed successfully", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
