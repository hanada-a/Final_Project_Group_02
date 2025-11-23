package business.Organization;

import business.Role.*;
import java.util.ArrayList;

/**
 * Disease Surveillance Division at CDC
 */
public class DiseaseSurveillanceOrganization extends Organization {
    
    public DiseaseSurveillanceOrganization(String name) {
        super(name);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new EpidemiologistRole());
        roles.add(new DataAnalystRole());
        return roles;
    }
}
