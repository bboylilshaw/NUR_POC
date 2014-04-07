package watson.user.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import watson.user.model.HPEmployee;

public class LDAPServiceImplTest {

    public static ApplicationContext ctx;

    @Before
    public void getContext(){
        ctx = new ClassPathXmlApplicationContext("WEB-INF/spring-application-context.xml");
    }

    @Test
    public void testGetEmployeeData(){
        HPEmployee hpEmployee = ctx.getBean("ldapService", LDAPServiceImpl.class).getEmployeeDataByDomainUserName("ASIAPACIFIC\\xiaoyao");
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
        boolean actual = ctx.getBean("ldapService", LDAPServiceImpl.class).authenticateUser("yao.xiao@hp.com", "");
        Assert.assertTrue(actual);
    }
}
