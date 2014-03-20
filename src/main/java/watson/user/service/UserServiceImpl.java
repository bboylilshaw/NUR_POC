package watson.user.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.dao.RequestDaoImpl;
import watson.user.dao.UserDaoImpl;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;
    private RequestDaoImpl requestDao;

    @Resource
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Resource
    public void setRequestDao(RequestDaoImpl requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    @Transactional
    public void requestAccess(String domainUserName, String instance, String comments) {
        //check if user already have the access to the instance
        boolean userExists = userDao.userExists(domainUserName, instance);
        boolean allowToSubmit = requestDao.allowToSubmit(domainUserName, instance);

        //if user doesn't exist and allow to submit the request
        if (!userExists && allowToSubmit) {
            userDao.submitRequest(domainUserName, instance, comments);
            //TODO get manager info and send notifications
        } else {//throw an error that user is not eligible to submit request
            throw new RuntimeException("User already have the access or request is WIP/Approved");
        }

    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        UserService userService = applicationContext.getBean("userService", UserServiceImpl.class);
        userService.requestAccess("asiapacific\\xiaoyao", "apwatson", "need access to Watson");
    }

}
