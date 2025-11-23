package business.Organization;

import business.Role.*;
import java.util.ArrayList;

/**
 * Provider Registry at State Health Department
 */
public class ProviderRegistryOrganization extends Organization {
    
    public ProviderRegistryOrganization(String name) {
        super(name);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new ProviderCoordinatorRole());
        return roles;
    }
}
