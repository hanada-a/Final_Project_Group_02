package business.Organization;

import business.Role.*;
import java.util.ArrayList;

/**
 * Vaccine Distribution Coordination at CDC
 */
public class VaccineDistributionOrganization extends Organization {
    
    public VaccineDistributionOrganization(String name) {
        super(name);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new DistributionCoordinatorRole());
        return roles;
    }
}
