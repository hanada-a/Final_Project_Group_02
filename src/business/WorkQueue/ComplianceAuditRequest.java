package business.WorkQueue;

import business.Organization.Organization;
import java.util.Date;

/**
 * Compliance audit request from state to provider (Cross-Enterprise)
 */
public class ComplianceAuditRequest extends WorkRequest {
    
    private Organization auditedOrganization;
    private String auditType; // "Vaccine Storage", "Reporting Compliance", "Safety Protocols"
    private Date scheduledAuditDate;
    private Date completedAuditDate;
    private String findings;
    private String complianceScore; // "Excellent", "Good", "Needs Improvement", "Non-Compliant"
    private String recommendedActions;
    private boolean followUpRequired;
    
    public ComplianceAuditRequest() {
        super();
        this.setStatus("Scheduled");
    }

    // Getters and Setters
    public Organization getAuditedOrganization() {
        return auditedOrganization;
    }

    public void setAuditedOrganization(Organization auditedOrganization) {
        this.auditedOrganization = auditedOrganization;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public Date getScheduledAuditDate() {
        return scheduledAuditDate;
    }

    public void setScheduledAuditDate(Date scheduledAuditDate) {
        this.scheduledAuditDate = scheduledAuditDate;
    }

    public Date getCompletedAuditDate() {
        return completedAuditDate;
    }

    public void setCompletedAuditDate(Date completedAuditDate) {
        this.completedAuditDate = completedAuditDate;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getComplianceScore() {
        return complianceScore;
    }

    public void setComplianceScore(String complianceScore) {
        this.complianceScore = complianceScore;
    }

    public String getRecommendedActions() {
        return recommendedActions;
    }

    public void setRecommendedActions(String recommendedActions) {
        this.recommendedActions = recommendedActions;
    }

    public boolean isFollowUpRequired() {
        return followUpRequired;
    }

    public void setFollowUpRequired(boolean followUpRequired) {
        this.followUpRequired = followUpRequired;
    }
    
    @Override
    public String toString() {
        return "Compliance Audit: " + auditType + " - " + 
               (auditedOrganization != null ? auditedOrganization.getName() : "N/A") + 
               " - " + getStatus();
    }
}
