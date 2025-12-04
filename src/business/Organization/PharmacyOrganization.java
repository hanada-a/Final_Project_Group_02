/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business.Organization;

import business.Role.Role;
import java.util.ArrayList;

/***
 * Pharmacy services include prescription fulfillment and medication management
 *
 * @author Maxwell Sowell
 */

public class PharmacyOrganization extends Organization {

    
    public PharmacyOrganization(String name) {
        super(name);
    }

    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new PharmacistRole());
        return roles;
    }  
}
