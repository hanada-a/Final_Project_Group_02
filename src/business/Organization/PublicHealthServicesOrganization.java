package business.Organization;

import business.Role.*;
import java.util.ArrayList;

/**
 * Public Health Services at State Health Department
 */
public class PublicHealthServicesOrganization extends Organization {
    
    public PublicHealthServicesOrganization(String name) {
        super(name);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new PublicHealthOfficerRole());
        return roles;
    }
}
