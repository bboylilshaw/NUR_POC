package watson.user.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import watson.user.model.RegionalRep;

import java.util.List;

@Repository("regionalRepDao")
public class RegionalRepDaoImpl implements RegionalRepDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public RegionalRep getRegionalRep(String watsonInstance) {
        String hql = "from RegionalRep as rr where rr.watsonInstance=:watsonInstance and rr.effectiveStatus=:active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<RegionalRep> regionalReps = query.setString("watsonInstance", watsonInstance)
                                              .setString("active", "A")
                                              .list();
        return regionalReps.size() == 0 ? null : regionalReps.get(0);
    }

}
