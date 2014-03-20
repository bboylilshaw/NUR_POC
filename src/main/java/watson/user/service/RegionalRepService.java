package watson.user.service;

import watson.user.model.Request;

public interface RegionalRepService {

    public Request reviewRequest(String requestID);

    public void approveRequest(String requestID, String regionalRepEmail, String comments);

    public void denyRequest(String requestID, String regionalRepEmail, String comments);

}
