package watson.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import watson.user.model.HPUser;

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

    public static void main(String[] args) {
        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("application.xml");
        UserDaoImpl hpUserDao = applicationContext.getBean("userDao", UserDaoImpl.class);
        System.out.println(hpUserDao.getHPUserByDomainUserName("asiapacific\\xiaoyao", "apwatson"));
    }
}
