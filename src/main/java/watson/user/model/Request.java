package watson.user.model;

import watson.user.commons.RequestStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "REQUEST")
public class Request {
    private String requestId;
    private String watsonInstance;
    private String domainUserName;
    private String employeeId;
    private String email;
    private Date requestDate;
    private String comments;
    private String managerDomainUserName;
    private String managerEmployeeId;
    private String managerEmail;
    private String managerProceed;
    private Date managerProceedDate;
    private String managerComments;
    private String countryRepDomainUserName;
    private String countryRepEmployeeId;
    private String countryRepEmail;
    private String countryRepProceed;
    private Date countryRepProceedDate;
    private String countryRepComments;
    private String regionalRepDomainUserName;
    private String regionalRepEmployeeId;
    private String regionalRepEmail;
    private String regionalRepProceed;
    private Date regionalRepProceedDate;
    private String regionalRepComments;
    private String finalResult;

    public Request() {
        this.requestId = UUID.randomUUID().toString();
        this.finalResult = RequestStatus.INITIAL;
    }

    @Id
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getWatsonInstance() {
        return watsonInstance;
    }

    public void setWatsonInstance(String watsonInstance) {
        this.watsonInstance = watsonInstance;
    }

    public String getDomainUserName() {
        return domainUserName;
    }

    public void setDomainUserName(String domainUserName) {
        this.domainUserName = domainUserName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getManagerDomainUserName() {
        return managerDomainUserName;
    }

    public void setManagerDomainUserName(String managerDomainUserName) {
        this.managerDomainUserName = managerDomainUserName;
    }

    public String getManagerEmployeeId() {
        return managerEmployeeId;
    }

    public void setManagerEmployeeId(String managerEmployeeId) {
        this.managerEmployeeId = managerEmployeeId;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerProceed() {
        return managerProceed;
    }

    public void setManagerProceed(String managerProceed) {
        this.managerProceed = managerProceed;
    }

    public Date getManagerProceedDate() {
        return managerProceedDate;
    }

    public void setManagerProceedDate(Date managerProceedDate) {
        this.managerProceedDate = managerProceedDate;
    }

    public String getManagerComments() {
        return managerComments;
    }

    public void setManagerComments(String managerComments) {
        this.managerComments = managerComments;
    }

    public String getCountryRepDomainUserName() {
        return countryRepDomainUserName;
    }

    public void setCountryRepDomainUserName(String countryRepDomainUserName) {
        this.countryRepDomainUserName = countryRepDomainUserName;
    }

    public String getCountryRepEmployeeId() {
        return countryRepEmployeeId;
    }

    public void setCountryRepEmployeeId(String countryRepEmployeeId) {
        this.countryRepEmployeeId = countryRepEmployeeId;
    }

    public String getCountryRepEmail() {
        return countryRepEmail;
    }

    public void setCountryRepEmail(String countryRepEmail) {
        this.countryRepEmail = countryRepEmail;
    }

    public String getCountryRepProceed() {
        return countryRepProceed;
    }

    public void setCountryRepProceed(String countryRepProceed) {
        this.countryRepProceed = countryRepProceed;
    }

    public Date getCountryRepProceedDate() {
        return countryRepProceedDate;
    }

    public void setCountryRepProceedDate(Date countryRepProceedDate) {
        this.countryRepProceedDate = countryRepProceedDate;
    }

    public String getCountryRepComments() {
        return countryRepComments;
    }

    public void setCountryRepComments(String countryRepComments) {
        this.countryRepComments = countryRepComments;
    }

    public String getRegionalRepDomainUserName() {
        return regionalRepDomainUserName;
    }

    public void setRegionalRepDomainUserName(String regionalRepDomainUserName) {
        this.regionalRepDomainUserName = regionalRepDomainUserName;
    }

    public String getRegionalRepEmployeeId() {
        return regionalRepEmployeeId;
    }

    public void setRegionalRepEmployeeId(String regionalRepEmployeeId) {
        this.regionalRepEmployeeId = regionalRepEmployeeId;
    }

    public String getRegionalRepEmail() {
        return regionalRepEmail;
    }

    public void setRegionalRepEmail(String regionalRepEmail) {
        this.regionalRepEmail = regionalRepEmail;
    }

    public String getRegionalRepProceed() {
        return regionalRepProceed;
    }

    public void setRegionalRepProceed(String regionalRepProceed) {
        this.regionalRepProceed = regionalRepProceed;
    }

    public Date getRegionalRepProceedDate() {
        return regionalRepProceedDate;
    }

    public void setRegionalRepProceedDate(Date regionalRepProceedDate) {
        this.regionalRepProceedDate = regionalRepProceedDate;
    }

    public String getRegionalRepComments() {
        return regionalRepComments;
    }

    public void setRegionalRepComments(String regionalRepComments) {
        this.regionalRepComments = regionalRepComments;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }
}
