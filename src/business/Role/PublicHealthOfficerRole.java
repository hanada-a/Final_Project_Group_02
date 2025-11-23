package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.PublicHealthOfficerRole.PublicHealthOfficerWorkAreaJPanel;

/**
 * Public Health Officer role - coordinates state health initiatives
 */
public class PublicHealthOfficerRole extends Role {
    
    public PublicHealthOfficerRole() {
        this.type = RoleType.PublicHealthOfficer;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new PublicHealthOfficerWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
