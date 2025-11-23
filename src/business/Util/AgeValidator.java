package business.Util;

import java.util.Calendar;
import java.util.Date;

/**
 * Validates age and dates
 */
public class AgeValidator {
    
    public static int calculateAge(Date birthDate) {
        if (birthDate == null) {
            return 0;
        }
        
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthDate);
        
        Calendar today = Calendar.getInstance();
        
        int age = today.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
        
        if (today.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH) ||
            (today.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH) &&
             today.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        
        return age;
    }
    
    public static boolean isValidAge(int age, int minAge, int maxAge) {
        return age >= minAge && age <= maxAge;
    }
    
    public static Date validateBirthDate(Date birthDate, int minAge, int maxAge) throws ValidationException {
        if (birthDate == null) {
            throw new ValidationException("Birth date cannot be empty");
        }
        
        if (birthDate.after(new Date())) {
            throw new ValidationException("Birth date cannot be in the future");
        }
        
        int age = calculateAge(birthDate);
        
        if (age < minAge) {
            throw new ValidationException("Age must be at least " + minAge + " years");
        }
        
        if (age > maxAge) {
            throw new ValidationException("Age cannot exceed " + maxAge + " years");
        }
        
        return birthDate;
    }
}
