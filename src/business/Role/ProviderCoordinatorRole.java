package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.ProviderCoordinatorRole.ProviderCoordinatorWorkAreaJPanel;

/**
 * Provider Coordinator role - manages healthcare provider registry
 */
public class ProviderCoordinatorRole extends Role {
    
    public ProviderCoordinatorRole() {
        this.type = RoleType.ProviderCoordinator;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new ProviderCoordinatorWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
