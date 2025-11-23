package business.Util;

import java.util.regex.Pattern;

/**
 * Validates phone numbers
 */
public class PhoneValidator {
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?1?[-.]?\\(?([0-9]{3})\\)?[-.]?([0-9]{3})[-.]?([0-9]{4})$"
    );
    
    public static boolean isValid(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }
    
    public static String validate(String phone) throws ValidationException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone number cannot be empty");
        }
        
        String trimmed = phone.trim();
        if (!PHONE_PATTERN.matcher(trimmed).matches()) {
            throw new ValidationException("Invalid phone format. Use format: (123) 456-7890 or 123-456-7890");
        }
        
        return trimmed;
    }
    
    public static String format(String phone) {
        if (phone == null) return null;
        
        // Remove all non-digit characters
        String digits = phone.replaceAll("[^0-9]", "");
        
        // Format as (XXX) XXX-XXXX
        if (digits.length() == 10) {
            return String.format("(%s) %s-%s", 
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 10));
        } else if (digits.length() == 11 && digits.charAt(0) == '1') {
            return String.format("+1 (%s) %s-%s", 
                digits.substring(1, 4),
                digits.substring(4, 7),
                digits.substring(7, 11));
        }
        
        return phone;
    }
}
