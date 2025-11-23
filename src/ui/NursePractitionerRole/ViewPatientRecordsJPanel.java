package ui.NursePractitionerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel for nurses to view patient records
 */
public class ViewPatientRecordsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    
    public ViewPatientRecordsJPanel(JPanel userProcessContainer, UserAccount account,
                                   Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        
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
        
        String[] columns = {"Patient ID", "Name", "Age", "Last Visit", "Status", "Notes"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patientTable = new JTable(tableModel);
        
        // Add sample patient data
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date today = new java.util.Date();
        
        tableModel.addRow(new Object[]{"P001", "John Smith", "45", sdf.format(today), "Vaccinated", "COVID-19 Dose 2"});
        tableModel.addRow(new Object[]{"P002", "Mary Johnson", "67", "11/15/2024", "Follow-up Needed", "Flu shot administered"});
        tableModel.addRow(new Object[]{"P003", "Robert Williams", "32", "11/10/2024", "Completed", "MMR vaccination"});
        tableModel.addRow(new Object[]{"P004", "Patricia Brown", "54", "11/08/2024", "Vaccinated", "Hepatitis B Dose 1"});
        tableModel.addRow(new Object[]{"P005", "James Davis", "28", "11/05/2024", "Completed", "Annual checkup"});
        
        mainPanel.add(new JScrollPane(patientTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Patient records refreshed", "Info", JOptionPane.INFORMATION_MESSAGE));
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
