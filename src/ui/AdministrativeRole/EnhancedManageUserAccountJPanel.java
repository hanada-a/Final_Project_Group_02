package ui.AdministrativeRole;

import business.Business;
import business.Employee.Employee;
import business.Organization.Organization;
import business.Role.*;
import business.UserAccount.UserAccount;
import business.Util.PasswordValidator;
import business.Util.ValidationException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

/**
 * Enhanced User Account Management Panel with full CRUD operations
 * 
 * @author Akira Hanada
 */
public class EnhancedManageUserAccountJPanel extends JPanel {
    
    private Business business;
    private JPanel userProcessContainer;
    
    private JComboBox<Organization> cmbOrganization;
    private JComboBox<Employee> cmbEmployee;
    private JComboBox<String> cmbRole;
    private JTable tblUserAccounts;
    private DefaultTableModel tableModel;
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkEnabled;
    private JCheckBox chkShowPassword;
    
    private JButton btnCreate;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnResetPassword;
    private JButton btnClear;
    private JButton btnBack;
    
    private UserAccount selectedAccount;
    
    public EnhancedManageUserAccountJPanel(JPanel userProcessContainer, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        initComponents();
        populateOrganizationComboBox();
    }
    
    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(156, 39, 176));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        JLabel titleLabel = new JLabel("User Account Management - Full CRUD Operations");
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
        
        splitPane.setDividerLocation(550);
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
        cmbOrganization.addActionListener(e -> loadUserAccounts());
        orgPanel.add(cmbOrganization);
        panel.add(orgPanel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"Username", "Employee", "Role", "Enabled", "Last Login", "Failed Attempts"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblUserAccounts = new JTable(tableModel);
        tblUserAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblUserAccounts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectUserAccount();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tblUserAccounts);
        scrollPane.setBorder(BorderFactory.createTitledBorder("User Accounts"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Account Details"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Instructions
        JLabel instrLabel = new JLabel("<html><i>Click an account to select, or fill form to create new</i></html>");
        instrLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(instrLabel);
        panel.add(Box.createVerticalStrut(15));
        
        // Employee selector
        panel.add(new JLabel("Employee:"));
        cmbEmployee = new JComboBox<>();
        cmbEmployee.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(cmbEmployee);
        panel.add(Box.createVerticalStrut(10));
        
        // Username
        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(txtUsername);
        panel.add(Box.createVerticalStrut(10));
        
        // Password
        panel.add(new JLabel("Password:"));
        JPanel passwordPanel = new JPanel(new BorderLayout());
        txtPassword = new JPasswordField();
        chkShowPassword = new JCheckBox("Show");
        chkShowPassword.addActionListener(e -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char)0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });
        passwordPanel.add(txtPassword, BorderLayout.CENTER);
        passwordPanel.add(chkShowPassword, BorderLayout.EAST);
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(10));
        
        // Role
        panel.add(new JLabel("Role:"));
        cmbRole = new JComboBox<>();
        cmbRole.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(cmbRole);
        panel.add(Box.createVerticalStrut(10));
        
        // Enabled checkbox
        chkEnabled = new JCheckBox("Account Enabled");
        chkEnabled.setSelected(true);
        chkEnabled.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(chkEnabled);
        panel.add(Box.createVerticalStrut(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        btnCreate = new JButton("Create");
        btnCreate.addActionListener(e -> createUserAccount());
        buttonPanel.add(btnCreate);
        
        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateUserAccount());
        btnUpdate.setEnabled(false);
        buttonPanel.add(btnUpdate);
        
        btnResetPassword = new JButton("Reset Password");
        btnResetPassword.addActionListener(e -> resetPassword());
        btnResetPassword.setEnabled(false);
        buttonPanel.add(btnResetPassword);
        
        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteUserAccount());
        btnDelete.setEnabled(false);
        buttonPanel.add(btnDelete);
        
        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearForm());
        buttonPanel.add(btnClear);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadUserAccounts());
        buttonPanel.add(btnRefresh);
        
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panel.add(buttonPanel);
        
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private void populateOrganizationComboBox() {
        cmbOrganization.removeAllItems();
        
        try {
            if (business.getEcoSystem() != null) {
                for (business.Network network : business.getEcoSystem().getNetworkList()) {
                    for (business.Enterprise enterprise : network.getEnterpriseList()) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            cmbOrganization.addItem(org);
                        }
                    }
                }
            }
            
            if (cmbOrganization.getItemCount() > 0) {
                cmbOrganization.setSelectedIndex(0);
                loadUserAccounts();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading organizations: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void populateEmployeeComboBox() {
        cmbEmployee.removeAllItems();
        
        try {
            Organization org = (Organization) cmbOrganization.getSelectedItem();
            if (org != null) {
                for (Employee emp : org.getEmployeeDirectory().getEmployeeList()) {
                    cmbEmployee.addItem(emp);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading employees: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void populateRoleComboBox() {
        cmbRole.removeAllItems();
        
        try {
            Organization org = (Organization) cmbOrganization.getSelectedItem();
            if (org != null) {
                for (Role role : org.getSupportedRole()) {
                    cmbRole.addItem(role.toString());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading roles: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadUserAccounts() {
        tableModel.setRowCount(0);
        clearForm();
        
        try {
            Organization org = (Organization) cmbOrganization.getSelectedItem();
            if (org != null) {
                populateEmployeeComboBox();
                populateRoleComboBox();
                
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                for (UserAccount ua : org.getUserAccountDirectory().getUserAccountList()) {
                    tableModel.addRow(new Object[]{
                        ua.getUsername(),
                        ua.getEmployee() != null ? ua.getEmployee().getName() : "N/A",
                        ua.getRole() != null ? ua.getRole().toString() : "N/A",
                        ua.isEnabled() ? "Yes" : "No",
                        ua.getLastLogin() != null ? sdf.format(ua.getLastLogin()) : "Never",
                        ua.getFailedLoginAttempts()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading user accounts: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void selectUserAccount() {
        int row = tblUserAccounts.getSelectedRow();
        if (row >= 0) {
            try {
                String username = (String) tableModel.getValueAt(row, 0);
                Organization org = (Organization) cmbOrganization.getSelectedItem();
                
                if (org != null) {
                    for (UserAccount ua : org.getUserAccountDirectory().getUserAccountList()) {
                        if (ua.getUsername().equals(username)) {
                            selectedAccount = ua;
                            populateForm(ua);
                            btnUpdate.setEnabled(true);
                            btnDelete.setEnabled(true);
                            btnResetPassword.setEnabled(true);
                            btnCreate.setEnabled(false);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error selecting user account: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateForm(UserAccount ua) {
        txtUsername.setText(ua.getUsername());
        txtPassword.setText(""); // Don't show password
        chkEnabled.setSelected(ua.isEnabled());
        
        // Select employee in combo box
        if (ua.getEmployee() != null) {
            for (int i = 0; i < cmbEmployee.getItemCount(); i++) {
                if (cmbEmployee.getItemAt(i).getEmployeeID().equals(ua.getEmployee().getEmployeeID())) {
                    cmbEmployee.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Select role in combo box
        if (ua.getRole() != null) {
            cmbRole.setSelectedItem(ua.getRole().toString());
        }
    }
    
    private void clearForm() {
        txtUsername.setText("");
        txtPassword.setText("");
        chkEnabled.setSelected(true);
        selectedAccount = null;
        btnCreate.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnResetPassword.setEnabled(false);
        tblUserAccounts.clearSelection();
        
        if (cmbEmployee.getItemCount() > 0) {
            cmbEmployee.setSelectedIndex(0);
        }
        if (cmbRole.getItemCount() > 0) {
            cmbRole.setSelectedIndex(0);
        }
    }
    
    private void createUserAccount() {
        try {
            // Validate inputs
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            
            if (username.isEmpty()) {
                throw new ValidationException("Username is required");
            }
            
            if (password.isEmpty()) {
                throw new ValidationException("Password is required");
            }
            
            if (!PasswordValidator.isStrong(password)) {
                throw new ValidationException("Password must be at least 8 characters with uppercase, lowercase, and digit");
            }
            
            Organization org = (Organization) cmbOrganization.getSelectedItem();
            if (org == null) {
                throw new ValidationException("Please select an organization");
            }
            
            // Check for duplicate username
            for (UserAccount ua : org.getUserAccountDirectory().getUserAccountList()) {
                if (ua.getUsername().equalsIgnoreCase(username)) {
                    throw new ValidationException("Username already exists");
                }
            }
            
            Employee employee = (Employee) cmbEmployee.getSelectedItem();
            if (employee == null) {
                throw new ValidationException("Please select an employee");
            }
            
            String roleName = (String) cmbRole.getSelectedItem();
            if (roleName == null) {
                throw new ValidationException("Please select a role");
            }
            
            // Find role object
            Role selectedRole = null;
            for (Role role : org.getSupportedRole()) {
                if (role.toString().equals(roleName)) {
                    selectedRole = role;
                    break;
                }
            }
            
            if (selectedRole == null) {
                throw new ValidationException("Invalid role selected");
            }
            
            // Create user account
            UserAccount newAccount = new UserAccount();
            newAccount.setUsername(username);
            newAccount.setPassword(PasswordValidator.hashPassword(password));
            newAccount.setEmployee(employee);
            newAccount.setRole(selectedRole);
            newAccount.setEnabled(chkEnabled.isSelected());
            org.getUserAccountDirectory().getUserAccountList().add(newAccount);
            
            JOptionPane.showMessageDialog(this,
                "User account created successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadUserAccounts();
            clearForm();
            
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error creating user account: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateUserAccount() {
        if (selectedAccount == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a user account to update",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Update only non-password fields
            Employee employee = (Employee) cmbEmployee.getSelectedItem();
            if (employee == null) {
                throw new ValidationException("Please select an employee");
            }
            
            String roleName = (String) cmbRole.getSelectedItem();
            if (roleName == null) {
                throw new ValidationException("Please select a role");
            }
            
            // Find role object
            Organization org = (Organization) cmbOrganization.getSelectedItem();
            Role selectedRole = null;
            if (org != null) {
                for (Role role : org.getSupportedRole()) {
                    if (role.toString().equals(roleName)) {
                        selectedRole = role;
                        break;
                    }
                }
            }
            
            if (selectedRole == null) {
                throw new ValidationException("Invalid role selected");
            }
            
            // Update account
            String newUsername = txtUsername.getText().trim();
            if (!newUsername.isEmpty() && !newUsername.equals(selectedAccount.getUsername())) {
                // Check if new username already exists
                for (UserAccount ua : org.getUserAccountDirectory().getUserAccountList()) {
                    if (ua != selectedAccount && ua.getUsername().equals(newUsername)) {
                        throw new ValidationException("Username '" + newUsername + "' already exists");
                    }
                }
                selectedAccount.setUsername(newUsername);
            }
            
            selectedAccount.setEmployee(employee);
            selectedAccount.setRole(selectedRole);
            selectedAccount.setEnabled(chkEnabled.isSelected());
            
            JOptionPane.showMessageDialog(this,
                "User account updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadUserAccounts();
            clearForm();
            
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating user account: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetPassword() {
        if (selectedAccount == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a user account to reset password",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String password = new String(txtPassword.getPassword());
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a new password",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            if (!PasswordValidator.isStrong(password)) {
                throw new ValidationException("Password must be at least 8 characters with uppercase, lowercase, and digit");
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reset password for user: " + selectedAccount.getUsername() + "?",
                "Confirm Password Reset",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                selectedAccount.setPassword(PasswordValidator.hashPassword(password));
                selectedAccount.setFailedLoginAttempts(0);
                selectedAccount.setEnabled(true);
                
                JOptionPane.showMessageDialog(this,
                    "Password reset successfully! Failed attempts cleared and account enabled.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadUserAccounts();
                clearForm();
            }
            
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error resetting password: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteUserAccount() {
        if (selectedAccount == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a user account to delete",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete user account: " + selectedAccount.getUsername() + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Organization org = (Organization) cmbOrganization.getSelectedItem();
                if (org != null) {
                    org.getUserAccountDirectory().getUserAccountList().remove(selectedAccount);
                    
                    JOptionPane.showMessageDialog(this,
                        "User account deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    loadUserAccounts();
                    clearForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error deleting user account: " + e.getMessage(),
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
