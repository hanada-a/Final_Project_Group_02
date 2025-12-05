/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.VaccineStorageSpecialistRole.VaccineStorageSpecialistWorkAreaJPanel;

/**
 * Vaccine cold chain and inventory management role, e.g. as part of a pharmacy/hospital/clinic
 * 
 * @author Maxwell Sowell
 */
public class VaccineStorageSpecialistRole extends Role {
    
    public VaccineStorageSpecialistRole() {
        this.type = RoleType.VaccineStorageSpecialist;
    }

    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Business business) {
        return new VaccineStorageSpecialistWorkAreaJPanel(userProcessContainer, account, organization, business);
    }
    
}
