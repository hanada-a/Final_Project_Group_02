package business.Domain;

import business.Util.IDGenerator;

/**
 * Represents a disease in the public health system
 */
public class Disease {
    
    public enum Severity {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        CRITICAL("Critical");
        
        private String value;
        
        Severity(String value) {
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
    
    private String diseaseID;
    private String name;
    private String description;
    private Severity severity;
    private boolean isContagious;
    private String symptoms;
    private int incubationPeriod; // in days
    private boolean requiresReporting;
    
    public Disease() {
        this.diseaseID = IDGenerator.generateDiseaseID();
        this.requiresReporting = true;
    }
    
    public Disease(String name, Severity severity, boolean isContagious) {
        this();
        this.name = name;
        this.severity = severity;
        this.isContagious = isContagious;
    }

    // Getters and Setters
    public String getDiseaseID() {
        return diseaseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public boolean isContagious() {
        return isContagious;
    }

    public void setContagious(boolean contagious) {
        isContagious = contagious;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public int getIncubationPeriod() {
        return incubationPeriod;
    }

    public void setIncubationPeriod(int incubationPeriod) {
        this.incubationPeriod = incubationPeriod;
    }

    public boolean isRequiresReporting() {
        return requiresReporting;
    }

    public void setRequiresReporting(boolean requiresReporting) {
        this.requiresReporting = requiresReporting;
    }
    
    @Override
    public String toString() {
        return name + " (" + severity + ")";
    }
}
