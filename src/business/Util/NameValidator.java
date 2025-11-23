package business.Util;

/**
 * Validates names (person, organization, etc.)
 */
public class NameValidator {
    
    public static boolean isValid(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        String trimmed = name.trim();
        
        // Name should be at least 2 characters
        if (trimmed.length() < 2) {
            return false;
        }
        
        // Name should only contain letters, spaces, hyphens, and apostrophes
        return trimmed.matches("[a-zA-Z\\s'-]+");
    }
    
    public static String validate(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        
        String trimmed = name.trim();
        
        if (trimmed.length() < 2) {
            throw new ValidationException("Name must be at least 2 characters long");
        }
        
        if (!trimmed.matches("[a-zA-Z\\s'-]+")) {
            throw new ValidationException("Name can only contain letters, spaces, hyphens, and apostrophes");
        }
        
        return trimmed;
    }
}
