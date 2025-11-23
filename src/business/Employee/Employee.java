/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Employee;

import business.Util.IDGenerator;
import java.util.Date;

/**
 * Enhanced Employee class for Public Health Information Management System
 *
 * @author Akira Hanada
 */
public class Employee {
    
    private String employeeID;
    private String name;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private String position;
    private boolean isActive;

    public Employee() {
        this.employeeID = IDGenerator.generateEmployeeID();
        this.hireDate = new Date();
        this.isActive = true;
    }
    
    public Employee(String name, String email, String phoneNumber, String position) {
        this();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return name + " (" + employeeID + ")";
    }
}
