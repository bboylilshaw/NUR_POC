package watson.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.user.dao.RequestDaoImpl;
import watson.user.dao.UserDaoImpl;
import watson.user.model.HPEmployee;
import watson.user.model.Request;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired private UserDaoImpl userDao;
    @Autowired private RequestDaoImpl requestDao;
    @Autowired private NotificationServiceImpl notificationService;
    @Autowired private LDAPServiceImpl ldapService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitRequest(HPEmployee hpEmployee, String watsonInstance, String comments) throws Exception {
        //check if user is allowed to request access
        boolean requestExists = requestDao.exists(hpEmployee.getDomainUserName(), watsonInstance);

        //if user doesn't exist and allow to submit the request
        if (requestExists) {
            throw new Exception("Your request cannot be submitted! You had requested access to " + watsonInstance + " before, and it is pending approval or has been approved!");
        } else {//throw an error that user is not eligible to submit request

            String requestId = requestDao.save(hpEmployee, watsonInstance, comments);
            //TODO: get manager info and send notifications, hard code for now
//            String toManagerEmail = "yao.xiao@hp.com";
//            String ccEmail = "yao.xiao@hp.com";
//            String templateName = "EmailTemplates/UserRegInitialNotificationTemplate.vm";
//            HashMap<String, String> model = new HashMap<String, String>();
//            model.put("approver", "Approver");
//            model.put("url", "http://localhost:8080/NUR_POC/manager/review/request/" + requestId);
//            notificationService.sendEmailWithTemplate(toManagerEmail, ccEmail, templateName, model);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> listCurrentAccess(String domainUserName) {
        //TODO need to check HPUser for current status
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> listOpenRequests(String domainUserName) {
        return requestDao.listOpenRequests(domainUserName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> listRequestsAwaitingApproval(String domainUserName) {
        return requestDao.listRequestsAwaitingApproval(domainUserName);
    }

}
