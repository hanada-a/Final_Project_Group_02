package business.Organization;

import business.Role.*;
import java.util.ArrayList;

/**
 * Regional Hospital organization
 * 
 * @author Akira Hanada
 * @author Maxwell Sowell
 */
public class HospitalOrganization extends Organization {
    
    public HospitalOrganization(String name) {
        super(name);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new HospitalAdminRole());
        roles.add(new NursePractitionerRole());
        roles.add(new VaccineStorageSpecialistRole());
        return roles;
    }
}
