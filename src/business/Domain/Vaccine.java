package business.Domain;

import business.Util.IDGenerator;
import java.util.Date;

/**
 * Represents a vaccine in the public health system
 */
public class Vaccine {
    
    private String vaccineID;
    private String name;
    private String manufacturer;
    private String lotNumber;
    private Date expirationDate;
    private int dosesRequired;
    private int daysBetweenDoses;
    private String storageTemperature; // e.g., "-70°C to -80°C"
    private String targetDiseases; // Comma-separated disease names
    private boolean isActive;
    
    public Vaccine() {
        this.vaccineID = IDGenerator.generateVaccineID();
        this.isActive = true;
    }
    
    public Vaccine(String name, String manufacturer, int dosesRequired) {
        this();
        this.name = name;
        this.manufacturer = manufacturer;
        this.dosesRequired = dosesRequired;
    }

    // Getters and Setters
    public String getVaccineID() {
        return vaccineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getDosesRequired() {
        return dosesRequired;
    }

    public void setDosesRequired(int dosesRequired) {
        this.dosesRequired = dosesRequired;
    }

    public int getDaysBetweenDoses() {
        return daysBetweenDoses;
    }

    public void setDaysBetweenDoses(int daysBetweenDoses) {
        this.daysBetweenDoses = daysBetweenDoses;
    }

    public String getStorageTemperature() {
        return storageTemperature;
    }

    public void setStorageTemperature(String storageTemperature) {
        this.storageTemperature = storageTemperature;
    }

    public String getTargetDiseases() {
        return targetDiseases;
    }

    public void setTargetDiseases(String targetDiseases) {
        this.targetDiseases = targetDiseases;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString() {
        return name + " (" + manufacturer + ")";
    }
}
