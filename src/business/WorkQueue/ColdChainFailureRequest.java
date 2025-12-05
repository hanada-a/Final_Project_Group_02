/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business.WorkQueue;

import business.Domain.Vaccine;
import java.util.Date;

/**
 * Cross-enterprise (Provider's Vaccine Storage Specialist -> State's Provider Coordinator) work request for reporting vaccine cold chain failures
 * 
 * @author Maxwell Sowell
 */
public class ColdChainFailureRequest extends WorkRequest {
    
    private Vaccine affectedVaccine;
    private int affectedQuantity;
    private Date failureDate;
    private String failureType; // e.g. Temperature Excursion (fancy term!), Equipment Malfunction, Power Failure, Human Error
    private String temperatureRange;
    private String durationOfExposure;
    private String correctiveAction;
    private String replacementStatus;
    
    
    public ColdChainFailureRequest() {
        super();
        this.setStatus("Pending Review");
        this.failureDate = new Date();
    }

    
    public Vaccine getAffectedVaccine() {
        return affectedVaccine;
    }

    public void setAffectedVaccine(Vaccine affectedVaccine) {
        this.affectedVaccine = affectedVaccine;
    }

    public int getAffectedQuantity() {
        return affectedQuantity;
    }

    public void setAffectedQuantity(int affectedQuantity) {
        this.affectedQuantity = affectedQuantity;
    }

    public Date getFailureDate() {
        return failureDate;
    }

    public void setFailureDate(Date failureDate) {
        this.failureDate = failureDate;
    }

    public String getFailureType() {
        return failureType;
    }

    public void setFailureType(String failureType) {
        this.failureType = failureType;
    }

    public String getTemperatureRange() {
        return temperatureRange;
    }

    public void setTemperatureRange(String temperatureRange) {
        this.temperatureRange = temperatureRange;
    }

    public String getDurationOfExposure() {
        return durationOfExposure;
    }

    public void setDurationOfExposure(String durationOfExposure) {
        this.durationOfExposure = durationOfExposure;
    }

    public String getCorrectiveAction() {
        return correctiveAction;
    }

    public void setCorrectiveAction(String correctiveAction) {
        this.correctiveAction = correctiveAction;
    }

    public String getReplacementStatus() {
        return replacementStatus;
    }

    public void setReplacementStatus(String replacementStatus) {
        this.replacementStatus = replacementStatus;
    }
    
    
    @Override
    public String toString() {
        return "Cold Chain Failure: " + (affectedVaccine != null ? affectedVaccine.getName() : "N/A") + " - Qty: " + affectedQuantity + " - " + getStatus();
    }
    
}
