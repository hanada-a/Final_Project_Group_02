package ui.NursePractitionerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.PatientAppointmentRequest;
import business.WorkQueue.WorkRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for nurses to view patient records
 */
public class ViewPatientRecordsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private Organization organization;
    
    public ViewPatientRecordsJPanel(JPanel userProcessContainer, UserAccount account,
                                   Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("View Patient Records");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"Patient ID", "Name", "Phone", "Last Visit", "Status", "Service/Vaccine"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patientTable = new JTable(tableModel);
        
        // Load patient records from work queue
        loadPatientRecords();
        
        mainPanel.add(new JScrollPane(patientTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadPatientRecords();
            JOptionPane.showMessageDialog(this, 
                "Patient records refreshed", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadPatientRecords() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        // Map to store unique patients (using phone as unique identifier)
        Map<String, Object[]> patientMap = new HashMap<>();
        int patientIdCounter = 1;
        
        // Load from all patient appointment requests in the work queue
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof PatientAppointmentRequest) {
                PatientAppointmentRequest appointment = (PatientAppointmentRequest) request;
                
                String patientPhone = appointment.getPatientPhone();
                String patientName = appointment.getPatientName();
                
                if (patientPhone != null && patientName != null) {
                    // Parse service/vaccine info from message
                    String serviceInfo = "General Visit";
                    if (appointment.getMessage() != null) {
                        String message = appointment.getMessage();
                        // Extract service details (format: "Name - Service" or "Name - Vaccine - Dose")
                        if (message.contains(" - ")) {
                            String[] parts = message.split(" - ");
                            if (parts.length > 1) {
                                serviceInfo = String.join(" - ", Arrays.copyOfRange(parts, 1, parts.length));
                            }
                        }
                    }
                    
                    String visitDate = appointment.getPreferredDate() != null ? 
                        sdf.format(appointment.getPreferredDate()) : 
                        sdf.format(appointment.getRequestDate());
                    
                    String status = appointment.getStatus() != null ? appointment.getStatus() : "Scheduled";
                    
                    // Update patient record if exists, otherwise create new
                    if (patientMap.containsKey(patientPhone)) {
                        Object[] existingRecord = patientMap.get(patientPhone);
                        // Update with most recent visit date
                        try {
                            java.util.Date existingDate = sdf.parse((String) existingRecord[3]);
                            java.util.Date newDate = appointment.getPreferredDate() != null ? 
                                appointment.getPreferredDate() : appointment.getRequestDate();
                            if (newDate.after(existingDate)) {
                                existingRecord[3] = visitDate;
                                existingRecord[4] = status;
                                existingRecord[5] = serviceInfo;
                            }
                        } catch (Exception e) {
                            // Keep existing record if date parsing fails
                        }
                    } else {
                        String patientId = String.format("P%03d", patientIdCounter++);
                        Object[] record = new Object[]{
                            patientId,
                            patientName,
                            patientPhone,
                            visitDate,
                            status,
                            serviceInfo
                        };
                        patientMap.put(patientPhone, record);
                    }
                }
            }
        }
        
        // Add all unique patients to table, sorted by patient ID
        patientMap.values().stream()
            .sorted((a, b) -> ((String)a[0]).compareTo((String)b[0]))
            .forEach(tableModel::addRow);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
