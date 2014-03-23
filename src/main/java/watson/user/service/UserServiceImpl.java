package watson.user.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.dao.RequestDaoImpl;
import watson.user.dao.UserDaoImpl;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;
    private RequestDaoImpl requestDao;
    private NotificationServiceImpl notificationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void requestAccess(String domainUserName, String instance, String comments) throws Exception {
        //check if user already have the access to the instance
        boolean userExists = userDao.userExists(domainUserName, instance);
        boolean allowToSubmit = requestDao.allowToSubmit(domainUserName, instance);

        //if user doesn't exist and allow to submit the request
        if (!userExists && allowToSubmit) {
            String requestId = userDao.submitRequest(domainUserName, instance, comments);
            //TODO get manager info and send notifications
            String toManagerEmail = userDao.getManagerEmail(domainUserName);
            String ccEmail = userDao.getEmail(domainUserName);
            String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
            HashMap<String, String> model = new HashMap<String, String>();
            model.put("approver", "Approver");
            model.put("url", "http://localhost:8080/NUR_POC/manager/review/request/" + requestId);
            notificationService.sendEmailWithTemplate(toManagerEmail, ccEmail, templateName, model);
        } else {//throw an error that user is not eligible to submit request
            throw new Exception("User already have the access to " + instance + ", or request is WIP/Approved");
        }

    }

    @Resource
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Resource
    public void setRequestDao(RequestDaoImpl requestDao) {
        this.requestDao = requestDao;
    }

    @Resource
    public void setNotificationService(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserServiceImpl.class);
        try {
            userService.requestAccess("asiapacific\\xiaoyao", "apwatson", "need access to Watson");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
