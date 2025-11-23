package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.HospitalAdminRole.HospitalAdminWorkAreaJPanel;

/**
 * Hospital Administrator role - manages hospital operations
 */
public class HospitalAdminRole extends Role {
    
    public HospitalAdminRole() {
        this.type = RoleType.HospitalAdmin;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new HospitalAdminWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
