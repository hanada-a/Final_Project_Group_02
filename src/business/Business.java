/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import business.Domain.Disease;
import business.Domain.Vaccine;
import java.util.ArrayList;

/**
 * Central Business class - Singleton pattern
 * Manages the entire ecosystem
 * 
 * @author Akira Hanada
 */
public class Business {

    private static Business business;
    private EcoSystem ecoSystem;
    private ArrayList<Vaccine> vaccineDirectory;
    private ArrayList<Disease> diseaseDirectory;

    public static Business getInstance() {
        if (business == null) {
            business = new Business();
        }
        return business;
    }

    private Business() {
        this.vaccineDirectory = new ArrayList<>();
        this.diseaseDirectory = new ArrayList<>();
    }

    public EcoSystem getEcoSystem() {
        return ecoSystem;
    }

    public void setEcoSystem(EcoSystem ecoSystem) {
        this.ecoSystem = ecoSystem;
    }

    public ArrayList<Vaccine> getVaccineDirectory() {
        return vaccineDirectory;
    }

    public ArrayList<Disease> getDiseaseDirectory() {
        return diseaseDirectory;
    }
}
