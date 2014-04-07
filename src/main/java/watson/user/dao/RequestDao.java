package watson.user.dao;

import watson.user.model.CountryRep;
import watson.user.model.HPEmployee;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

import java.util.List;

public interface RequestDao {

    public Request getRequestById(String requestId);

    public String submitRequest(HPEmployee hpEmployee, String watsonInstance, String comments);

    public String approvedByManager(String requestId, String comments);

    public String approvedByManager(String requestId, String comments, boolean skipCountryRep);

    public String deniedByManager(String requestId, String comments);

    public String approvedByCountryRep(String requestId, CountryRep countryRep, String comments);

    public String deniedByCountryRep(String requestId, CountryRep countryRep, String comments);

    public String approvedByRegionalRep(String requestId, RegionalRep regionalRep, String comments);

    public String deniedByRegionalRep(String requestId, RegionalRep regionalRep, String comments);

    public boolean allowToSubmit(String domainUserName, String watsonInstance);

    public void setRequestExpired(String requestId, String expiredLevel);

    public List<Request> listOpenAccessRequests(String domainUserName);

    public List<Request> listAccessRequestsAwaitingApproval(String domainUserName);

}
