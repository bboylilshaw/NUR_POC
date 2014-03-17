package watson.user.service;

import watson.user.model.Request;

public interface ManagerService {

    public Request reviewRequest(String requestID);

    public void approveRequest(String requestID, String managerEmail, String comments);

    public void denyRequest(String requestID, String managerEmail, String comments);

    public void passToCountryRep(String requestID);

    public void passToRegionalRep(String requestID);
}
