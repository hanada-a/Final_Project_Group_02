package ui.HospitalAdminRole;

import business.Business;
import business.Employee.Employee;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for hospital admins to manage hospital staff
 */
public class ManageHospitalStaffJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private JTable staffTable;
    private DefaultTableModel tableModel;
    
    public ManageHospitalStaffJPanel(JPanel userProcessContainer, UserAccount account,
                                    Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel titleLabel = new JLabel("Manage Hospital Staff");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info label
        JLabel infoLabel = new JLabel("Hospital Staff Directory - " + organization.getName());
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(infoLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Employee ID", "Name", "Email", "Phone", "Role", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        staffTable = new JTable(tableModel);
        staffTable.setRowHeight(25);
        
        loadStaff();
        
        mainPanel.add(new JScrollPane(staffTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadStaff();
            JOptionPane.showMessageDialog(this, "Staff list refreshed", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewStaffDetails());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadStaff() {
        tableModel.setRowCount(0);
        
        for (Employee employee : organization.getEmployeeDirectory().getEmployeeList()) {
            String role = "Staff";
            for (UserAccount ua : organization.getUserAccountDirectory().getUserAccountList()) {
                if (ua.getEmployee() != null && ua.getEmployee().equals(employee)) {
                    role = ua.getRole() != null ? ua.getRole().toString() : "Staff";
                    break;
                }
            }
            
            tableModel.addRow(new Object[]{
                employee.getEmployeeID(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                role,
                employee.isActive() ? "Active" : "Inactive"
            });
        }
    }
    
    private void viewStaffDetails() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a staff member", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String id = (String) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        String email = (String) tableModel.getValueAt(selectedRow, 2);
        String phone = (String) tableModel.getValueAt(selectedRow, 3);
        String role = (String) tableModel.getValueAt(selectedRow, 4);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        String details = String.format(
            "Staff Member Details\n\n" +
            "Employee ID: %s\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Phone: %s\n" +
            "Role: %s\n" +
            "Status: %s\n" +
            "Organization: %s",
            id, name, email, phone, role, status, organization.getName()
        );
        
        JOptionPane.showMessageDialog(this, details, 
            "Staff Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
