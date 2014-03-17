package watson.admin.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import watson.admin.model.HPUser;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("hpUserDao")
public class HPUserDaoImpl implements HPUserDao {

    private final Logger logger = Logger.getLogger(HPUserDaoImpl.class);
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
        HPUserDaoImpl hpUserDao = applicationContext.getBean("hpUserDao", HPUserDaoImpl.class);
        System.out.println(hpUserDao.getHPUserByDomainUserName("asiapacific\\xiaoyao", "apwatson"));
    }
}
