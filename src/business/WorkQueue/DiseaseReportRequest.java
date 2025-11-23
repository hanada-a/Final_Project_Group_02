package business.WorkQueue;

import business.Domain.Disease;
import business.Organization.Organization;
import java.util.Date;

/**
 * Disease report from provider to state to CDC (Multi-level, Cross-Enterprise)
 */
public class DiseaseReportRequest extends WorkRequest {
    
    private Disease disease;
    private int caseCount;
    private String patientDemographics; // Age group, gender, etc.
    private String location; // City, county
    private Date onsetDate;
    private boolean isOutbreak;
    private String clinicalNotes;
    private Organization reportingOrganization;
    private String reportType; // "Initial", "Follow-up", "Final"
    
    public DiseaseReportRequest() {
        super();
        this.setStatus("Submitted");
        this.reportType = "Initial";
    }

    // Getters and Setters
    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public int getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(int caseCount) {
        this.caseCount = caseCount;
    }

    public String getPatientDemographics() {
        return patientDemographics;
    }

    public void setPatientDemographics(String patientDemographics) {
        this.patientDemographics = patientDemographics;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getOnsetDate() {
        return onsetDate;
    }

    public void setOnsetDate(Date onsetDate) {
        this.onsetDate = onsetDate;
    }

    public boolean isOutbreak() {
        return isOutbreak;
    }

    public void setOutbreak(boolean outbreak) {
        isOutbreak = outbreak;
    }

    public String getClinicalNotes() {
        return clinicalNotes;
    }

    public void setClinicalNotes(String clinicalNotes) {
        this.clinicalNotes = clinicalNotes;
    }

    public Organization getReportingOrganization() {
        return reportingOrganization;
    }

    public void setReportingOrganization(Organization reportingOrganization) {
        this.reportingOrganization = reportingOrganization;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    @Override
    public String toString() {
        return "Disease Report: " + (disease != null ? disease.getName() : "N/A") + 
               " - Cases: " + caseCount + " - " + getStatus();
    }
}
