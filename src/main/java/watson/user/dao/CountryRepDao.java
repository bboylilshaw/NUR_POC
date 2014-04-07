package watson.user.dao;

import watson.user.model.CountryRep;

public interface CountryRepDao {

    public CountryRep getCountryRepByCountryCode(String countryCode);

    public CountryRep getCountryRep(String domainUserName, String countryCode);

}
