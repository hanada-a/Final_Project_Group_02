package business.WorkQueue;

import business.Domain.Vaccine;
import java.util.Date;

/**
 * Patient appointment request from clinic to hospital (Same Enterprise, Cross-Organization)
 */
public class PatientAppointmentRequest extends WorkRequest {
    
    private String patientName;
    private String patientPhone;
    private String patientEmail;
    private Vaccine vaccine;
    private Date preferredDate;
    private Date scheduledDate;
    private String appointmentType; // "First Dose", "Second Dose", "Booster"
    private String medicalNotes;
    private boolean confirmed;
    
    public PatientAppointmentRequest() {
        super();
        this.setStatus("Requested");
        this.confirmed = false;
    }

    // Getters and Setters
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Date getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(Date preferredDate) {
        this.preferredDate = preferredDate;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getMedicalNotes() {
        return medicalNotes;
    }

    public void setMedicalNotes(String medicalNotes) {
        this.medicalNotes = medicalNotes;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    
    @Override
    public String toString() {
        return "Appointment: " + patientName + " - " + 
               (vaccine != null ? vaccine.getName() : "N/A") + " - " + getStatus();
    }
}
