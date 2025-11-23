package business.UserAccount;

import business.Employee.Employee;
import business.Role.Role;
import business.Util.IDGenerator;
import business.WorkQueue.WorkQueue;
import java.util.Date;

/**
 * Enhanced UserAccount class with security features
 *
 * @author Akira Hanada
 */
public class UserAccount {
    
    private String userAccountID;
    private String username;
    private String password;
    private Employee employee;
    private Role role;
    private WorkQueue workQueue;
    private boolean enabled;
    private Date lastLogin;
    private int failedLoginAttempts;

    public UserAccount() {
        workQueue = new WorkQueue();
        this.userAccountID = IDGenerator.generateUserAccountID();
        this.enabled = true;
        this.failedLoginAttempts = 0;
    }

    public String getUserAccountID() {
        return userAccountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public void incrementFailedLoginAttempts() {
        this.failedLoginAttempts++;
        if (this.failedLoginAttempts >= 5) {
            this.enabled = false; // Lock account after 5 failed attempts
        }
    }

    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
    }

    @Override
    public String toString() {
        return username;
    }
}