package business;

import business.Organization.*;
import business.Role.Role;
import java.util.ArrayList;

/**
 * Represents an Enterprise that contains multiple Organizations
 * 
 * @author Akira Hanada
 * @author Maxwell Sowell
 */
public class Enterprise extends Organization {
    
    public enum EnterpriseType {
        CDC("Centers for Disease Control"),
        STATE_HEALTH("State Health Department"),
        HEALTHCARE_PROVIDER("Healthcare Provider Network");
        
        private String value;
        
        EnterpriseType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }
    
    private EnterpriseType enterpriseType;
    
    public Enterprise(String name, EnterpriseType type) {
        super(name);
        this.enterpriseType = type;
    }
    
    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }
    
    public void setEnterpriseType(EnterpriseType enterpriseType) {
        this.enterpriseType = enterpriseType;
    }
    
    // Create organization based on enterprise type
    public Organization createOrganization(String orgName, Organization.Type type) {
        Organization org = null;
        
        switch (type) {
            case Admin:
                org = new AdminOrganization();
                org.setName(orgName);
                break;
            case DiseaseSurveillance:
                org = new DiseaseSurveillanceOrganization(orgName);
                break;
            case VaccineDistribution:
                org = new VaccineDistributionOrganization(orgName);
                break;
            case PublicHealthServices:
                org = new PublicHealthServicesOrganization(orgName);
                break;
            case ProviderRegistry:
                org = new ProviderRegistryOrganization(orgName);
                break;
            case Hospital:
                org = new HospitalOrganization(orgName);
                break;
            case Clinic:
                org = new ClinicOrganization(orgName);
                break;
            case Lab:
                org = new LabOrganization(orgName);
                break; 
            case Pharmacy:
                org = new PharmacyOrganization(orgName);
                break;
            default:
                return null;
        }
        
        if (org != null) {
            getOrganizationDirectory().getOrganizationList().add(org);
        }
        
        return org;
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}
