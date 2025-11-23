package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.NursePractitionerRole.NursePractitionerWorkAreaJPanel;

/**
 * Nurse Practitioner role - provides patient care and reports diseases
 */
public class NursePractitionerRole extends Role {
    
    public NursePractitionerRole() {
        this.type = RoleType.NursePractitioner;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new NursePractitionerWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
