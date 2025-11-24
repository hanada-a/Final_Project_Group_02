/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Organization;

import business.Role.DoctorRole;
import business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class DoctorOrganization extends Organization{

    public DoctorOrganization() {
        // Legacy - Type.Doctor doesn't exist
        super("Doctor"); // Organization.Type.Doctor.getValue()
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new DoctorRole());
        return roles;
    }
     
}