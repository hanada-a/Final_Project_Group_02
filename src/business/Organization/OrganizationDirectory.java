/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Organization;

import business.Organization.Organization.Type;
import java.util.ArrayList;

/**
 *
 * @author raunak
 * @author Akira Hanada
 * @author Maxwell Sowell
 */
public class OrganizationDirectory {
    
    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList<>();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public Organization createOrganization(Type type){
        Organization organization = null;
        
        if (type.getValue().equals(Type.DiseaseSurveillance.getValue())){
            organization = new DiseaseSurveillanceOrganization(type.getValue());
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.VaccineDistribution.getValue())){
            organization = new VaccineDistributionOrganization(type.getValue());
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.PublicHealthServices.getValue())){
            organization = new PublicHealthServicesOrganization(type.getValue());
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.ProviderRegistry.getValue())){
            organization = new ProviderRegistryOrganization(type.getValue());
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Hospital.getValue())){
            organization = new HospitalOrganization(type.getValue());
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Clinic.getValue())){
            organization = new ClinicOrganization(type.getValue());
            organizationList.add(organization);
        }
        
        else if (type.getValue().equals(Type.Lab.getValue())){
            organization = new LabOrganization(type.getValue());
            organizationList.add(organization);
        }
        
        return organization;
    }
}