package business.Util;

import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Generates unique IDs for various entities
 */
public class IDGenerator {
    
    private static final AtomicInteger employeeCounter = new AtomicInteger(1000);
    private static final AtomicInteger userAccountCounter = new AtomicInteger(2000);
    private static final AtomicInteger workRequestCounter = new AtomicInteger(3000);
    private static final AtomicInteger organizationCounter = new AtomicInteger(100);
    
    public static String generateEmployeeID() {
        return "EMP-" + employeeCounter.getAndIncrement();
    }
    
    public static String generateUserAccountID() {
        return "USR-" + userAccountCounter.getAndIncrement();
    }
    
    public static String generateWorkRequestID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "WR-" + sdf.format(new Date()) + "-" + workRequestCounter.getAndIncrement();
    }
    
    public static String generateOrganizationID(String prefix) {
        return prefix + "-" + organizationCounter.getAndIncrement();
    }
    
    public static String generateVaccineID() {
        return "VAC-" + System.currentTimeMillis();
    }
    
    public static String generateDiseaseID() {
        return "DIS-" + System.currentTimeMillis();
    }
}
