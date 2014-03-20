package watson.user.service;

import watson.user.model.Request;

public interface CountryRepService {

    public Request reviewRequest(String requestID);

    public void approveRequest(String requestID, String countryRepEmail, String comments);

    public void denyRequest(String requestID, String countryRepEmail, String comments);

    public void passToRegionalRep(String requestID);
}
