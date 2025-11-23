# JavaFaker Library Setup Instructions

## Required Library
- **JavaFaker 1.0.2** (or latest version)
- Download from: https://github.com/DiUS/java-faker

## Installation Steps

### Option 1: Manual JAR Download (Recommended for NetBeans)

1. **Download JavaFaker JAR:**
   - Go to https://repo1.maven.org/maven2/com/github/javafaker/javafaker/1.0.2/
   - Download `javafaker-1.0.2.jar`

2. **Add to Project in NetBeans:**
   - Right-click on project → Properties
   - Select "Libraries" category
   - Click "Add JAR/Folder"
   - Navigate to downloaded `javafaker-1.0.2.jar`
   - Click "Open" then "OK"

### Option 2: Maven Dependency (If using Maven)

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>com.github.javafaker</groupId>
    <artifactId>javafaker</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Option 3: Direct Library Folder

1. Create a `lib` folder in your project root
2. Place `javafaker-1.0.2.jar` in the `lib` folder
3. In NetBeans:
   - Right-click project → Properties → Libraries
   - Click "Add JAR/Folder"
   - Select `lib/javafaker-1.0.2.jar`

## Verification

After adding the library, the import statements should work:
```java
import com.github.javafaker.Faker;
```

## What's Changed

The `ConfigureABusiness.java` class now uses Faker to generate:
- **50+ realistic employee names** with proper titles (Dr., Nurse, etc.)
- **Professional email addresses** matching organizational domains
- **Properly formatted phone numbers** (area codes matching locations)
- **Realistic job positions** and specializations
- **20+ work requests** demonstrating all workflow types
- **Additional vaccines and diseases** for better analytics

## Benefits of Faker Integration

✅ **Realistic Demo Data**: Names, emails, and phone numbers look authentic  
✅ **Analytics-Ready**: 50+ employees and 20+ work requests for dashboard testing  
✅ **Professional Presentation**: Data appears production-quality  
✅ **Easy to Regenerate**: Change seed for different datasets  
✅ **Meets Requirements**: Fulfills Faker module requirement

## Run the Application

Once library is added:
1. Clean and Build project (Shift+F11)
2. Run project (F6)
3. Login with admin credentials: `admin` / `Admin@2024!`
4. Explore Manage Employees/Users to see Faker-generated data
