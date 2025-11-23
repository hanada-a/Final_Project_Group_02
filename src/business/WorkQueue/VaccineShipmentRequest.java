package business.WorkQueue;

import business.Domain.Vaccine;
import business.Organization.Organization;
import java.util.Date;

/**
 * Vaccine shipment request from provider to state (Cross-Organization)
 */
public class VaccineShipmentRequest extends WorkRequest {
    
    private Vaccine vaccine;
    private int quantity;
    private Organization destinationOrganization;
    private String shippingAddress;
    private Date requestedDeliveryDate;
    private Date actualDeliveryDate;
    private String trackingNumber;
    private String storageRequirements;
    private boolean urgentDelivery;
    
    public VaccineShipmentRequest() {
        super();
        this.setStatus("Requested");
    }

    // Getters and Setters
    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Organization getDestinationOrganization() {
        return destinationOrganization;
    }

    public void setDestinationOrganization(Organization destinationOrganization) {
        this.destinationOrganization = destinationOrganization;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStorageRequirements() {
        return storageRequirements;
    }

    public void setStorageRequirements(String storageRequirements) {
        this.storageRequirements = storageRequirements;
    }

    public boolean isUrgentDelivery() {
        return urgentDelivery;
    }

    public void setUrgentDelivery(boolean urgentDelivery) {
        this.urgentDelivery = urgentDelivery;
    }
    
    @Override
    public String toString() {
        return "Vaccine Shipment: " + (vaccine != null ? vaccine.getName() : "N/A") + 
               " - Qty: " + quantity + " - " + getStatus();
    }
}
