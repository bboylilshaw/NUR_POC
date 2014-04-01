package watson.user.dao;

import watson.user.model.Request;

import java.util.List;

public interface RequestDao {

    public Request getRequestByID(String requestID);

    public String proceedByManager(String requestID, String managerEmail, String proceedAction, String comments);

    public String proceedByCountryRep(String requestID, String countryRepEmail, String proceedAction, String comments);

    public String proceedByRegionalRep(String requestID, String regionalRepEmail, String proceedAction, String comments);

    public boolean allowToSubmit(String domainUserName, String instance);

    public void setRequestExpired(String requestID, String expiredLevel);

    public List<Request> listOpenAccessRequests(String domainUserName);

    public List<Request> listAccessRequestsAwaitingApproval(String domainUserName);

}
