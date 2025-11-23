package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.ClinicManagerRole.ClinicManagerWorkAreaJPanel;

/**
 * Clinic Manager role - manages clinic operations and vaccine requests
 */
public class ClinicManagerRole extends Role {
    
    public ClinicManagerRole() {
        this.type = RoleType.ClinicManager;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new ClinicManagerWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
