package watson.user.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-application-context.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class CountryRepDaoImplTest {

    @Autowired
    private CountryRepDaoImpl countryRepDao;

    @Test
    public void testExists() throws Exception {
        Assert.assertTrue(countryRepDao.exists("ASIAPACIFIC\\xiaoyao"));
    }

    @Test
    public void testGetCountryRep() throws Exception {
        Assert.assertEquals("ASIAPACIFIC\\xiaoyao", countryRepDao.getCountryRep("apwatson", "CN").getDomainUserName());
        Assert.assertNull(countryRepDao.getCountryRep("watson", "ABC"));
    }

}
