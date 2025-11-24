package ui.PublicHealthOfficerRole;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import business.WorkQueue.DiseaseReportRequest;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewDiseaseReportsJPanel extends JPanel {
    
    private JPanel userProcessContainer;
    private Organization organization;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    
    public ViewDiseaseReportsJPanel(JPanel userProcessContainer, UserAccount account,
                                    Organization organization, Business business) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(220, 20, 60));
        JLabel titleLabel = new JLabel("View Disease Reports");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"Date", "Disease", "Cases", "Location", "Demographics", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        reportTable = new JTable(tableModel);
        reportTable.setRowHeight(25);
        
        loadReports();
        mainPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton reviewButton = new JButton("Review Report");
        reviewButton.addActionListener(e -> reviewReport());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadReports());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        
        buttonPanel.add(reviewButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadReports() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        for (Object obj : organization.getWorkQueue().getWorkRequestList()) {
            if (obj instanceof DiseaseReportRequest) {
                DiseaseReportRequest request = (DiseaseReportRequest) obj;
                tableModel.addRow(new Object[]{
                    sdf.format(request.getRequestDate()),
                    request.getDisease() != null ? request.getDisease().getName() : "N/A",
                    request.getCaseCount(),
                    request.getLocation(),
                    request.getPatientDemographics(),
                    request.getStatus()
                });
            }
        }
    }
    
    private void reviewReport() {
        int selectedRow = reportTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a report", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String details = String.format("Disease Report Details\n\nDisease: %s\nCases: %s\nLocation: %s\nDemographics: %s\nStatus: %s",
            tableModel.getValueAt(selectedRow, 1),
            tableModel.getValueAt(selectedRow, 2),
            tableModel.getValueAt(selectedRow, 3),
            tableModel.getValueAt(selectedRow, 4),
            tableModel.getValueAt(selectedRow, 5));
        
        JOptionPane.showMessageDialog(this, details, "Report Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void goBack() {
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }
}
