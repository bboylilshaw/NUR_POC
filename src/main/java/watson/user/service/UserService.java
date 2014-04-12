package watson.user.service;

import watson.user.model.HPEmployee;
import watson.user.model.Request;

import java.util.List;

public interface UserService {

    public void submitRequest(HPEmployee hpEmployee, String watsonInstance, String comments) throws Exception;

    public List<Request> listCurrentAccess(String domainUserName);

    public List<Request> listOpenRequests(String domainUserName);

    public List<Request> listRequestsAwaitingApproval(String domainUserName);

}
