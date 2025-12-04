/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import ui.LabTechRole.LabTechWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author Maxwell Sowell
 */
public class LabTechRole extends Role {

    public LabTechRole() {
        this.type = RoleType.LabTech;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Business business) {
        return new LabTechWorkAreaJPanel(userProcessContainer, account, organization, business);
    }

}
