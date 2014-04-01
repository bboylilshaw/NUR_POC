package watson.user.model;

public class HPEmployee {
    //private static final char DOMAIN_USERNAME_SEPARATOR_CHAR    = '\\';
    //private static final char DOMAIN_USERNAME_SEPARATOR_CHAR_ED = ':';

    protected String domainUserName;
    protected String employeeId;
    protected String name;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String jobFunction;
    protected String orgUnit;
    protected String country;
    protected String hpStatus;
    protected String managerEmployeeId;
    protected String managerDomainUserName;
    protected String managerName;
    protected String managerEmail;

    protected String domainName;
    protected String userName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobFunction() {
        return jobFunction;
    }

    public void setJobFunction(String jobFunction) {
        this.jobFunction = jobFunction;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHpStatus() {
        return hpStatus;
    }

    public void setHpStatus(String hpStatus) {
        this.hpStatus = hpStatus;
    }

    public String getManagerEmployeeId() {
        return managerEmployeeId;
    }

    public void setManagerEmployeeId(String managerEmployeeId) {
        this.managerEmployeeId = managerEmployeeId;
    }

    public String getManagerDomainUserName() {
        return managerDomainUserName;
    }

    public void setManagerDomainUserName(String managerDomainUserName) {
        this.managerDomainUserName = managerDomainUserName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
