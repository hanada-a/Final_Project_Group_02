package business.UserAccount;

import business.Employee.Employee;
import business.Role.Role;
import business.Util.PasswordValidator;
import java.util.ArrayList;

/**
 * Enhanced UserAccountDirectory with secure authentication
 *
 * @author Akira Hanada
 */
public class UserAccountDirectory {
    
    private ArrayList<UserAccount> userAccountList;

    public UserAccountDirectory() {
        userAccountList = new ArrayList<>();
    }

    public ArrayList<UserAccount> getUserAccountList() {
        return userAccountList;
    }
    
    public UserAccount authenticateUser(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equals(username)) {
                // Use password hashing for authentication
                if (PasswordValidator.verifyPassword(password, ua.getPassword())) {
                    return ua;
                }
                // If verification fails, increment failed attempts
                ua.incrementFailedLoginAttempts();
                return null;
            }
        }
        return null;
    }
    
    public UserAccount createUserAccount(String username, String password, Employee employee, Role role) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        // Hash password before storing
        userAccount.setPassword(PasswordValidator.hashPassword(password));
        userAccount.setEmployee(employee);
        userAccount.setRole(role);
        userAccountList.add(userAccount);
        return userAccount;
    }
    
    public boolean checkUsernameExists(String username) {
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
