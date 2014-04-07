package watson.user.service;

import watson.user.model.CountryRep;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

public interface ManagerService {

    public Request reviewRequest(String requestId);

    public void proceedRequest(String requestId, String proceedAction, String comments) throws Exception;

    public CountryRep getCountryRep(Request request);

    public RegionalRep getRegionalRep(String watsonInstance);

}
