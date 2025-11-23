package business.WorkQueue;

import business.Domain.Disease;
import business.Organization.Organization;
import java.util.Date;

/**
 * Health data analysis request from CDC to State (Data Request)
 */
public class HealthDataAnalysisRequest extends WorkRequest {
    
    private Disease targetDisease;
    private String analysisType; // "Trend Analysis", "Outbreak Prediction", "Demographic Study"
    private String dataParameters; // What data is needed
    private Date dataStartDate;
    private Date dataEndDate;
    private String geographicScope; // "State-wide", "Regional", "County-specific"
    private Organization dataProvider;
    private String findings;
    private String analysisReport; // Path to report or summary
    
    public HealthDataAnalysisRequest() {
        super();
        this.setStatus("Requested");
    }

    // Getters and Setters
    public Disease getTargetDisease() {
        return targetDisease;
    }

    public void setTargetDisease(Disease targetDisease) {
        this.targetDisease = targetDisease;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public String getDataParameters() {
        return dataParameters;
    }

    public void setDataParameters(String dataParameters) {
        this.dataParameters = dataParameters;
    }

    public Date getDataStartDate() {
        return dataStartDate;
    }

    public void setDataStartDate(Date dataStartDate) {
        this.dataStartDate = dataStartDate;
    }

    public Date getDataEndDate() {
        return dataEndDate;
    }

    public void setDataEndDate(Date dataEndDate) {
        this.dataEndDate = dataEndDate;
    }

    public String getGeographicScope() {
        return geographicScope;
    }

    public void setGeographicScope(String geographicScope) {
        this.geographicScope = geographicScope;
    }

    public Organization getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(Organization dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getAnalysisReport() {
        return analysisReport;
    }

    public void setAnalysisReport(String analysisReport) {
        this.analysisReport = analysisReport;
    }
    
    @Override
    public String toString() {
        return "Data Analysis: " + analysisType + " - " + 
               (targetDisease != null ? targetDisease.getName() : "General") + 
               " - " + getStatus();
    }
}
