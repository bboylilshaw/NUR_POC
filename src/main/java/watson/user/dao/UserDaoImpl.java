package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import watson.user.model.HPUser;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDaoImpl.class);
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public HPUser getHPUserByDomainUserName(String domainUserName, String watsonInstance) {
        String hql = "from HPUser as u where u.domainUserName=:domainUserName and u.watsonInstance=:watsonInstance";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<HPUser> hpUsers = query.setString("domainUserName", domainUserName)
                                    .setString("watsonInstance", watsonInstance)
                                    .list();
        return hpUsers.size() == 0 ? null : hpUsers.get(0);
    }

    @Override
    public boolean userExists(String domainUserName, String watsonInstance) {
        if (this.getHPUserByDomainUserName(domainUserName, watsonInstance) != null) {
            return true;
        }
        return false;
    }

}
