package ui.AdministrativeRole;

import business.EcoSystem;
import business.Employee.Employee;
import business.Organization.Organization;
import business.Util.EmailValidator;
import business.Util.PhoneValidator;
import business.Util.NameValidator;
import business.Util.ValidationException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Enhanced Employee Management Panel with full CRUD operations
 * 
 * @author Akira Hanada
 */
public class EnhancedManageEmployeeJPanel extends JPanel {
    
    private EcoSystem ecoSystem;
    private JPanel userProcessContainer;
    
    private JComboBox<Organization> cmbOrganization;
    private JTable tblEmployees;
    private DefaultTableModel tableModel;
    
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtPosition;
    
    private JButton btnCreate;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnBack;
    
    private Employee selectedEmployee;
    
    public EnhancedManageEmployeeJPanel(JPanel userProcessContainer, EcoSystem ecoSystem) {
        this.userProcessContainer = userProcessContainer;
        this.ecoSystem = ecoSystem;
        
        setLayout(new BorderLayout());
        initComponents();
        populateOrganizationComboBox();
    }
    
    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        JLabel titleLabel = new JLabel("Employee Management - Full CRUD Operations");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel with split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left side - Table
        JPanel tablePanel = createTablePanel();
        splitPane.setLeftComponent(tablePanel);
        
        // Right side - Form
        JPanel formPanel = createFormPanel();
        splitPane.setRightComponent(formPanel);
        
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> navigateBack());
        footerPanel.add(btnBack);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Organization selector
        JPanel orgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        orgPanel.add(new JLabel("Organization:"));
        cmbOrganization = new JComboBox<>();
        cmbOrganization.setPreferredSize(new Dimension(300, 25));
        cmbOrganization.addActionListener(e -> loadEmployees());
        orgPanel.add(cmbOrganization);
        panel.add(orgPanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"ID", "Name", "Email", "Phone", "Position"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblEmployees = new JTable(tableModel);
        tblEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblEmployees.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectEmployee();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tblEmployees);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Employee List"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Instructions
        JLabel instrLabel = new JLabel("<html><i>Click an employee to select, or fill form to create new</i></html>");
        instrLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(instrLabel);
        panel.add(Box.createVerticalStrut(15));
        
        // Name
        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(txtName);
        panel.add(Box.createVerticalStrut(10));
        
        // Email
        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(txtEmail);
        panel.add(Box.createVerticalStrut(10));
        
        // Phone
        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        txtPhone.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(txtPhone);
        panel.add(Box.createVerticalStrut(10));
        
        // Position
        panel.add(new JLabel("Position:"));
        txtPosition = new JTextField();
        txtPosition.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(txtPosition);
        panel.add(Box.createVerticalStrut(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        btnCreate = new JButton("Create");
        btnCreate.addActionListener(e -> createEmployee());
        buttonPanel.add(btnCreate);
        
        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateEmployee());
        btnUpdate.setEnabled(false);
        buttonPanel.add(btnUpdate);
        
        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteEmployee());
        btnDelete.setEnabled(false);
        buttonPanel.add(btnDelete);
        
        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearForm());
        buttonPanel.add(btnClear);
        
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panel.add(buttonPanel);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private void populateOrganizationComboBox() {
        cmbOrganization.removeAllItems();
        
        try {
            if (ecoSystem != null) {
                for (business.Network network : ecoSystem.getNetworkList()) {
                    for (business.Enterprise enterprise : network.getEnterpriseList()) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            cmbOrganization.addItem(org);
                        }
                    }
                }
            }
            
            if (cmbOrganization.getItemCount() > 0) {
                cmbOrganization.setSelectedIndex(0);
                loadEmployees();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading organizations: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadEmployees() {
        tableModel.setRowCount(0);
        clearForm();
        
        try {
            Organization org = (Organization) cmbOrganization.getSelectedItem();
            if (org != null) {
                for (Employee emp : org.getEmployeeDirectory().getEmployeeList()) {
                    tableModel.addRow(new Object[]{
                        emp.getEmployeeID(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),
                        emp.getPosition()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading employees: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void selectEmployee() {
        int row = tblEmployees.getSelectedRow();
        if (row >= 0) {
            try {
                String employeeID = (String) tableModel.getValueAt(row, 0);
                Organization org = (Organization) cmbOrganization.getSelectedItem();
                
                if (org != null) {
                    for (Employee emp : org.getEmployeeDirectory().getEmployeeList()) {
                        if (emp.getEmployeeID().equals(employeeID)) {
                            selectedEmployee = emp;
                            populateForm(emp);
                            btnUpdate.setEnabled(true);
                            btnDelete.setEnabled(true);
                            btnCreate.setEnabled(false);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error selecting employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateForm(Employee emp) {
        txtName.setText(emp.getName());
        txtEmail.setText(emp.getEmail());
        txtPhone.setText(emp.getPhoneNumber());
        txtPosition.setText(emp.getPosition());
    }
    
    private void clearForm() {
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPosition.setText("");
        selectedEmployee = null;
        btnCreate.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        tblEmployees.clearSelection();
    }
    
    private void createEmployee() {
        try {
            // Validate inputs
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String position = txtPosition.getText().trim();
            
            if (name.isEmpty()) {
                throw new ValidationException("Employee name is required");
            }
            
            if (!NameValidator.isValid(name)) {
                throw new ValidationException("Invalid name format. Use only letters, spaces, hyphens, and apostrophes.");
            }
            
            if (!email.isEmpty() && !EmailValidator.isValid(email)) {
                throw new ValidationException("Invalid email format");
            }
            
            if (!phone.isEmpty() && !PhoneValidator.isValid(phone)) {
                throw new ValidationException("Invalid phone format. Use (XXX) XXX-XXXX format");
            }
            
            Organization org = (Organization) cmbOrganization.getSelectedItem();
            if (org == null) {
                throw new ValidationException("Please select an organization");
            }
            
            // Create employee
            Employee newEmployee = new Employee(name, email, phone, position);
            org.getEmployeeDirectory().getEmployeeList().add(newEmployee);
            
            JOptionPane.showMessageDialog(this,
                "Employee created successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadEmployees();
            clearForm();
            
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error creating employee: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateEmployee() {
        if (selectedEmployee == null) {
            JOptionPane.showMessageDialog(this,
                "Please select an employee to update",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Validate inputs
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String position = txtPosition.getText().trim();
            
            if (name.isEmpty()) {
                throw new ValidationException("Employee name is required");
            }
            
            if (!NameValidator.isValid(name)) {
                throw new ValidationException("Invalid name format");
            }
            
            if (!email.isEmpty() && !EmailValidator.isValid(email)) {
                throw new ValidationException("Invalid email format");
            }
            
            if (!phone.isEmpty() && !PhoneValidator.isValid(phone)) {
                throw new ValidationException("Invalid phone format");
            }
            
            // Update employee
            selectedEmployee.setName(name);
            selectedEmployee.setEmail(email);
            selectedEmployee.setPhoneNumber(phone);
            selectedEmployee.setPosition(position);
            
            JOptionPane.showMessageDialog(this,
                "Employee updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadEmployees();
            clearForm();
            
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating employee: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteEmployee() {
        if (selectedEmployee == null) {
            JOptionPane.showMessageDialog(this,
                "Please select an employee to delete",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete employee: " + selectedEmployee.getName() + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Organization org = (Organization) cmbOrganization.getSelectedItem();
                if (org != null) {
                    org.getEmployeeDirectory().getEmployeeList().remove(selectedEmployee);
                    
                    JOptionPane.showMessageDialog(this,
                        "Employee deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    loadEmployees();
                    clearForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error deleting employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void navigateBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
