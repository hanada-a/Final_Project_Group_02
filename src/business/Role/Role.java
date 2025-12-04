/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Role;

import business.Business;
import business.Organization.Organization;
import business.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author raunak
 * @author Akira Hanada
 * @author Maxwell Sowell
 */
public abstract class Role {

    public enum RoleType {
        Admin("System Admin"),
        Epidemiologist("Epidemiologist"),
        DataAnalyst("Data Analyst"),
        DistributionCoordinator("Distribution Coordinator"),
        PublicHealthOfficer("Public Health Officer"),
        ProviderCoordinator("Provider Coordinator"),
        HospitalAdmin("Hospital Administrator"),
        ClinicManager("Clinic Manager"),
        NursePractitioner("Nurse Practitioner"),
        LabTech("Lab Technician"),
        Pharmacist("Pharmacist");

        private String value;

        private RoleType(String value) {
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

    public RoleType type;

    public abstract JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Business business);

    @Override
    public String toString() {
        return (type != null) ? this.type.getValue() : this.getClass().getName();
    }

}
