package ui.PublicHealthOfficerRole;

import business.Business;
import business.Domain.Disease;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;

public class IssueStateHealthAlertJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Business business;
    private JComboBox<String> diseaseCombo;
    private JComboBox<String> severityCombo;
    private JTextArea messageArea;
    
    public IssueStateHealthAlertJPanel(JPanel userProcessContainer, UserAccount account,
                                       Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 20, 60));
        JLabel titleLabel = new JLabel("Issue State Health Alert");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Alert Information"));
        
        formPanel.add(new JLabel("Disease:"));
        diseaseCombo = new JComboBox<>();
        diseaseCombo.addItem("-- Select Disease --");
        for (Disease disease : business.getDiseaseDirectory()) {
            diseaseCombo.addItem(disease.getName());
        }
        formPanel.add(diseaseCombo);
        
        formPanel.add(new JLabel("Severity Level:"));
        severityCombo = new JComboBox<>(new String[]{"Low", "Medium", "High", "Critical"});
        formPanel.add(severityCombo);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // Message panel
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createTitledBorder("Alert Message"));
        
        messageArea = new JTextArea(10, 40);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 12));
        messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton issueButton = new JButton("Issue Alert");
        issueButton.setBackground(new Color(220, 20, 60));
        issueButton.setForeground(Color.WHITE);
        issueButton.setFont(new Font("Arial", Font.BOLD, 14));
        issueButton.addActionListener(e -> issueAlert());
        
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearForm());
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(issueButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void issueAlert() {
        if (diseaseCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a disease", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (messageArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter alert message", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String disease = (String) diseaseCombo.getSelectedItem();
        String severity = (String) severityCombo.getSelectedItem();
        String message = messageArea.getText().trim();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            String.format("Issue state health alert?\n\nDisease: %s\nSeverity: %s\n\nThis will notify all healthcare providers in the state.",
                disease, severity),
            "Confirm Alert",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                "State Health Alert Issued Successfully!\n\n" +
                "All healthcare providers and organizations have been notified.\n" +
                "Alert has been logged in the system.",
                "Alert Issued",
                JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
        }
    }
    
    private void clearForm() {
        diseaseCombo.setSelectedIndex(0);
        severityCombo.setSelectedIndex(0);
        messageArea.setText("");
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
