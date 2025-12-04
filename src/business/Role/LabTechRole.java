/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import ui.LabAssistantRole.LabAssistantWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author sowell.m
 */
public class LabTechRole extends Role {

    public LabTechRole() {
        // Legacy - RoleType.LabAssistant doesn't exist
        // this.type = RoleType.LabAssistant;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Business business) {
        // Legacy - RoleType.LabAssistant doesn't exist
        // this.type = RoleType.LabAssistant;
        return new LabAssistantWorkAreaJPanel(userProcessContainer, account, organization, business);
    }

}
