package watson.user.dao;

import watson.user.model.CountryRep;

public interface CountryRepDao {

    public boolean exists(String domainUserName);

    public CountryRep getCountryRep(String watsonInstance, String countryCode);

}
