package watson.user.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import watson.user.model.HPEmployee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-application-context.xml"})
public class LDAPServiceImplTest {

    @Autowired
    private LDAPServiceImpl ldapService;

    @Test
    public void testGetEmployeeData(){
        HPEmployee hpEmployee = ldapService.getEmployeeDataByDomainUserName("ASIAPACIFIC\\xiaoyao");
        System.out.println("HP Employee: ");
        System.out.println("   DomainUserName = " + hpEmployee.getDomainUserName());
        System.out.println("   Employee Id = " + hpEmployee.getEmployeeId());
        System.out.println("   Employee Name = " + hpEmployee.getName());
        System.out.println("   Employee Mail = " + hpEmployee.getEmail());
        System.out.println("   Employee Job Function = " + hpEmployee.getJobFunction());
        System.out.println("   Employee Country = " + hpEmployee.getCountry());
        System.out.println("   Manager Employee Id = " + hpEmployee.getManagerEmployeeId());
        System.out.println("   Manager Name = " + hpEmployee.getManagerName());
        System.out.println("   Manager Mail = " + hpEmployee.getManagerEmail());
    }

    @Test
    public void testAuthenticateUser(){
        Assert.assertTrue(ldapService.authenticateUser("yao.xiao@hp.com", ""));
    }
}
