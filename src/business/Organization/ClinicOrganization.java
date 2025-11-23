package business.Organization;

import business.Role.*;
import java.util.ArrayList;

/**
 * Community Clinic organization
 */
public class ClinicOrganization extends Organization {
    
    public ClinicOrganization(String name) {
        super(name);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new ClinicManagerRole());
        roles.add(new NursePractitionerRole());
        return roles;
    }
}
