/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Organization;

import business.Role.LabTechRole;
import business.Role.Role;
import java.util.ArrayList;

/**
 * Health provider Organization offering laboratory services,
 * including testing and diagnostics
 * 
 * @author sowell.m
 */
public class LabOrganization extends Organization {

    
    public LabOrganization(String name) {
        super(name);
    }

    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new LabTechRole());
        return roles;
    }
    
    
}
