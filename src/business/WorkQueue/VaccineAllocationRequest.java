package business.WorkQueue;

import business.Domain.Vaccine;
import business.Organization.Organization;
import java.util.Date;

/**
 * Request for vaccine allocation from state to CDC (Cross-Enterprise)
 */
public class VaccineAllocationRequest extends WorkRequest {
    
    private Vaccine vaccine;
    private int quantityRequested;
    private int quantityApproved;
    private String priority; // "HIGH", "MEDIUM", "LOW"
    private String justification;
    private String targetPopulation; // e.g., "Healthcare Workers", "Elderly 65+"
    private Organization requestingOrganization;
    private Date neededByDate;
    
    public VaccineAllocationRequest() {
        super();
        this.setStatus("Pending");
    }

    // Getters and Setters
    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public int getQuantityRequested() {
        return quantityRequested;
    }

    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    public int getQuantityApproved() {
        return quantityApproved;
    }

    public void setQuantityApproved(int quantityApproved) {
        this.quantityApproved = quantityApproved;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getTargetPopulation() {
        return targetPopulation;
    }

    public void setTargetPopulation(String targetPopulation) {
        this.targetPopulation = targetPopulation;
    }

    public Organization getRequestingOrganization() {
        return requestingOrganization;
    }

    public void setRequestingOrganization(Organization requestingOrganization) {
        this.requestingOrganization = requestingOrganization;
    }

    public Date getNeededByDate() {
        return neededByDate;
    }

    public void setNeededByDate(Date neededByDate) {
        this.neededByDate = neededByDate;
    }
    
    @Override
    public String toString() {
        return "Vaccine Allocation: " + (vaccine != null ? vaccine.getName() : "N/A") + 
               " - Qty: " + quantityRequested + " - " + getStatus();
    }
}
