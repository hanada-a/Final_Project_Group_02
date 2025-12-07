# Public Health Information Management System
## Final Project - Application Engineering and Development

**Team Members:** Akira Hanada, Maxwell Sowell, Travis Hodo(Withdrawn from the Group)  
**Course:** Application Engineering and Development  
**Institution:** Northeastern University  
**Submission Date:** December 2025

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
- 9 Organizations:
  1. Disease Surveillance Division (CDC)
  2. Vaccine Distribution Coordination (CDC)
  3. Public Health Services (State)
  4. Healthcare Provider Registry (State)
  5. Northeast Regional Medical Center (Hospital)
  6. Brooklyn Community Health Clinic (Clinic)
  7. Northeast Regional Medical Laboratory (Lab)
  8. Northeast Regional Pharmacy (Pharmacy)
  9. Cold Chain Storage Facilities (Hospital & Pharmacy)

### Key Features

✅ **13 Unique Roles:**
1. System Administrator - Ecosystem-level administration
2. Epidemiologist - Disease pattern analysis and alerts
3. Data Analyst - Statistical analysis and reporting
4. Distribution Coordinator - Vaccine allocation management
5. Public Health Officer - State health coordination
6. Provider Coordinator - Healthcare provider registry
7. Hospital Administrator - Hospital operations and prescription management
8. Clinic Manager - Clinic operations, vaccine requests, and lab test requests
9. Nurse Practitioner - Patient care and disease reporting
10. Doctor - Medical consultations and lab test requests
11. Lab Technician - Laboratory test processing and analysis
12. Pharmacist - Prescription fulfillment and medication management
13. Vaccine Storage Specialist - Cold chain monitoring and failure reporting

✅ **9 Work Request Types:**
1. VaccineAllocationRequest (Cross-Enterprise: State → CDC)
2. DiseaseReportRequest (Multi-level: Clinic/Nurse → State → CDC)
3. VaccineShipmentRequest (Cross-Organization: Clinic → Provider Registry)
4. PatientAppointmentRequest (Cross-Organization: Clinic → Hospital)
5. ComplianceAuditRequest (Cross-Enterprise: State → Provider)
6. HealthDataAnalysisRequest (Data Request: CDC → State)
7. LabTestRequest (Cross-Organization: Clinic/Doctor → Lab)
8. PrescriptionRequest (Cross-Organization: Hospital → Pharmacy)
9. ColdChainFailureRequest (Cross-Organization: Storage Specialist → Provider Coordinator)

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
- **60+ employees** with realistic names, emails, and phone numbers across 9 organizations
- **40+ work requests** demonstrating all 9 workflow types with varied statuses
- 4 vaccines (COVID-19, Influenza, MMR, Hepatitis B)
- 4 diseases (COVID-19, Influenza, Measles, Tuberculosis)
- **JavaFaker integration** for realistic, analytics-ready demo data
- Professional contact information (emails matching organizational domains)
- 5 lab test requests (Clinic → Lab)
- 5 prescription requests (Hospital → Pharmacy)
- 4 cold chain failure reports (Storage Specialist → Provider Coordinator)
- Diverse job titles and specializations using Faker's job title generator

✅ **Reporting & Analytics (NEW):**
- **System Dashboard**: Comprehensive analytics at ecosystem level
- Work request analysis by type, status, and organization
- Employee distribution across enterprises
- Organization performance metrics
- Health data visualization (vaccines & diseases)
- CSV export functionality for all reports
- Key insights and system health indicators

✅ **Enhanced Admin Operations (NEW):**
- **Full CRUD for Employees**: Create, Read, Update, Delete with validation
- **Full CRUD for User Accounts**: Complete account lifecycle management
- Password reset functionality with account unlock
- Form validation and error handling
- Confirmation dialogs for all destructive operations
- Real-time table updates after operations

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

**Hospital - Vaccine Storage Manager:**
- Username: `ray.kim`
- Password: `Cold@2024!`

**Hospital - Cold Chain Specialist:**
- Username: `jay.poe`
- Password: `Cold@2024!`

**Lab - Lab Director:**
- Username: `lisa.ann`
- Password: `Lab@2024!`

**Lab - Sr Lab Technician:**
- Username: `john.bob`
- Password: `Lab@2024!`

**Lab - Lab Technician:**
- Username: `mary.sue`
- Password: `Lab@2024!`

**Pharmacy - Lead Pharmacist:**
- Username: `dan.man`
- Password: `Pharm@2024!`

**Pharmacy - Clinical Pharmacist:**
- Username: `kim.park`
- Password: `Pharm@2024!`

**Pharmacy - Vaccine Storage Mgr:**
- Username: `mae.toh`
- Password: `Cold@2024!`

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
│   │   │   ├── AdminOrganization.java
│   │   │   ├── DiseaseSurveillanceOrganization.java
│   │   │   ├── VaccineDistributionOrganization.java
│   │   │   ├── PublicHealthServicesOrganization.java
│   │   │   ├── ProviderRegistryOrganization.java
│   │   │   ├── HospitalOrganization.java
│   │   │   ├── ClinicOrganization.java
│   │   │   ├── LabOrganization.java
│   │   │   ├── PharmacyOrganization.java
│   │   │   └── OrganizationDirectory.java
│   │   │
│   │   ├── Role/
│   │   │   ├── Role.java (Abstract)
│   │   │   ├── AdminRole.java
│   │   │   ├── EpidemiologistRole.java
│   │   │   ├── DataAnalystRole.java
│   │   │   ├── DistributionCoordinatorRole.java
│   │   │   ├── PublicHealthOfficerRole.java
│   │   │   ├── ProviderCoordinatorRole.java
│   │   │   ├── HospitalAdminRole.java
│   │   │   ├── ClinicManagerRole.java
│   │   │   ├── NursePractitionerRole.java
│   │   │   ├── DoctorRole.java
│   │   │   ├── LabTechRole.java
│   │   │   ├── PharmacistRole.java
│   │   │   └── VaccineStorageSpecialistRole.java
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
│   │   │   ├── HealthDataAnalysisRequest.java
│   │   │   ├── LabTestRequest.java
│   │   │   ├── PrescriptionRequest.java
│   │   │   └── ColdChainFailureRequest.java
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
│       ├── AdministrativeRole/
│       │   └── (Admin panels)
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
│       │   ├── HospitalAdminWorkAreaJPanel.java
│       │   └── SubmitPrescriptionJPanel.java
│       ├── ClinicManagerRole/
│       │   ├── ClinicManagerWorkAreaJPanel.java
│       │   ├── RequestLabTestJPanel.java
│       │   └── SchedulePatientAppointmentsJPanel.java
│       ├── NursePractitionerRole/
│       │   ├── NursePractitionerWorkAreaJPanel.java
│       │   └── ReportDiseaseCasesJPanel.java
│       ├── DoctorRole/
│       │   └── (Doctor panels)
│       ├── LabTechRole/
│       │   └── LabTechWorkAreaJPanel.java
│       ├── PharmacistRole/
│       │   └── PharmacistWorkAreaJPanel.java
│       └── VaccineStorageSpecialistRole/
│           └── VaccineStorageSpecialistWorkAreaJPanel.java
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
1. **Nurse Practitioner** at Brooklyn Clinic reports COVID-19 cases
2. Report automatically routed to **Public Health Officer** at State Health Dept
3. Officer reviews and forwards to **Epidemiologist** at CDC
4. Epidemiologist analyzes trends and issues alerts

### Cross-Enterprise Workflow: Vaccine Allocation
1. **Provider Coordinator** at State requests flu vaccines from CDC
2. **Distribution Coordinator** at CDC reviews request
3. Coordinator approves allocation and schedules shipment
4. Vaccines distributed to state and then to clinics/hospitals

### Cross-Organization Workflow: Patient Appointment
1. **Clinic Manager** schedules patient appointment
2. Request automatically routed to **Hospital Administrator**
3. Administrator confirms appointment slot
4. **Nurse Practitioner** or **Doctor** provides care

### Cross-Organization Workflow: Lab Testing (NEW)
1. **Clinic Manager** submits lab test request for patient
2. Request automatically routed to **Lab Organization**
3. **Lab Technician** processes test and records results
4. Results available to clinic manager for patient follow-up

### Cross-Organization Workflow: Prescription Fulfillment (NEW)
1. **Hospital Administrator** submits prescription request
2. Request automatically routed to **Pharmacy Organization**
3. **Pharmacist** reviews and fulfills prescription
4. Pharmacist adds fulfillment notes visible to hospital admin

### Cross-Organization Workflow: Cold Chain Management (NEW)
1. **Vaccine Storage Specialist** reports cold chain failure
2. Request automatically routed to **Provider Coordinator**
3. Coordinator reviews failure details and approves replacement
4. Replacement vaccines allocated and contaminated vaccines disposed

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
3. **Email/SMS Notifications**: Alert system for critical events
4. **Advanced Search**: Enhanced filter capabilities across all tables
5. **Audit Trail**: Complete logging of all system actions
6. **Mobile Responsive**: Web-based interface
7. **Real-time Dashboards**: Live data updates with charts
8. **PDF Export**: Professional report generation

---

## FAKER LIBRARY INTEGRATION

### Benefits
✅ **Realistic Demo Data**: Professional-looking names, emails, phone numbers  
✅ **Analytics-Ready**: 60+ employees and 40+ work requests for dashboard testing  
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

## KEY FEATURES UPDATE (December 2024)

### Newly Implemented Features

✅ **System Reporting Dashboard**
- Comprehensive analytics at ecosystem level with 6 tabbed views:
  - Overview: System-wide statistics with 9 key metrics
  - Work Requests: Analysis by type, status, and percentage distribution
  - Employees: Enterprise and role distribution
  - Organizations: Performance metrics per organization
  - Health Data: Vaccine inventory and disease registry
  - Export: CSV export for all major reports

✅ **Enhanced Admin CRUD Operations**
- **Employee Management**: Full Create-Read-Update-Delete operations
  - Form validation (name, email, phone format)
  - Real-time table updates
  - Organization-based filtering
  - Confirmation dialogs for deletions
  
- **User Account Management**: Complete account lifecycle
  - Username/password validation
  - Role assignment with organization-based filtering
  - Account enable/disable toggle
  - Password reset with account unlock
  - Failed login attempts display

✅ **New Workflow Implementations (December 2024)**
- **Lab Test Workflow**: Clinic Manager → Lab Technician
  - Request lab tests for patients with urgency levels
  - Lab technicians process tests and add results
  - Cross-organization routing (Clinic → Lab)
  - 5 pre-populated demo lab test requests
  
- **Prescription Workflow**: Hospital Admin → Pharmacist
  - Submit prescription requests with patient details
  - 10 medication options (Penicillin, Gabapentin, etc.)
  - Pharmacists fulfill prescriptions and add notes
  - Enhanced table with 7 columns including quantity and fulfillment notes
  - 5 pre-populated demo prescriptions (3 pending, 2 fulfilled)
  
- **Cold Chain Failure Workflow**: Storage Specialist → Provider Coordinator
  - Report vaccine storage failures (temperature, equipment, power)
  - Track affected vaccines and exposure duration
  - Coordinator approves replacements and manages disposal
  - 4 pre-populated cold chain failure reports

✅ **Cross-Organization Routing Fixes (December 2024)**
- Fixed disease report routing: Nurse → Public Health → CDC
- Fixed vaccine shipment routing: Clinic → Provider Registry
- Fixed patient appointment routing: Clinic → Hospital
- Fixed epidemiologist work area: Proper filtering of mixed request types
- All workflows now properly route to both sender and receiver queues

✅ **Comprehensive Error Handling**
- Try-catch blocks throughout all operations
- NULL checks before data access
- User-friendly error dialogs with specific messages
- Validation exceptions with clear guidance
- Fixed ClassCastException in Epidemiologist work area

✅ **Export Functionality**
- System Overview (CSV)
- Work Requests Analysis (CSV)
- Complete Employee List (CSV)
- Organization Details (CSV)
- Health Data Export (CSV)
- Timestamped filenames for all exports

---

## RECENT UPDATES & BUG FIXES (December 2024)

### Pull Request #5 - Merged from maxwellsowell
**Status**: ✅ Approved and Merged

**Changes**:
- Enhanced SubmitPrescriptionJPanel UI with improved table layout
- Added "Quantity" column to prescription display (now 7 columns)
- Reordered columns for better readability: Date, Patient, Medication, Dosage, Quantity, Status, Notes
- Quantity displays as "X pills" format for clarity
- Code formatting improvements in ConfigureABusiness.java
- Updated author attribution for collaborative work

**Files Modified**:
- `src/ui/HospitalAdminRole/SubmitPrescriptionJPanel.java`
- `src/business/ConfigureABusiness.java`

### Critical Bug Fixes
1. **Disease Report Routing** (susan.taylor → emily.thompson)
   - Fixed: Nurse disease reports now route to Public Health Services
   - Added cross-organization routing with proper disease report fields
   
2. **Epidemiologist ClassCastException**
   - Fixed: Proper filtering when work queue contains mixed request types
   - Prevents crashes when viewing disease reports with other request types present
   
3. **Patient Appointment Routing** (christopher.lee → amanda.garcia)
   - Fixed: Clinic appointments now route to Hospital organization
   - Cross-organization visibility for appointment coordination

### System Enhancements
- All 9 workflow types fully tested and operational
- 60+ employees across 9 organizations
- 40+ pre-populated work requests demonstrating all workflows
- Enhanced demo data with realistic scenarios
- Improved table displays across all role panels

---

## TEAM CONTRIBUTIONS

**Akira Hanada:**
- Business model architecture (EcoSystem, Network, Enterprise)
- Security implementation (password hashing, validation)
- All Role and Organization implementations (13 roles, 9 org types)
- All Work Request types and cross-organization routing (9 request types)
- System configuration and comprehensive data population
- Bug fixes and workflow validations
- System reporting dashboard and analytics

**Maxwell Sowell:**
- Lab test workflow implementation
- Prescription workflow implementation
- Cold chain failure workflow implementation
- Testing and quality assurance across all workflows
- UI enhancements and table improvements
- Bug fixes and error handling
- User acceptance testing and validation

**Travis Hodo:**
- Initial UI/UX design and implementation
- Work area panels foundation
- Authentication system
- Form validations
- (Withdrawn from the group - contributions through early development)

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
