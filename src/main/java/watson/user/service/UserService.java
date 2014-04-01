package watson.user.service;

import watson.user.model.HPEmployee;
import watson.user.model.Request;

import java.util.List;

public interface UserService {

    public void requestAccess(String domainUserName, String instance, String comments) throws Exception;

    public List<Request> listCurrentAccess(String domainUserName);

    public List<Request> listOpenAccessRequests(String domainUserName);

    public List<Request> listAccessRequestsAwaitingApproval(String domainUserName);

    public HPEmployee getEmployeeData(String domainUserName);
}
