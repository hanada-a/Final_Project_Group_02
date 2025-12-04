/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business.Role;

import business.Business;
import business.Organization.Organization;
import business.Organization.PharmacyOrganization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;

/***
 *
 * @author Maxwell Sowell
 */
public class PharmacistRole extends Role {

    public PharmacistRole() {
        this.type = RoleType.Pharmacist;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Business business) {
        return new PharmacistWorkAreaJPanel(userProcessContainer, account, (PharmacyOrganization) organization, business);
    }
}
