package watson.user.service;

public interface UserService {
    public void requestAccess(String domainUserName, String instance, String comments) throws Exception;
}
