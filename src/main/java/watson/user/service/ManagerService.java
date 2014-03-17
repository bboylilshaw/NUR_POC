package watson.user.service;

public interface ManagerService {
    public void approveRequest(String domainUserName, String instance);

    public void rejectRequest(String domainUserName, String instance);
}
