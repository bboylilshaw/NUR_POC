package watson.user.dao;

import watson.user.model.HPUser;

public interface UserDao {
    public HPUser getHPUserByDomainUserName (String domainUserName, String instance);

    public String submitRequest(String domainUserName, String instance, String comments);

    public boolean userExists(String domainUserName, String instance);

    public String getManager(String domainUserName);
}
