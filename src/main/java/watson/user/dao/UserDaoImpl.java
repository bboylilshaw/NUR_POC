package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import watson.user.commons.RequestStatus;
import watson.user.model.HPEmployee;
import watson.user.model.HPUser;
import watson.user.model.Request;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private SessionFactory sessionFactory;

    @Override
    public HPUser getHPUserByDomainUserName(String domainUserName, String instance) {
        HPUser hpUser = null;
        Query query = sessionFactory.getCurrentSession().createQuery("from HPUser as u where u.domainUserName=:domainUserName and u.instance=:instance");
        List<HPUser> results = (ArrayList<HPUser>) query.setString("domainUserName", domainUserName)
                                                        .setString("instance", instance)
                                                        .list();
        if (results.size() > 0) {
            hpUser = results.get(0);
        }
        return hpUser;
    }

    @Override
    public String submitRequest(HPEmployee hpEmployee, String instance, String comments) {
        Request request = new Request(hpEmployee.getDomainUserName(), instance, comments);
        request.setManagerDomainUserName(hpEmployee.getManagerDomainUserName());
        request.setManagerEmail(hpEmployee.getManagerEmail());
        request.setManagerProceed(RequestStatus.INITIAL);
        request.setFinalResult(RequestStatus.WIP);
        sessionFactory.getCurrentSession().save(request);
        return request.getRequestID();
    }

    @Override
    public boolean userExists(String domainUserName, String instance) {
        if (getHPUserByDomainUserName(domainUserName, instance) != null) {
            return true;
        }
        return false;
    }

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
