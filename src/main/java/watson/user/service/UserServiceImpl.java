package watson.user.service;

import org.springframework.stereotype.Service;
import watson.user.commons.UserValidator;
import watson.user.dao.UserDao;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDaoImpl;

    @Override
    public void requestAccess(String domainUserName, String instance, String comments) {
        //check if user already have the access to the instance
        boolean userExists = UserValidator.checkUserExists(domainUserName, instance);
        //TODO check if pending approval
        //boolean pendingApproval = UserValidator.checkIfPendingApproval(domainUserName, instance);
        boolean pendingApproval = false;

        //if user doesn't exist and hasn't submit the request before, then allow to submit the request
        if (!userExists && !pendingApproval) {
            userDaoImpl.submitRequest(domainUserName, instance, comments);
            String managerEmail = userDaoImpl.getManager(domainUserName);
            //TODO notification.send(managerEmail);
        } else {//throw an error that user already have the access, no need to re-submit
            throw new RuntimeException("User already have the access");
        }

    }

    @Resource
    public void setUserDaoImpl(UserDao userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }
}
