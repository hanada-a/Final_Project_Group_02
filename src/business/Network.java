package business;

import business.Organization.Organization;
import business.Role.Role;
import java.util.ArrayList;

/**
 * Represents a Network that contains multiple Enterprises
 */
public class Network extends Organization {
    
    private ArrayList<Enterprise> enterpriseList;
    
    public Network(String name) {
        super(name);
        this.enterpriseList = new ArrayList<>();
    }
    
    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }
    
    public Enterprise createEnterprise(String name, Enterprise.EnterpriseType type) {
        Enterprise enterprise = new Enterprise(name, type);
        enterpriseList.add(enterprise);
        return enterprise;
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}
