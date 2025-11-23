package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.EpidemiologistRole.EpidemiologistWorkAreaJPanel;

/**
 * Epidemiologist role - analyzes disease patterns and issues alerts
 */
public class EpidemiologistRole extends Role {
    
    public EpidemiologistRole() {
        this.type = RoleType.Epidemiologist;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new EpidemiologistWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
