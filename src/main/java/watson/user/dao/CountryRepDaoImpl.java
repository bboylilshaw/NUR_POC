package watson.user.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import watson.user.model.CountryRep;

@Repository("countryRepDao")
public class CountryRepDaoImpl implements CountryRepDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public boolean exists(String domainUserName) {
        String hql = "from CountryRep as cr where cr.domainUserName=:domainUserName and cr.effectiveStatus=:active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        int results = query.setString("domainUserName", domainUserName)
                           .setString("active", "A")
                           .list().size();
        return results > 0;
    }

    @Override
    public CountryRep getCountryRep(String watsonInstance, String countryCode) {
        String hql = "from CountryRep as cr where cr.watsonInstance=:watsonInstance and cr.countryCode=:countryCode and cr.effectiveStatus=:active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        return (CountryRep) query.setString("watsonInstance", watsonInstance)
                                 .setString("countryCode", countryCode)
                                 .setString("active", "A")
                                 .uniqueResult();
    }

}
