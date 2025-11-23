package business;

import business.Employee.EmployeeDirectory;
import business.Organization.Organization;
import business.Organization.OrganizationDirectory;
import business.Role.Role;
import business.UserAccount.UserAccountDirectory;
import business.WorkQueue.WorkQueue;
import java.util.ArrayList;

/**
 * Represents the top-level System in the hierarchy
 * System → Network → Enterprise → Organization
 */
public class EcoSystem extends Organization {
    
    private ArrayList<Network> networkList;
    
    public EcoSystem(String name) {
        super(name);
        this.networkList = new ArrayList<>();
    }
    
    public ArrayList<Network> getNetworkList() {
        return networkList;
    }
    
    public Network createNetwork(String name) {
        Network network = new Network(name);
        networkList.add(network);
        return network;
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}
