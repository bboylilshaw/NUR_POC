package watson.user.dao;

import watson.user.model.CountryRep;
import watson.user.model.HPEmployee;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

import java.util.List;

public interface RequestDao {

    public Request getRequestById(String requestId);

    public String submitRequest(HPEmployee hpEmployee, String watsonInstance, String comments);

    public Request proceededByManager(Request request, String proceedAction, String comments, CountryRep countryRep, RegionalRep regionalRep);

    public Request proceededByManager(Request request, String proceedAction, String comments, CountryRep countryRep, RegionalRep regionalRep, boolean skipCountryRep);

    public Request proceededByCountryRep(Request request, String proceedAction, String comments, RegionalRep regionalRep);

    public Request proceededByRegionalRep(Request request, String proceedAction, String comments);

    public boolean allowToSubmit(String domainUserName, String watsonInstance);

    public List<Request> listOpenRequests(String domainUserName);

    public List<Request> listRequestsAwaitingApproval(String domainUserName);

    public List<Request> listRequestsAwaitingManagerApproval(String managerDomainUserName);

    public List<Request> listRequestsAwaitingCountryRepApproval(String watsonInstance, String countryCode);

    public List<Request> listRequestsAwaitingRegionalRepApproval(String watsonInstance);

    public int getCurrentApprovalLevel(String requestId);

}
