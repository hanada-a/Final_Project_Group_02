package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.DataAnalystRole.DataAnalystWorkAreaJPanel;

/**
 * Data Analyst role - processes data requests and generates reports
 */
public class DataAnalystRole extends Role {
    
    public DataAnalystRole() {
        this.type = RoleType.DataAnalyst;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new DataAnalystWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
