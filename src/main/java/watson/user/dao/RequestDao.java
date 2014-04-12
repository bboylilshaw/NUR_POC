package watson.user.dao;

import watson.user.model.CountryRep;
import watson.user.model.HPEmployee;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

import java.util.List;

public interface RequestDao {

    public Request getRequestById(String requestId);

    public boolean exists(String domainUserName, String watsonInstance);

    public boolean isApproved(String domainUserName, String watsonInstance);

    public boolean isPendingApproval(String domainUserName, String watsonInstance);

    public String save(HPEmployee hpEmployee, String watsonInstance, String comments);

    public Request updatedByManager(Request request, String proceedAction, String comments, CountryRep countryRep, RegionalRep regionalRep);

    public Request updatedByManager(Request request, String proceedAction, String comments, CountryRep countryRep, RegionalRep regionalRep, boolean skipCountryRep);

    public Request updatedByCountryRep(Request request, String proceedAction, String comments, RegionalRep regionalRep);

    public Request updatedByRegionalRep(Request request, String proceedAction, String comments);

    public List listOpenRequests(String domainUserName);

    public List<Request> listRequestsAwaitingApproval(String domainUserName);

    public List<Request> listRequestsAwaitingApproval(String domainUserName, String approverLevel);

}
