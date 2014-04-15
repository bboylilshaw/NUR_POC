package watson.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "HP_USER")
public class HPUser {
    private String gid;
    private String watsonInstance;
    private String domainUserName;
    private String domainUserNameIndex;
    private String name;
    private String firstName;
    private String lastName;
    private String employeeId;
    private String email;
    private String userStatus;
    private Date lastLoginDate;
    private Date createDate;
    private String createdBy;
    private Date lastModifyDate;
    private String lastModifiedBy;

    @Id
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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

    public String getDomainUserNameIndex() {
        return domainUserNameIndex;
    }

    public void setDomainUserNameIndex(String domainUserNameIndex) {
        this.domainUserNameIndex = domainUserNameIndex;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
