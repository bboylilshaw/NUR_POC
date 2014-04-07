package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.dao.RequestDaoImpl;
import watson.user.dao.UserDaoImpl;
import watson.user.model.HPEmployee;
import watson.user.model.Request;

import java.util.HashMap;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired private UserDaoImpl userDao;
    @Autowired private RequestDaoImpl requestDao;
    @Autowired private NotificationServiceImpl notificationService;
    @Autowired private LDAPServiceImpl ldapService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void requestAccess(HPEmployee hpEmployee, String watsonInstance, String comments) throws Exception {
        //check if user is qualified to request access
        boolean userExists = userDao.userExists(hpEmployee.getDomainUserName(), watsonInstance);
        boolean allowToSubmit = requestDao.allowToSubmit(hpEmployee.getDomainUserName(), watsonInstance);

        //if user doesn't exist and allow to submit the request
        if (!userExists && allowToSubmit) {
            String requestId = requestDao.submitRequest(hpEmployee, watsonInstance, comments);
            //FIXME: get manager info and send notifications, hard code for now
            String toManagerEmail = "yao.xiao@hp.com";
            String ccEmail = "yao.xiao@hp.com";
            String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
            HashMap<String, String> model = new HashMap<String, String>();
            model.put("approver", "Approver");
            model.put("url", "http://localhost:8080/NUR_POC/manager/review/request/" + requestId);
            notificationService.sendEmailWithTemplate(toManagerEmail, ccEmail, templateName, model);
        } else {//throw an error that user is not eligible to submit request
            throw new Exception("User already have the access to " + watsonInstance + ", or request is WIP/Approved");
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

}
