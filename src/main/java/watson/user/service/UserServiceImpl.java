package watson.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.dao.RequestDaoImpl;
import watson.user.dao.UserDaoImpl;
import watson.user.model.HPEmployee;
import watson.user.model.Request;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;
    private RequestDaoImpl requestDao;
    private NotificationServiceImpl notificationService;
    private LDAPServiceImpl ldapService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void requestAccess(String domainUserName, String instance, String comments) throws Exception {
        //check if user already have the access to the instance
        boolean userExists = userDao.userExists(domainUserName, instance);
        boolean allowToSubmit = requestDao.allowToSubmit(domainUserName, instance);

        //if user doesn't exist and allow to submit the request
        if (!userExists && allowToSubmit) {
            HPEmployee hpEmployee = ldapService.getEmployeeDataByDomainUserName(domainUserName);
            String requestId = userDao.submitRequest(hpEmployee, instance, comments);
            //TODO get manager info and send notifications
            String toManagerEmail = "yao.xiao@hp.com";
            String ccEmail = hpEmployee.getEmail();
            String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
            HashMap<String, String> model = new HashMap<String, String>();
            model.put("approver", hpEmployee.getFirstName());
            model.put("url", "http://localhost:8080/NUR_POC/manager/review/request/" + requestId);
            notificationService.sendEmailWithTemplate(toManagerEmail, ccEmail, templateName, model);
        } else {//throw an error that user is not eligible to submit request
            throw new Exception("User already have the access to " + instance + ", or request is WIP/Approved");
        }

    }

    @Override
    @Transactional
    public List<Request> listCurrentAccess(String domainUserName) {
        //TODO need to check HPUser for current status
        return null;
    }

    @Override
    @Transactional
    public List<Request> listOpenAccessRequests(String domainUserName) {
        return requestDao.listOpenAccessRequests(domainUserName);
    }

    @Override
    @Transactional
    public List<Request> listAccessRequestsAwaitingApproval(String domainUserName) {
        return requestDao.listAccessRequestsAwaitingApproval(domainUserName);
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

    @Resource
    public void setLdapService(LDAPServiceImpl ldapService) {
        this.ldapService = ldapService;
    }
}
