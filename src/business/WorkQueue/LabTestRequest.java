/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.WorkQueue;

/**
 * Work request for laboratory test processing
 * 
 * @author Maxwell Sowell
 */
public class LabTestRequest extends WorkRequest{
    
    
    private String testType;
    private String urgencyLevel;
    private String testResult;
    
    
    public LabTestRequest() {
        super();
        this.urgencyLevel = "Normal";
        this.setStatus("Pending");
    }

    
    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
    
    
}
