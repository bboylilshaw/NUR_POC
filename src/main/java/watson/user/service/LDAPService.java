package watson.user.service;

import watson.user.model.HPEmployee;

import java.util.List;

public interface LDAPService {

    public boolean authenticateUser(String email, String password);

    public HPEmployee getEmployeeDataByEmail(String email);

    public HPEmployee getEmployeeDataByDomainUserName(String domainUserName);

    public List<HPEmployee> getEmployeeData(List<String> domainUserNames);

}
