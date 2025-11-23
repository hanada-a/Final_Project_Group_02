# Public Health Information Management System
## Final Project - Application Engineering and Development

**Team Members:** Akira Hanada, Travis Hodo, Maxwell Sowell  
**Course:** Application Engineering and Development  
**Institution:** Northeastern University  
**Submission Date:** December 2024

---

## PROJECT OVERVIEW

The Public Health Information Management System is a comprehensive multi-enterprise digital ecosystem designed to coordinate vaccine distribution and disease surveillance across national boundaries. The system connects the Centers for Disease Control and Prevention (CDC), state health departments, and healthcare providers into a unified network.

### System Architecture

**Hierarchy:**
- 1 Network: US Public Health Network
- 3 Enterprises:
  1. Centers for Disease Control (CDC)
  2. New York State Health Department
  3. Northeast Healthcare Provider Network
- 6 Organizations:
  1. Disease Surveillance Division (CDC)
  2. Vaccine Distribution Coordination (CDC)
  3. Public Health Services (State)
  4. Healthcare Provider Registry (State)
  5. Northeast Regional Medical Center (Provider)
  6. Brooklyn Community Health Clinic (Provider)

### Key Features

✅ **8 Unique Roles:**
1. Epidemiologist - Disease pattern analysis and alerts
2. Data Analyst - Statistical analysis and reporting
3. Distribution Coordinator - Vaccine allocation management
4. Public Health Officer - State health coordination
5. Provider Coordinator - Healthcare provider registry
6. Hospital Administrator - Hospital operations
7. Clinic Manager - Clinic operations and vaccine requests
8. Nurse Practitioner - Patient care and disease reporting

✅ **6+ Work Request Types:**
1. VaccineAllocationRequest (Cross-Enterprise: State → CDC)
2. DiseaseReportRequest (Multi-level: Provider → State → CDC)
3. VaccineShipmentRequest (Cross-Organization: Clinic → State)
4. PatientAppointmentRequest (Same Enterprise: Clinic → Hospital)
5. ComplianceAuditRequest (Cross-Enterprise: State → Provider)
6. HealthDataAnalysisRequest (Data Request: CDC → State)

✅ **Security Features:**
- Strong password validation (minimum 8 characters, uppercase, lowercase, digit)
- Password hashing using SHA-256
- Account lockout after 5 failed login attempts
- Session management with last login tracking

✅ **Validation:**
- Email validation with regex pattern
- Phone number validation and formatting
- Name validation
- Age validation with date calculations
- Unique ID generation for all entities

✅ **Pre-populated Data (Using Faker Library):**
- **50+ employees** with realistic names, emails, and phone numbers across 9 organizations
- **25+ work requests** demonstrating all workflow types with varied statuses
- 4 vaccines (COVID-19, Influenza, MMR, Hepatitis B)
- 4 diseases (COVID-19, Influenza, Measles, Tuberculosis)
- **JavaFaker integration** for realistic, analytics-ready demo data
- Professional contact information (emails matching organizational domains)
- Diverse job titles and specializations using Faker's job title generator

---

## INSTALLATION & SETUP

### Prerequisites
- Java JDK 8 or higher
- NetBeans IDE 8.2 or higher
- Windows/Mac/Linux OS
- **JavaFaker Library** (for realistic data generation)
  - Download: `javafaker-1.0.2.jar` from [Maven Repository](https://repo1.maven.org/maven2/com/github/javafaker/javafaker/1.0.2/)
  - See `FAKER_SETUP_INSTRUCTIONS.md` for detailed setup steps

### Running the Application

1. **Add JavaFaker Library (Required):**
   - Download `javafaker-1.0.2.jar` from [Maven Repository](https://repo1.maven.org/maven2/com/github/javafaker/javafaker/1.0.2/)
   - In NetBeans: Right-click project → Properties → Libraries → Add JAR/Folder
   - Select the downloaded JAR file
   - **Note:** Application will run without Faker but with fallback data (see console message)

2. **Open Project in NetBeans:**
   - File → Open Project
   - Navigate to: `Hanada_Akira_002069977_labs/Final_Project1`
   - Click "Open Project"

3. **Build the Project:**
   - Right-click on project → Clean and Build
   - Ensure all dependencies are resolved

4. **Run the Application:**
   - Right-click on project → Run
   - Or press F6
   - Check console for "✓ Faker library loaded successfully" message

### Test Credentials

The system comes pre-configured with the following test accounts:

**System/Enterprise Admins:**
- CDC Admin: `admin` / `Admin@2024!`
- State Admin: `stateadmin` / `Admin@2024!`
- Provider Admin: `provideradmin` / `Admin@2024!`

**CDC - Epidemiologist:**
- Username: `sarah.johnson`
- Password: `Cdc@2024!`

**CDC - Data Analyst:**
- Username: `jennifer.williams`
- Password: `Cdc@2024!`

**CDC - Distribution Coordinator:**
- Username: `robert.martinez`
- Password: `Cdc@2024!`

**State - Public Health Officer:**
- Username: `emily.thompson`
- Password: `State@2024!`

**State - Provider Coordinator:**
- Username: `patricia.davis`
- Password: `State@2024!`

**Hospital - Administrator:**
- Username: `amanda.garcia`
- Password: `Hospital@2024!`

**Clinic - Manager:**
- Username: `christopher.lee`
- Password: `Clinic@2024!`

**Clinic - Nurse:**
- Username: `susan.taylor`
- Password: `Clinic@2024!`

---

## PROJECT STRUCTURE

```
Final_Project1/
├── src/
│   ├── business/
│   │   ├── Business.java (Singleton)
│   │   ├── EcoSystem.java (Top-level hierarchy)
│   │   ├── Network.java
│   │   ├── Enterprise.java
│   │   ├── ConfigureABusiness.java (Data population)
│   │   │
│   │   ├── Domain/
│   │   │   ├── Vaccine.java
│   │   │   └── Disease.java
│   │   │
│   │   ├── Employee/
│   │   │   ├── Employee.java
│   │   │   └── EmployeeDirectory.java
│   │   │
│   │   ├── Organization/
│   │   │   ├── Organization.java (Abstract)
│   │   │   ├── DiseaseSurveillanceOrganization.java
│   │   │   ├── VaccineDistributionOrganization.java
│   │   │   ├── PublicHealthServicesOrganization.java
│   │   │   ├── ProviderRegistryOrganization.java
│   │   │   ├── HospitalOrganization.java
│   │   │   ├── ClinicOrganization.java
│   │   │   └── OrganizationDirectory.java
│   │   │
│   │   ├── Role/
│   │   │   ├── Role.java (Abstract)
│   │   │   ├── EpidemiologistRole.java
│   │   │   ├── DataAnalystRole.java
│   │   │   ├── DistributionCoordinatorRole.java
│   │   │   ├── PublicHealthOfficerRole.java
│   │   │   ├── ProviderCoordinatorRole.java
│   │   │   ├── HospitalAdminRole.java
│   │   │   ├── ClinicManagerRole.java
│   │   │   └── NursePractitionerRole.java
│   │   │
│   │   ├── UserAccount/
│   │   │   ├── UserAccount.java
│   │   │   └── UserAccountDirectory.java
│   │   │
│   │   ├── WorkQueue/
│   │   │   ├── WorkRequest.java (Abstract)
│   │   │   ├── WorkQueue.java
│   │   │   ├── VaccineAllocationRequest.java
│   │   │   ├── DiseaseReportRequest.java
│   │   │   ├── VaccineShipmentRequest.java
│   │   │   ├── PatientAppointmentRequest.java
│   │   │   ├── ComplianceAuditRequest.java
│   │   │   └── HealthDataAnalysisRequest.java
│   │   │
│   │   └── Util/
│   │       ├── EmailValidator.java
│   │       ├── PhoneValidator.java
│   │       ├── PasswordValidator.java
│   │       ├── NameValidator.java
│   │       ├── AgeValidator.java
│   │       ├── IDGenerator.java
│   │       └── ValidationException.java
│   │
│   └── ui/
│       ├── MainJFrame.java
│       ├── LoginScreen.java
│       ├── MainScreen.java
│       ├── EpidemiologistRole/
│       │   └── EpidemiologistWorkAreaJPanel.java
│       ├── DataAnalystRole/
│       │   └── DataAnalystWorkAreaJPanel.java
│       ├── DistributionCoordinatorRole/
│       │   └── DistributionCoordinatorWorkAreaJPanel.java
│       ├── PublicHealthOfficerRole/
│       │   └── PublicHealthOfficerWorkAreaJPanel.java
│       ├── ProviderCoordinatorRole/
│       │   └── ProviderCoordinatorWorkAreaJPanel.java
│       ├── HospitalAdminRole/
│       │   └── HospitalAdminWorkAreaJPanel.java
│       ├── ClinicManagerRole/
│       │   └── ClinicManagerWorkAreaJPanel.java
│       └── NursePractitionerRole/
│           └── NursePractitionerWorkAreaJPanel.java
```

---

## KEY DESIGN PATTERNS

### 1. Singleton Pattern
- `Business.java` - Ensures single instance of the business system

### 2. Factory Pattern  
- `Enterprise.createOrganization()` - Creates appropriate organization types

### 3. Template Method Pattern
- `WorkRequest` abstract class with concrete implementations

### 4. Strategy Pattern
- `Role` abstract class with role-specific behavior

### 5. Composition Pattern
- Organization HAS-A WorkQueue, EmployeeDirectory, UserAccountDirectory

---

## WORKFLOW EXAMPLES

### Cross-Enterprise Workflow: Disease Reporting
1. **Nurse** at Brooklyn Clinic reports COVID-19 cases
2. Report forwarded to **Public Health Officer** at State Health Dept
3. Officer reviews and escalates to **Epidemiologist** at CDC
4. Epidemiologist analyzes trends and issues alerts

### Cross-Enterprise Workflow: Vaccine Allocation
1. **Provider Coordinator** at State requests flu vaccines from CDC
2. **Distribution Coordinator** at CDC reviews request
3. Coordinator approves allocation and schedules shipment
4. Vaccines distributed to state and then to clinics/hospitals

### Same-Enterprise Workflow: Patient Appointment
1. **Clinic Manager** schedules patient for second vaccine dose
2. Request sent to **Hospital Administrator**
3. Administrator confirms appointment slot
4. **Nurse Practitioner** administers vaccine

---

## VALIDATION & ERROR HANDLING

All user inputs are validated:
- **Email**: RFC-compliant email format
- **Phone**: (XXX) XXX-XXXX format
- **Password**: 8+ chars, uppercase, lowercase, digit
- **Names**: Letters, spaces, hyphens, apostrophes only
- **NULL Checks**: All object references validated before use

---

## FUTURE ENHANCEMENTS

1. **Database Integration**: PostgreSQL/MySQL for persistent storage
2. **Enhanced Faker Usage**: More sophisticated data patterns and relationships
3. **Reporting Dashboard**: Real-time analytics and charts with 50+ employee dataset
4. **Email/SMS Notifications**: Alert system for critical events
5. **Advanced Search**: Filter work requests by multiple criteria
6. **Audit Trail**: Complete logging of all system actions
7. **Export Functionality**: PDF/Excel reports
8. **Mobile Responsive**: Web-based interface

---

## FAKER LIBRARY INTEGRATION

### Benefits
✅ **Realistic Demo Data**: Professional-looking names, emails, phone numbers  
✅ **Analytics-Ready**: 50+ employees and 25+ work requests for dashboard testing  
✅ **Easy Setup**: Single JAR file, no complex configuration  
✅ **Fallback Support**: Works without Faker (uses simple data generator)  
✅ **Production-Quality**: Data appears authentic for demonstrations

### What Faker Generates
- **Employee Names**: Realistic first and last name combinations
- **Email Addresses**: Professional emails matching organizational domains
- **Phone Numbers**: Properly formatted (XXX) XXX-XXXX format
- **Job Titles**: Varied positions with prefixes (Senior, Chief, Lead, etc.)

### Setup Instructions
See `FAKER_SETUP_INSTRUCTIONS.md` for complete setup guide.

---

## TEAM CONTRIBUTIONS

**Akira Hanada:**
- Business model architecture (EcoSystem, Network, Enterprise)
- Security implementation (password hashing, validation)
- Role and Organization implementations
- Work Request types and workflows
- System configuration and data population

**Travis Hodo:**
- UI/UX design and implementation
- Work area panels for 8 roles
- Authentication system
- Form validations

**Maxwell Sowell:**
- Testing and quality assurance
- Documentation
- Bug fixes and error handling
- User acceptance testing

---

## CONTACT

For questions or issues, please contact:
- Akira Hanada: hanada.a@northeastern.edu
- Travis Hodo: hodo.t@northeastern.edu  
- Maxwell Sowell: sowell.m@northeastern.edu

---

## LICENSE

This project is submitted as academic work for Northeastern University.
© 2024 Akira Hanada, Travis Hodo, Maxwell Sowell. All Rights Reserved.
