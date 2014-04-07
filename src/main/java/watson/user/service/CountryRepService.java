package watson.user.service;

import watson.user.model.CountryRep;
import watson.user.model.RegionalRep;
import watson.user.model.Request;

public interface CountryRepService {

    public Request reviewRequest(String requestId);

    public void proceedRequest(String requestId, CountryRep countryRep, String proceedAction, String comments) throws Exception;

    public RegionalRep getRegionalRep(String watsonInstance);
}
