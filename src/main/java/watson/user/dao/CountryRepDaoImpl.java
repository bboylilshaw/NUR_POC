package watson.user.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import watson.user.model.CountryRep;

import java.util.List;

@Repository("countryRepDao")
public class CountryRepDaoImpl implements CountryRepDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public boolean isCountryRep(String domainUserName) {
        String hql = "from CountryRep as cr where cr.domainUserName=:domainUserName and cr.effectiveStatus=:active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<CountryRep> countryReps = query.setString("domainUserName", domainUserName)
                                            .setString("active", "A")
                                            .list();
        return countryReps.size() <= 0;
    }

    @Override
    public CountryRep getCountryRepByCountryCode(String countryCode) {
        String hql = "from CountryRep as cr where cr.countryCode=:countryCode and cr.effectiveStatus=:active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<CountryRep> countryReps = query.setString("countryCode", countryCode)
                                            .setString("active", "A")
                                            .list();
        return countryReps.size() == 0 ? null : countryReps.get(0);
    }

    @Override
    public CountryRep getCountryRep(String domainUserName, String countryCode) {
        return null;
    }

}
