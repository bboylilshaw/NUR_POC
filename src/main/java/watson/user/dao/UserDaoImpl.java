package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import watson.user.model.HPUser;
import watson.user.model.Request;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private SessionFactory sessionFactory;

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public HPUser getHPUserByDomainUserName(String domainUserName, String instance) {
        HPUser hpUser = null;
        Query query = sessionFactory.getCurrentSession().createQuery("from HPUser as u where u.domainUserName=:domainUserName");
        List<HPUser> results = (ArrayList<HPUser>)query.setString("domainUserName", domainUserName).list();
        if (results.size() > 0) {
            hpUser = results.get(0);
        }
        return hpUser;
    }

    @Transactional
    @Override
    public String submitRequest(String domainUserName, String instance, String comments) {
        Request request = new Request(domainUserName, instance, comments);
        request.setManagerProceed("IN");
        sessionFactory.getCurrentSession().save(request);
        return request.getRequestID();
    }

    @Override
    public String getManager(String domainUserName) {
        //fake manager info
        return "xiaoyao8823@gmail.com";
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("application.xml");
        UserDao hpUserDao = applicationContext.getBean("userDaoImpl", UserDao.class);
        //System.out.println(hpUserDao.getHPUserByDomainUserName("asiapacific\\xiaoyao", "apwatson"));
        hpUserDao.submitRequest("asiapacific\\xiaoyao", "apwatson", "need access to Watson");
    }
}
