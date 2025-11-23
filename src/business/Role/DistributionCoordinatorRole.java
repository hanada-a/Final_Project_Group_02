package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.DistributionCoordinatorRole.DistributionCoordinatorWorkAreaJPanel;

/**
 * Distribution Coordinator role - manages vaccine allocation and distribution
 */
public class DistributionCoordinatorRole extends Role {
    
    public DistributionCoordinatorRole() {
        this.type = RoleType.DistributionCoordinator;
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                 Organization organization, Business business) {
        return new DistributionCoordinatorWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
}
