package business;

import business.Domain.Disease;
import business.Domain.Vaccine;
import business.Employee.Employee;
import business.Organization.*;
import business.Role.*;
import business.UserAccount.UserAccount;
import business.Util.PasswordValidator;
import business.WorkQueue.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

// Faker library for realistic data generation
// Download: https://repo1.maven.org/maven2/com/github/javafaker/javafaker/1.0.2/
// Add JAR to project: Right-click project → Properties → Libraries → Add JAR/Folder
// Uncomment the import below after adding the library:
// import com.github.javafaker.Faker;

/**
 * Configures the complete Public Health Information Management System
 * with 1 Network, 3 Enterprises, 11 Organizations (including Admin), 50+ employees
 * Uses Faker library for realistic data generation (with fallback if not available)
 * 
 * @author Akira Hanada
 * @author Maxwell Sowell
 */
public class ConfigureABusiness {
    
    private static Random random = new Random();
    private static Object faker; // Will hold Faker instance if library is available
    private static boolean useFaker = false;
    
    static {
        // Try to initialize Faker if library is present
        try {
            Class<?> fakerClass = Class.forName("com.github.javafaker.Faker");
            faker = fakerClass.getDeclaredConstructor().newInstance();
            useFaker = true;
            System.out.println("✓ Faker library loaded successfully - generating realistic data");
        } catch (Exception e) {
            System.out.println("ℹ Faker library not found - using fallback data");
            System.out.println("  To use Faker: Add javafaker-1.0.2.jar to project libraries");
            System.out.println("  Download from: https://repo1.maven.org/maven2/com/github/javafaker/javafaker/1.0.2/");
        }
    }
    
    public static Business configure() {
        Business business = Business.getInstance();
        
        // Create the ecosystem
        EcoSystem ecoSystem = new EcoSystem("National Health Coordination System");
        business.setEcoSystem(ecoSystem);
        
        // Create vaccines and diseases (enhanced with Faker)
        populateVaccinesAndDiseases(business);
        
        // Create network
        Network network = ecoSystem.createNetwork("US Public Health Network");
        
        // Create 3 Enterprises
        Enterprise cdcEnterprise = network.createEnterprise("Centers for Disease Control", 
                                                             Enterprise.EnterpriseType.CDC);
        Enterprise stateHealthEnterprise = network.createEnterprise("New York State Health Department", 
                                                                    Enterprise.EnterpriseType.STATE_HEALTH);
        Enterprise providerEnterprise = network.createEnterprise("Northeast Healthcare Provider Network", 
                                                                 Enterprise.EnterpriseType.HEALTHCARE_PROVIDER);
        
        // Create 9 Organizations (3 Admin + 6 Operational) with 50+ employees
        setupCDCOrganizations(cdcEnterprise);
        setupStateHealthOrganizations(stateHealthEnterprise);
        setupProviderOrganizations(providerEnterprise);
        
        // Create 20+ sample work requests for analytics
        createSampleWorkRequests(cdcEnterprise, stateHealthEnterprise, providerEnterprise, business);
        
        return business;
    }
    
    // Helper methods for Faker with fallback
    private static String generateName() {
        if (useFaker) {
            try {
                java.lang.reflect.Method method = faker.getClass().getMethod("name");
                Object nameObj = method.invoke(faker);
                method = nameObj.getClass().getMethod("fullName");
                return (String) method.invoke(nameObj);
            } catch (Exception e) {
                // Fallback
            }
        }
        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Lisa", 
                               "James", "Mary", "William", "Patricia", "Richard", "Jennifer", "Thomas"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", 
                              "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez"};
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }
    
    private static String generateEmail(String name, String domain) {
        String emailName = name.toLowerCase().replace(" ", ".");
        return emailName + "@" + domain;
    }
    
    private static String generatePhone() {
        if (useFaker) {
            try {
                java.lang.reflect.Method method = faker.getClass().getMethod("phoneNumber");
                Object phoneObj = method.invoke(faker);
                method = phoneObj.getClass().getMethod("cellPhone");
                String phone = (String) method.invoke(phoneObj);
                // Format to (XXX) XXX-XXXX
                String numbers = phone.replaceAll("[^0-9]", "");
                if (numbers.length() >= 10) {
                    return String.format("(%s) %s-%s", 
                        numbers.substring(0, 3), 
                        numbers.substring(3, 6), 
                        numbers.substring(6, 10));
                }
            } catch (Exception e) {
                // Fallback
            }
        }
        return String.format("(%03d) %03d-%04d", 
            random.nextInt(900) + 100, 
            random.nextInt(900) + 100, 
            random.nextInt(10000));
    }
    
    private static String generateJobTitle(String prefix) {
        if (useFaker) {
            try {
                java.lang.reflect.Method method = faker.getClass().getMethod("job");
                Object jobObj = method.invoke(faker);
                method = jobObj.getClass().getMethod("title");
                String title = (String) method.invoke(jobObj);
                return prefix + " " + title;
            } catch (Exception e) {
                // Fallback
            }
        }
        String[] titles = {"Senior", "Junior", "Lead", "Principal", "Staff", "Chief", "Associate"};
        return prefix + " " + titles[random.nextInt(titles.length)];
    }
    
    private static void populateVaccinesAndDiseases(Business business) {
        // Add Vaccines
        Vaccine covidVaccine = new Vaccine("COVID-19 mRNA Vaccine", "Pfizer-BioNTech", 2);
        covidVaccine.setDaysBetweenDoses(21);
        covidVaccine.setStorageTemperature("-70°C to -80°C");
        covidVaccine.setTargetDiseases("COVID-19");
        covidVaccine.setExpirationDate(getDateInFuture(365));
        business.getVaccineDirectory().add(covidVaccine);
        
        Vaccine fluVaccine = new Vaccine("Influenza Vaccine", "Sanofi Pasteur", 1);
        fluVaccine.setStorageTemperature("2°C to 8°C");
        fluVaccine.setTargetDiseases("Influenza");
        fluVaccine.setExpirationDate(getDateInFuture(180));
        business.getVaccineDirectory().add(fluVaccine);
        
        Vaccine measlesVaccine = new Vaccine("MMR Vaccine", "Merck", 2);
        measlesVaccine.setDaysBetweenDoses(28);
        measlesVaccine.setStorageTemperature("2°C to 8°C");
        measlesVaccine.setTargetDiseases("Measles, Mumps, Rubella");
        measlesVaccine.setExpirationDate(getDateInFuture(730));
        business.getVaccineDirectory().add(measlesVaccine);
        
        Vaccine hepatitisVaccine = new Vaccine("Hepatitis B Vaccine", "GlaxoSmithKline", 3);
        hepatitisVaccine.setStorageTemperature("2°C to 8°C");
        hepatitisVaccine.setTargetDiseases("Hepatitis B");
        hepatitisVaccine.setExpirationDate(getDateInFuture(1095));
        business.getVaccineDirectory().add(hepatitisVaccine);
        
        // Add Diseases
        Disease covid = new Disease("COVID-19", Disease.Severity.HIGH, true);
        covid.setDescription("Respiratory illness caused by SARS-CoV-2 virus");
        covid.setSymptoms("Fever, cough, shortness of breath, loss of taste/smell");
        covid.setIncubationPeriod(5);
        business.getDiseaseDirectory().add(covid);
        
        Disease influenza = new Disease("Influenza", Disease.Severity.MEDIUM, true);
        influenza.setDescription("Contagious respiratory illness caused by influenza viruses");
        influenza.setSymptoms("Fever, cough, sore throat, body aches, fatigue");
        influenza.setIncubationPeriod(2);
        business.getDiseaseDirectory().add(influenza);
        
        Disease measles = new Disease("Measles", Disease.Severity.HIGH, true);
        measles.setDescription("Highly contagious viral disease");
        measles.setSymptoms("High fever, cough, runny nose, red eyes, rash");
        measles.setIncubationPeriod(10);
        business.getDiseaseDirectory().add(measles);
        
        Disease tuberculosis = new Disease("Tuberculosis", Disease.Severity.HIGH, true);
        tuberculosis.setDescription("Bacterial infection that primarily affects the lungs");
        tuberculosis.setSymptoms("Persistent cough, chest pain, coughing up blood, weight loss");
        tuberculosis.setIncubationPeriod(60);
        business.getDiseaseDirectory().add(tuberculosis);
    }
    
    private static void setupCDCOrganizations(Enterprise cdcEnterprise) {
        // Admin Organization for CDC Enterprise
        AdminOrganization cdcAdmin = (AdminOrganization) cdcEnterprise.createOrganization(
                "CDC Administration", Organization.Type.Admin);
        
        createEmployeeAndAccount(cdcAdmin, "Admin John Smith", "admin.cdc@cdc.gov", 
                                "(404) 555-0001", "CDC System Administrator", new AdminRole(), 
                                "admin", "Admin@2024!");
        
        // Organization 1: Disease Surveillance Division (15+ employees)
        DiseaseSurveillanceOrganization diseaseSurv = 
            (DiseaseSurveillanceOrganization) cdcEnterprise.createOrganization(
                "Disease Surveillance Division", Organization.Type.DiseaseSurveillance);
        
        // Key employees with known credentials
        createEmployeeAndAccount(diseaseSurv, "Dr. Sarah Johnson", "sarah.johnson@cdc.gov", 
                                "(404) 555-0101", "Chief Epidemiologist", new EpidemiologistRole(), 
                                "sarah.johnson", "Cdc@2024!");
        
        createEmployeeAndAccount(diseaseSurv, "Dr. Michael Chen", "michael.chen@cdc.gov", 
                                "(404) 555-0102", "Senior Epidemiologist", new EpidemiologistRole(), 
                                "michael.chen", "Cdc@2024!");
        
        createEmployeeAndAccount(diseaseSurv, "Jennifer Williams", "jennifer.williams@cdc.gov", 
                                "(404) 555-0103", "Data Analyst", new DataAnalystRole(), 
                                "jennifer.williams", "Cdc@2024!");
        
        // Generate additional employees with Faker
        for (int i = 0; i < 6; i++) {
            String name = "Dr. " + generateName();
            createEmployeeAndAccount(diseaseSurv, name, generateEmail(name, "cdc.gov"),
                                    generatePhone(), generateJobTitle("Epidemiologist"), 
                                    new EpidemiologistRole(), null, null);
        }
        
        for (int i = 0; i < 6; i++) {
            String name = generateName();
            createEmployeeAndAccount(diseaseSurv, name, generateEmail(name, "cdc.gov"),
                                    generatePhone(), generateJobTitle("Data Analyst"), 
                                    new DataAnalystRole(), null, null);
        }
        
        // Organization 2: Vaccine Distribution Coordination (12+ employees)
        VaccineDistributionOrganization vaccineDist = 
            (VaccineDistributionOrganization) cdcEnterprise.createOrganization(
                "Vaccine Distribution Coordination", Organization.Type.VaccineDistribution);
        
        createEmployeeAndAccount(vaccineDist, "Robert Martinez", "robert.martinez@cdc.gov", 
                                "(404) 555-0201", "Distribution Coordinator", new DistributionCoordinatorRole(), 
                                "robert.martinez", "Cdc@2024!");
        
        createEmployeeAndAccount(vaccineDist, "Lisa Anderson", "lisa.anderson@cdc.gov", 
                                "(404) 555-0202", "Senior Distribution Coordinator", new DistributionCoordinatorRole(), 
                                "lisa.anderson", "Cdc@2024!");
        
        // Generate additional distribution coordinators
        for (int i = 0; i < 10; i++) {
            String name = generateName();
            createEmployeeAndAccount(vaccineDist, name, generateEmail(name, "cdc.gov"),
                                    generatePhone(), generateJobTitle("Distribution Coordinator"), 
                                    new DistributionCoordinatorRole(), null, null);
        }
    }
    
    private static void setupStateHealthOrganizations(Enterprise stateHealthEnterprise) {
        // Admin Organization for State Health Enterprise
        AdminOrganization stateAdmin = (AdminOrganization) stateHealthEnterprise.createOrganization(
                "State Health Administration", Organization.Type.Admin);
        
        createEmployeeAndAccount(stateAdmin, "Admin Mary Johnson", "admin.state@health.ny.gov", 
                                "(518) 555-0001", "State System Administrator", new AdminRole(), 
                                "stateadmin", "Admin@2024!");
        
        // Organization 3: Public Health Services (10+ employees)
        PublicHealthServicesOrganization publicHealth = 
            (PublicHealthServicesOrganization) stateHealthEnterprise.createOrganization(
                "Public Health Services", Organization.Type.PublicHealthServices);
        
        createEmployeeAndAccount(publicHealth, "Dr. Emily Thompson", "emily.thompson@health.ny.gov", 
                                "(518) 555-0301", "Public Health Officer", new PublicHealthOfficerRole(), 
                                "emily.thompson", "State@2024!");
        
        createEmployeeAndAccount(publicHealth, "Dr. David Brown", "david.brown@health.ny.gov", 
                                "(518) 555-0302", "Senior Public Health Officer", new PublicHealthOfficerRole(), 
                                "david.brown", "State@2024!");
        
        // Generate additional public health officers
        for (int i = 0; i < 8; i++) {
            String name = "Dr. " + generateName();
            createEmployeeAndAccount(publicHealth, name, generateEmail(name, "health.ny.gov"),
                                    generatePhone(), generateJobTitle("Public Health Officer"), 
                                    new PublicHealthOfficerRole(), null, null);
        }
        
        // Organization 4: Healthcare Provider Registry (8+ employees)
        ProviderRegistryOrganization providerRegistry = 
            (ProviderRegistryOrganization) stateHealthEnterprise.createOrganization(
                "Healthcare Provider Registry", Organization.Type.ProviderRegistry);
        
        createEmployeeAndAccount(providerRegistry, "Patricia Davis", "patricia.davis@health.ny.gov", 
                                "(518) 555-0401", "Provider Coordinator", new ProviderCoordinatorRole(), 
                                "patricia.davis", "State@2024!");
        
        createEmployeeAndAccount(providerRegistry, "James Wilson", "james.wilson@health.ny.gov", 
                                "(518) 555-0402", "Senior Provider Coordinator", new ProviderCoordinatorRole(), 
                                "james.wilson", "State@2024!");
        
        // Generate additional provider coordinators
        for (int i = 0; i < 6; i++) {
            String name = generateName();
            createEmployeeAndAccount(providerRegistry, name, generateEmail(name, "health.ny.gov"),
                                    generatePhone(), generateJobTitle("Provider Coordinator"), 
                                    new ProviderCoordinatorRole(), null, null);
        }
    }
    
    private static void setupProviderOrganizations(Enterprise providerEnterprise) {
        // Admin Organization for Provider Enterprise
        AdminOrganization providerAdmin = (AdminOrganization) providerEnterprise.createOrganization(
                "Provider Network Administration", Organization.Type.Admin);
        
        createEmployeeAndAccount(providerAdmin, "Admin Robert Wilson", "admin.provider@nrmc.org", 
                                "(212) 555-0001", "Provider Network Administrator", new AdminRole(), 
                                "provideradmin", "Admin@2024!");
        
        // Organization 5: Regional Hospital (15+ employees)
        HospitalOrganization hospital = 
            (HospitalOrganization) providerEnterprise.createOrganization(
                "Northeast Regional Medical Center", Organization.Type.Hospital);
        
        createEmployeeAndAccount(hospital, "Dr. Amanda Garcia", "amanda.garcia@nrmc.org", 
                                "(212) 555-0501", "Hospital Administrator", new HospitalAdminRole(), 
                                "amanda.garcia", "Hospital@2024!");
        
        createEmployeeAndAccount(hospital, "Nurse Maria Rodriguez", "maria.rodriguez@nrmc.org", 
                                "(212) 555-0502", "Nurse Practitioner", new NursePractitionerRole(), 
                                "maria.rodriguez", "Hospital@2024!");
        
        createEmployeeAndAccount(hospital, "Nurse John Miller", "john.miller@nrmc.org", 
                                "(212) 555-0503", "Nurse Practitioner", new NursePractitionerRole(), 
                                "john.miller", "Hospital@2024!");
        
        // Generate additional hospital staff
        for (int i = 0; i < 5; i++) {
            String name = "Dr. " + generateName();
            createEmployeeAndAccount(hospital, name, generateEmail(name, "nrmc.org"),
                                    generatePhone(), generateJobTitle("Hospital Administrator"), 
                                    new HospitalAdminRole(), null, null);
        }
        
        for (int i = 0; i < 7; i++) {
            String name = "Nurse " + generateName();
            createEmployeeAndAccount(hospital, name, generateEmail(name, "nrmc.org"),
                                    generatePhone(), "Nurse Practitioner", 
                                    new NursePractitionerRole(), null, null);
        }
        
        // Organization 6: Community Clinic (12+ employees)
        ClinicOrganization clinic = 
            (ClinicOrganization) providerEnterprise.createOrganization(
                "Brooklyn Community Health Clinic", Organization.Type.Clinic);
        
        createEmployeeAndAccount(clinic, "Dr. Christopher Lee", "christopher.lee@bchc.org", 
                                "(718) 555-0601", "Clinic Manager", new ClinicManagerRole(), 
                                "christopher.lee", "Clinic@2024!");
        
        createEmployeeAndAccount(clinic, "Nurse Susan Taylor", "susan.taylor@bchc.org", 
                                "(718) 555-0602", "Nurse Practitioner", new NursePractitionerRole(), 
                                "susan.taylor", "Clinic@2024!");
        
        createEmployeeAndAccount(clinic, "Nurse Kevin White", "kevin.white@bchc.org", 
                                "(718) 555-0603", "Nurse Practitioner", new NursePractitionerRole(), 
                                "kevin.white", "Clinic@2024!");
        
        // Generate additional clinic staff
        for (int i = 0; i < 4; i++) {
            String name = "Dr. " + generateName();
            createEmployeeAndAccount(clinic, name, generateEmail(name, "bchc.org"),
                                    generatePhone(), generateJobTitle("Clinic Manager"), 
                                    new ClinicManagerRole(), null, null);
        }
        
        for (int i = 0; i < 5; i++) {
            String name = "Nurse " + generateName();
            createEmployeeAndAccount(clinic, name, generateEmail(name, "bchc.org"),
                                    generatePhone(), "Nurse Practitioner", 
                                    new NursePractitionerRole(), null, null);
        }
        
        
        // Organization 7: Laboratory Services (8+ employees)
        LabOrganization labOrg = 
            (LabOrganization) providerEnterprise.createOrganization(
                "Northeast Regional Medical Laboratory", Organization.Type.Lab);
        
        createEmployeeAndAccount(labOrg, "John Bob", "john.bob@nrml.org", 
                                "(212) 555-0701", "Senior Lab Technician", new LabTechRole(), 
                                "john.bob", "Lab@2024!");
        
        createEmployeeAndAccount(labOrg, "Mary Sue", "mary.sue@nrml.org", 
                                "(212) 555-0702", "Lab Technician", new LabTechRole(), 
                                "mary.sue", "Lab@2024!");
        
        createEmployeeAndAccount(labOrg, "Dr. Lisa Ann", "lisa.ann@nrml.org", 
                                "(212) 555-0703", "Lab Director", new LabTechRole(), 
                                "lisa.ann", "Lab@2024!");
        
        // Generate additional lab staff
        for (int i = 0; i < 5; i++) {
            String name = generateName();
            createEmployeeAndAccount(labOrg, name, generateEmail(name, "nrml.org"),
                                    generatePhone(), generateJobTitle("Lab Technician"), 
                                    new LabTechRole(), null, null);
        }
        
        
        // Organization 8: Pharmacy Services (6+ employees)
        PharmacyOrganization pharmacyOrg = 
            (PharmacyOrganization) providerEnterprise.createOrganization(
                "Northeast Regional Pharmacy", Organization.Type.Pharmacy);
        
        createEmployeeAndAccount(pharmacyOrg, "Dan Man", "dan.man@nrp.org", 
                                "(212) 555-0801", "Lead Pharmacist", new PharmacistRole(), 
                                "dan.man", "Pharm@2024!");
        
        createEmployeeAndAccount(pharmacyOrg, "Al Joe", "al.joe@nrp.org", 
                                "(212) 555-0802", "Staff Pharmacist", new PharmacistRole(), 
                                "al.joe", "Pharm@2024!");
        
        createEmployeeAndAccount(pharmacyOrg, "Kim Park", "kim.park@nrp.org", 
                                "(212) 555-0803", "Clinical Pharmacist", new PharmacistRole(), 
                                "kim.park", "Pharm@2024!");
        
        // Generate additional pharmacy staff
        for (int i = 0; i < 3; i++) {
            String name = generateName();
            createEmployeeAndAccount(pharmacyOrg, name, generateEmail(name, "nrp.org"),
                                    generatePhone(), generateJobTitle("Pharmacist"), 
                                    new PharmacistRole(), null, null);
        }
        
        
    }
    
    private static void createEmployeeAndAccount(Organization org, String name, String email, 
                                                  String phone, String position, Role role, 
                                                  String username, String password) {
        Employee employee = new Employee(name, email, phone, position);
        org.getEmployeeDirectory().getEmployeeList().add(employee);
        
        // Only create user account if username and password are provided
        if (username != null && password != null) {
            UserAccount account = new UserAccount();
            account.setUsername(username);
            account.setPassword(PasswordValidator.hashPassword(password));
            account.setRole(role);
            account.setEmployee(employee);
            account.setEnabled(true);
            org.getUserAccountDirectory().getUserAccountList().add(account);
        }
    }
    
    private static void createSampleWorkRequests(Enterprise cdcEnt, Enterprise stateEnt, 
                                                  Enterprise providerEnt, Business business) {
        // Get organizations (skip admin orgs at index 0)
        Organization vaccineDist = cdcEnt.getOrganizationDirectory().getOrganizationList().get(2);
        Organization publicHealth = stateEnt.getOrganizationDirectory().getOrganizationList().get(1);
        Organization providerRegistry = stateEnt.getOrganizationDirectory().getOrganizationList().get(2);
        Organization hospital = providerEnt.getOrganizationDirectory().getOrganizationList().get(1);
        Organization clinic = providerEnt.getOrganizationDirectory().getOrganizationList().get(2);
        
        // Create 25+ diverse work requests for analytics and better demo
        
        // 1-5: Disease Reports (Cross-Enterprise)
        for (int i = 0; i < 5; i++) {
            DiseaseReportRequest diseaseReport = new DiseaseReportRequest();
            diseaseReport.setDisease(business.getDiseaseDirectory().get(i % 4));
            diseaseReport.setCaseCount(random.nextInt(10) + 1);
            diseaseReport.setLocation(i % 2 == 0 ? "Brooklyn, NY" : "Manhattan, NY");
            diseaseReport.setPatientDemographics("Adults 25-65, Mixed gender");
            diseaseReport.setOnsetDate(getDateInPast(random.nextInt(30) + 1));
            diseaseReport.setSender(clinic.getUserAccountDirectory().getUserAccountList().get(0));
            diseaseReport.setReceiver(publicHealth.getUserAccountDirectory().getUserAccountList().get(0));
            diseaseReport.setMessage("Reporting " + diseaseReport.getCaseCount() + " new cases");
            diseaseReport.setStatus(i < 3 ? "Pending" : "Completed");
            clinic.getWorkQueue().getWorkRequestList().add(diseaseReport);
        }
        
        // 6-10: Vaccine Allocations (Cross-Enterprise)
        for (int i = 0; i < 5; i++) {
            VaccineAllocationRequest vaccineAlloc = new VaccineAllocationRequest();
            vaccineAlloc.setVaccine(business.getVaccineDirectory().get(i % 4));
            vaccineAlloc.setQuantityRequested((random.nextInt(10) + 1) * 10000);
            vaccineAlloc.setPriority(i < 2 ? "HIGH" : (i < 4 ? "MEDIUM" : "LOW"));
            vaccineAlloc.setTargetPopulation("Healthcare Workers and Vulnerable Populations");
            vaccineAlloc.setJustification("Seasonal preparation");
            vaccineAlloc.setSender(providerRegistry.getUserAccountDirectory().getUserAccountList().get(0));
            vaccineAlloc.setReceiver(vaccineDist.getUserAccountDirectory().getUserAccountList().get(0));
            vaccineAlloc.setMessage("Vaccine allocation request");
            vaccineAlloc.setNeededByDate(getDateInFuture(random.nextInt(60) + 10));
            vaccineAlloc.setStatus(i < 3 ? "Pending" : "Completed");
            providerRegistry.getWorkQueue().getWorkRequestList().add(vaccineAlloc);
        }
        
        // 11-15: Vaccine Shipments (Cross-Organization)
        for (int i = 0; i < 5; i++) {
            VaccineShipmentRequest shipment = new VaccineShipmentRequest();
            shipment.setVaccine(business.getVaccineDirectory().get(i % 4));
            shipment.setQuantity((random.nextInt(5) + 1) * 500);
            shipment.setShippingAddress("Brooklyn Community Health Clinic, NY 11201");
            shipment.setRequestedDeliveryDate(getDateInFuture(random.nextInt(14) + 3));
            shipment.setSender(clinic.getUserAccountDirectory().getUserAccountList().get(0));
            shipment.setReceiver(providerRegistry.getUserAccountDirectory().getUserAccountList().get(0));
            shipment.setMessage(i % 2 == 0 ? "Urgent shipment" : "Routine resupply");
            shipment.setUrgentDelivery(i % 2 == 0);
            shipment.setStatus(i < 2 ? "Pending" : "Completed");
            clinic.getWorkQueue().getWorkRequestList().add(shipment);
        }
        
        // 16-20: Patient Appointments
        for (int i = 0; i < 5; i++) {
            PatientAppointmentRequest appointment = new PatientAppointmentRequest();
            appointment.setPatientName(generateName());
            appointment.setPatientPhone(generatePhone());
            appointment.setPatientEmail(generateEmail("patient" + i, "email.com"));
            appointment.setVaccine(business.getVaccineDirectory().get(i % 4));
            appointment.setAppointmentType(i % 2 == 0 ? "First Dose" : "Second Dose");
            appointment.setPreferredDate(getDateInFuture(random.nextInt(30) + 1));
            appointment.setSender(clinic.getUserAccountDirectory().getUserAccountList().get(0));
            appointment.setReceiver(hospital.getUserAccountDirectory().getUserAccountList().get(0));
            appointment.setMessage("Patient appointment request");
            appointment.setStatus(i < 3 ? "Pending" : "Completed");
            clinic.getWorkQueue().getWorkRequestList().add(appointment);
        }
        
        // 21-22: Compliance Audits
        for (int i = 0; i < 2; i++) {
            ComplianceAuditRequest audit = new ComplianceAuditRequest();
            audit.setAuditType(i == 0 ? "Vaccine Storage" : "Distribution Protocol");
            audit.setComplianceScore(String.valueOf(random.nextInt(30) + 70));
            audit.setFindings("Routine compliance check");
            audit.setSender(publicHealth.getUserAccountDirectory().getUserAccountList().get(0));
            audit.setReceiver(hospital.getUserAccountDirectory().getUserAccountList().get(0));
            audit.setMessage("Scheduled compliance audit");
            audit.setStatus("Completed");
            publicHealth.getWorkQueue().getWorkRequestList().add(audit);
        }
        
        // 23-25: Health Data Analysis
        for (int i = 0; i < 3; i++) {
            HealthDataAnalysisRequest analysis = new HealthDataAnalysisRequest();
            analysis.setAnalysisType("Outbreak Pattern Analysis");
            analysis.setDataParameters("Regional disease surveillance data");
            analysis.setGeographicScope("New York State");
            analysis.setSender(cdcEnt.getOrganizationDirectory().getOrganizationList().get(1)
                              .getUserAccountDirectory().getUserAccountList().get(0));
            analysis.setReceiver(publicHealth.getUserAccountDirectory().getUserAccountList().get(0));
            analysis.setMessage("Request for health data analysis");
            analysis.setStatus(i == 0 ? "Pending" : "Completed");
            cdcEnt.getOrganizationDirectory().getOrganizationList().get(1)
                  .getWorkQueue().getWorkRequestList().add(analysis);
        }
        
        // 26-30: Lab Test Requests (Cross-Organization)
        Organization labOrg = providerEnt.getOrganizationDirectory().getOrganizationList().get(3);
        
        for (int i = 0; i < 5; i++) {
            LabTestRequest labTest = new LabTestRequest();
            
            String[] testTypes = {"COVID-19 PCR Test", "COVID-19 Antibody Test", "Influenza Test", 
                    "Blood Culture", "Complete Blood Count"};
            labTest.setTestType(testTypes[i % testTypes.length]);
            
            labTest.setUrgencyLevel(i < 2 ? "URGENT" : "Normal");
            labTest.setMessage("Patient ID: PT" + (10000 + i) + " - " + testTypes[i % testTypes.length]);
            
            // Demo lab tests requested by Clinic Nurse Susan Taylor from John Bob
            labTest.setSender(clinic.getUserAccountDirectory().getUserAccountList().get(1));
            labTest.setReceiver(labOrg.getUserAccountDirectory().getUserAccountList().get(0));
            
            labTest.setStatus(i < 3 ? "Pending" : "Completed");
            
            if (i >= 3) {
                labTest.setTestResult("Test result: " + (i % 2 == 0 ? "Negative" : "Positive"));
                labTest.setResolveDate(getDateInPast(random.nextInt(5) + 1));
            }
            
            labOrg.getWorkQueue().getWorkRequestList().add(labTest);
        }
        
        
        // 31-35: Prescription Requests (Cross-Organization)
        Organization pharmacyOrg = providerEnt.getOrganizationDirectory().getOrganizationList().get(4);
        
        String[] medications = {"Penicillin", "Gabapentin", "Hydrocodone", "Alprazolam", "Atorvastatin"};
        String[] dosages = {"250mg", "800mg", "5mg", "1mg", "20mg"};
        int[] quantities = {30, 120, 15, 60, 90};
        
        for (int i = 0; i < 5; i++) {
            PrescriptionRequest rxRequest = new PrescriptionRequest();
            
            rxRequest.setPatientName(generateName());
            rxRequest.setMedicationName(medications[i]);
            rxRequest.setDosage(dosages[i]);
            rxRequest.setQuantity(quantities[i]);
            rxRequest.setPrescribingDoctor("Dr. Amanda Garcia");
            rxRequest.setMessage("Prescription for " + medications[i] + " - " + dosages[i]);
            
            // Demo prescriptions sent from Hospital Admin (Dr. Amanda Garcia) to Pharmacist (Dan Man)
            rxRequest.setSender(hospital.getUserAccountDirectory().getUserAccountList().get(0));
            rxRequest.setReceiver(pharmacyOrg.getUserAccountDirectory().getUserAccountList().get(0));
            
            rxRequest.setStatus(i < 3 ? "Pending" : "Fulfilled");
            
            if (i >= 3) {
                rxRequest.setFulfillmentNotes("Prescription filled. Patient provided with dosage instructions.");
                rxRequest.setResolveDate(getDateInPast(random.nextInt(3) + 1));
            }
            
            pharmacyOrg.getWorkQueue().getWorkRequestList().add(rxRequest);
        }
        
        
    }
    
    private static Date getDateInFuture(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }
    
    private static Date getDateInPast(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        return cal.getTime();
    }
}
