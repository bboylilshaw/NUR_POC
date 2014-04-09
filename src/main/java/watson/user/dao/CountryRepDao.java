package watson.user.dao;

import watson.user.model.CountryRep;

public interface CountryRepDao {

    public boolean isCountryRep(String domainUserName);

    public CountryRep getCountryRep(String watsonInstance, String countryCode);

}
