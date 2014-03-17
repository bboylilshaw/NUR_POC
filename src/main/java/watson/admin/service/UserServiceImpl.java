package watson.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import watson.admin.commons.UserValidator;

@Service
public class UserServiceImpl implements UserService {



    @Override
    @Transactional
    public void requestAccess(String domainUserName, String instance) {
        //check if user already have the access to the instance
        boolean userExists = UserValidator.checkUserExists(domainUserName, instance);
        //if no, submit the request
        if (!userExists){
            userDao.submitRequest(domainUserName, instance);
            notification.send();
        } else {//if yes, throw an error that user already have the access, no need to re-submit
            throw new RuntimeException("User already have the access");
        }

    }
}
